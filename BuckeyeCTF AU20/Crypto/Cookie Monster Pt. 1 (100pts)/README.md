# Cookie Monster Pt. 0

## Question

> Note: This is in the crypto category for a reason. It's very much a crypto challenge.  
> Hint: Look at the differences between parts 1, 2, and 3  
> Connect to this challenge at http://pwn.osucyber.club:13375

<details>
<summary>Hint: </summary>
<br>
The 20-char limit is only on input characters. There are single characters that encode as more than one character when stored in a JSON string.
</details>

## Solution (by bricks)
What sticks out the most is this section of the source code:
```
session = {
    'name': username,
    'role': 'users'
}
cipher = AES.new(key=current_app.config['KEY'], mode=AES.MODE_ECB)
cookie_data = json.dumps(session, separators=(',', ':'), sort_keys=True).encode('ascii', errors='replace')
return base64.b64encode(cipher.encrypt(pkcs7_pad(cookie_data)))
```
AES with ECB mode is insecure, and a quick Google brings up some similar challenges from other CTFs.

Our goal is to make sure that role is set to administrators. Oddly enough, the other parts of this challenge all use admin instead of administrators, so there might be some significance here.

Anyway, we need to make sure that when the cookie is decoded, it results in valid JSON with the correct role. Here is the code responsible for the decoding:
```
cipher = AES.new(key=current_app.config['KEY'], mode=AES.MODE_ECB)
cookie_data = pkcs7_unpad(cipher.decrypt(base64.b64decode(cookie)))
return json.loads(cookie_data.decode('ascii', errors='replace'))
```
In summary:

To encode:
- Put username into session object (with hardcoded `users` role)
- Create AES cipher using KEY (using ECB mode)
- Stringify session JSON
- PKCS7 pad stringified JSON
- Encrypt with AES cipher
- Base 64 encode

To decode:
- Create AES cipher using KEY (using ECB mode)
- Base 64 decode
- Decrypt with AES cipher
- PKCS7 unpad
- String to JSON
Due to the nature of AES, there's no feasible way to obtain the key even if we have plaintext and ciphertext pairs. So it looks our only hope is to do some hackery to find a ciphertext that decodes to the JSON we want.

AES-128 in EBC mode will encrypt 16 byte (128 bit) blocks at a time. Each block is encrypted in isolation. So the same block of plaintext always generates the block of ciphertext.

So if we wanted to find the ciphertext of this plaintext block 1234567890123456, we would need the website to encrypt it for us. If we gave that ciphertext block back to the website, it will always decrypt back to 1234567890123456.

Now the trick is to come up with name values we can enter into the website that will result in the plaintext blocks we want.

This turns out to be the hardest part. After playing around for a bit, it actually seems impossible to create inputs that result in the plaintext blocks we need. For example, one of the plaintext blocks we need is this:   
> administrators",   

In order to obtain this block we need to supply a name like xxxxxx_administrators:

> 1234567890123456
> {"name":"xxxxxx_
> administrators",
> "role":"users"}
However, xxxxxx_administrators is 21 characters long, and the maximum allowed length is 20. Now it seems impossible to generate any useful plaintext blocks. I was stuck here for about 3 HOURS until I realized something about this line of code:
```
if 5 <= len(username) <= 20:
```
To calculate the length of a string, doesn't Python just count the number of characters, regardless of whether they're ASCII or unicode? What happens if we put in a ton of Unicode characters like 😀😀😀😀😀😀😀😀😀😀😀😀😀😀😀😀😀😀😀😀?
```
{"name":"\ud83d\
ude00\ud83d\ude0
0\ud83d\ude00\ud
83d\ude00\ud83d\
ude00\ud83d\ude0
0\ud83d\ude00\ud
83d\ude00\ud83d\
ude00\ud83d\ude0
0\ud83d\ude00\ud
83d\ude00\ud83d\
ude00\ud83d\ude0
0\ud83d\ude00\ud
83d\ude00\ud83d\
ude00\ud83d\ude0
0\ud83d\ude00\ud
83d\ude00","role
":"users"}
```
What the heck?? Each of these Unicode characters expands to 6 characters. That means our new maxium is 20 * 6 which is 120. Now we can get the plaintext blocks we need by mixing in these unicode characters to increase the length of the JSON. For example, with 我_administrators, we get:
```
{"name":"\u6211_
administrators", <- This is the block we want
"role":"users"}
```
Perfect! Now we have to find some other bits and pieces to create a valid JSON string.

However, there's a big issue with double quotes. If we try to put in a " in the input string, it gets escaped to \". After getting stuck here for a few more hours (I was trying to make Python accept single quotes, but that didn't work), I realized that we could abuse whitespace to isolate a single character in the plaintext block. Since JSON doesn't care about whitespace, it can still be parsed without any issue.

After a lot of trial and error, I finally came up this list of plaintext blocks:
```
{"name":"xxxxxxx
xxxxxx","role":"
administrators",
"zzzzzzzzzzzzzzz
":              
"               
"               
}               
```
I had to include an extra "zzzzzzzzzzzzzzz": "" key-value pair so that JSON wouldn't complain about the comma after administrators". Since the author told json.dumps to sort the keys, we can guarantee that zzzzzzzzzzzzzzz will come after role.

Next I had to come up with inputs that would result in these plaintext blocks. These were:
```
xxxxxxx
我_xxxxxx
我_administrators
我"zzzzzzzzzzzzzzz
我":              
我"               
我_}               
我_xxxxxxxxxxxxxxx
```
The last input was needed to generate a full row of padding that the pkcs7_pad function would generate.

Plugging each of these inputs into the website, I recorded the cookie value in SESSIONID1. I then wrote a script to process these cookie values and combine the relevant blocks into one cookie to rule them all.
```
import base64


def slice_block(ciphertext: bytes, index: int) -> bytes:
    block_size = 16
    start_i = block_size * index
    end_i = start_i + block_size
    return ciphertext[start_i:end_i]


def print_hex_blocks(ciphertext: bytes):
    for i in range(len(ciphertext)):
        b = ciphertext[i:i+1]
        print(b.hex(), end='')

        if i > 0:
            if (i + 1) % 16 == 0:
                print()
            elif (i + 1) % 2 == 0:
                print(end=' ')


if __name__ == '__main__':
    cookies = {}

    u = 'xxxxxxx'
    cookies['name'] = 'gLeo7iVSypsn/drBfy3RbNqzjyybmzeMf7kRLIBfjMOw5q6quh7obDIhswwefiJ/'

    u = '我_xxxxxx'
    cookies['name_to_role'] = 'PWcy62SAOrsP5g6T3NFrE30kGeiwGNdQMVb1PUmSSIuUUEeu2MALDRwSEM/HUPvE'

    u = '我_administrators'
    cookies['admins'] = 'PWcy62SAOrsP5g6T3NFrE7D7rtDFzVt3hvNeWiz0otGzyCGGy/RtajYLllJgyL40'

    u = '我"zzzzzzzzzzzzzzz'
    cookies['zzz'] = 'U2l/OSWmJxArnhzkfE7elbHB6v0jFqSd7KzxFzedWXras48sm5s3jH+5ESyAX4zDsOauqroe6GwyIbMMHn4ifw=='

    u = '我":              '
    cookies['quote_colon'] = 'U2l/OSWmJxArnhzkfE7elVllHkQOfGWG7yCYJzrclBfas48sm5s3jH+5ESyAX4zDsOauqroe6GwyIbMMHn4ifw=='

    u = '我"               '
    cookies['quote'] = 'U2l/OSWmJxArnhzkfE7elT4irMNU1cVFIEzx4Mm5Y9Tas48sm5s3jH+5ESyAX4zDsOauqroe6GwyIbMMHn4ifw=='

    u = '我_}               '
    cookies['closing_bracket'] = 'PWcy62SAOrsP5g6T3NFrE3fvkbcAz1yplNd9r2En5wvas48sm5s3jH+5ESyAX4zDsOauqroe6GwyIbMMHn4ifw=='

    u = '我_xxxxxxxxxxxxxxx'
    cookies['fullpad'] = 'PWcy62SAOrsP5g6T3NFrE0ZkqLs0DTmDwwwpyQuQqMKHcnoJvwNYEQ6loImPxfrbcHeHlz/WN/Bwz+p0sC7ojg=='

    def slice_index(k):
        return {
            'name': 0,
            'fullpad': 3,
        }.get(k, 1)

    widgets = {}
    for key in cookies:
        ciphertext = base64.b64decode(cookies[key])
        # print_hex_blocks(ciphertext)
        # print()

        si = slice_index(key)
        widget = slice_block(ciphertext, si)
        # print_hex_blocks(widget)
        # print("---")
        # print()

        widgets[key] = widget

    ans = \
        widgets['name'] + \
        widgets['name_to_role'] + \
        widgets['admins'] + \
        widgets['zzz'] + \
        widgets['quote_colon'] + \
        widgets['quote'] + \
        widgets['quote'] + \
        widgets['closing_bracket'] + \
        widgets['fullpad']

    # print_hex_blocks(ans)
    ans = base64.b64encode(ans).decode()

    # print()
    print(ans)
```
This gave me
```
gLeo7iVSypsn/drBfy3RbH0kGeiwGNdQMVb1PUmSSIuw+67Qxc1bd4bzXlos9KLRscHq/SMWpJ3srPEXN51ZelllHkQOfGWG7yCYJzrclBc+IqzDVNXFRSBM8eDJuWPUPiKsw1TVxUUgTPHgyblj1HfvkbcAz1yplNd9r2En5wtwd4eXP9Y38HDP6nSwLuiO
```
After setting my SESSIONID1 cookie to this value, I was greeted by the flag:

osuctf{N3V3r_R0Ll_Y0ur_0wN_CrYP70}

## Solution (by ripcrypto)
So here we go. The first part of this problem is to look at the source provided. We know that what is being encrypted is JSON, where the only thing we can change is the username.
```
session = { 'name': username, 'role': 'users' }
```
We also know we need to change the role to administrators.
```
if session.get('role', 'users') == 'administrators': flag_text = current_app.config['FLAG']
```
It also tells us the type of encryption being used, which is ECB.
```
cipher = AES.new(key=current_app.config['KEY'],** mode=AES.MODE_ECB**)
```
If you're me and know nothing about this, you google ECB encryption to find weaknesses and just how it works. Here's good info on all the ways this encryption apparently is horrible: Why shouldn't I use ECB? It being horrible is great news for us! We can use both that each block is encrypted separately and that same plain text = same cipher text. (More on this later.)

Once you have the background info, one of the big things to notice in the source code is that you can't change the key at all and have no clue how it's generated. So this limits the types of attack. Some more googling into ECB attacks, and we find the attack is called a cut and paste attack. There's a lot of sites about that attack, but that was the one that I found least confusing.

With knowing it's a cut and paste attack, we can now use what we learned about ECB weaknesses. Looking once again at the source, we are given the block size = 16 bytes.
```
def pkcs7_pad(data: bytes, blocksize: int = 16) -> bytes:
```
[Note to problem creator: thank you so much for this. My head hurt looking at how to find this.] Knowing the block size, it's important to also note that after the ECB encryption that it is base64 encoded too. To be able to split into blocks I found it easiest to convert from the base 64 to hex.

We then need to figure out how to make the blocks we are given fit what we want. With only being able to change the username and needing to make the role administrators, this means we need to trick the encryption into giving us a block with "administrators" that only contains valid JSON. We can do this by pushing the string "administrators" to the second block.

We know the first block starts with:
```
{'name':'
```
which is only 9 bytes. We need to get 7 more bytes in this block, and "administrators" to the next block. I thought I could do this by just putting 7 letters before "adminsitrators", but we have a character limit in our login prompt. "administrators" is 16 bytes. Perfect size for its own block, but adding 7 characters to the input gives me more than the 20 character limit.

To get around the character limit, it's important to know how strings vs JSON behaves. The JSON converts characters such as ' to ' to escape the character. This doubles the characters input into the block. Then inputting ''''administrators gives us (with each bullet being a block:
```
{'name':''''\
'administrators'
```
So now we have one block and need to figure out what else we need. For 'administrators' to be it's own block, the blocks must be:
```
{'name':' [7 chars]
[7 chars] ', 'role':
'administrators'
} [Padding]
```
The second and last blocks must be formatted that way to ensure 'administrators' is it's own block. This means we need to input something to get } on its own. It ended up being:
```
{'name':' [7 chars]
[1 char] ,'role':'users'
}
```
Then we have the last two blocks of hex for final output. We just need to grab the first two lines which looks the same as above:
```
{'name':' [7 chars]
[7 chars] ', 'role':
```
We remove the last two blocks of hex, add on the hex we found, and then encode in base64. We then replace the given cookie with the one we found.

And we are finally done.

Note: I did a lot of this by hand and copying/pasting. This was the WRONG idea. Scripting can make this a lot easier. One way to do this is by modifying their source code to create a script with your own hard coded key to find the right blocks and make sure you have it correctly. This would have made my life easier. Instead a copy/paste error added about another hour onto my solving this.


