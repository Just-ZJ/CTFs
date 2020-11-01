# Sus

## Question

> We noticed one of our computers acting a little odd, so we captured some network traffic. Now it's definitely suspicious

## Solution (by b01lers)
We follow the tcp conversation, dump the hex of the program to a file, and open it up. We RE this and see that it accepts 2 commands over the wire, E and C.

C runs a command and captures up to 0x400 of the output. Cool. Anyway.

E sets the encryption key. We have in our packet capture that that key is "DontCallMeSecurely". We also have the ciphertext. So....lets just ignore the RE and decrypt.
```
// gcc -o solvesus solvesus.c -lcrypto
#include <openssl/aes.h>
#include <stdio.h>

int main() {
    const unsigned char * userKey = "DontCallMeSecurely";
    const unsigned char peer1_175[] = { /* Packet 360 */
        0x0e, 0x44, 0xe0, 0xaa, 0x4c, 0x65, 0x59, 0xf0, 
        0xfa, 0x14, 0x1f, 0xe1, 0xc7, 0x36, 0xac, 0xe4 
    };
    const unsigned char peer1_176[] = { /* Packet 362 */
        0xae, 0x81, 0x44, 0x4a, 0x89, 0x0c, 0x5a, 0xb3, 
        0x80, 0xfc, 0x42, 0xf4, 0x3a, 0x97, 0x47, 0xba 
    };
    const unsigned char peer1_177[] = { /* Packet 364 */
        0x26, 0x50, 0x03, 0x40, 0x56, 0xeb, 0xa1, 0x5f, 
        0xea, 0x75, 0xa7, 0xbb, 0xc0, 0xa3, 0xde, 0x87 
    };
    unsigned char out1[17] = {0};
    unsigned char out2[17] = {0};
    unsigned char out3[17] = {0};
    const int bits = 0x80;
    AES_KEY k;
    AES_set_decrypt_key(userKey, bits, &k);
    AES_decrypt(peer1_175, out1, &k);
    AES_decrypt(peer1_176, out2, &k);
    AES_decrypt(peer1_177, out3, &k);
    printf("%s%s%s\n", out1, out2, out3);
}
```

## Solution (by ripcrypto)

Looking at the binary, it appears to be a wireshark pcap file. Opening it in wireshark, we get a bunch of packet captures. Most of the data in these packets is pretty random, at the start it says Cxxd -p shellcode, and at the end it says EDontCallMeSecurely and Ccat flag.txt. I get a general idea that the random data is shellcode in the middle, and a flag at the bottom, but the flag is unreadable.

Since I can't read the "flag", I use a data.data filter and the follow TCP stream function in wireshark to see all of the data in each packet recombined into one big file. I can download this file using the download raw function.

Now that I have the shellcode (as well as some extra junk (cat, xxd, flag commands)), I convert the shellcode from hex ascii bytes to a binary file using the xxd -p -r function. The -r flag reverses the regular xxd operation. Next, I try and run the binary file I created from the xxd command, but it segfaults. Darn...

I guess I have to do static analysis in Ghidra. I open the binary in Ghidra, and go to the main function, which looks like this. There are two "commands". "E" for encryption, and "C" for printing stuff out. If you go into the function I liked to call notprintf, you find that if an "E" command is run, the encrypt flag turns on and the next command will be encrypted. Since EDontCallMeSecurely was followed by Ccat flag.txt, the flag was encrypted with AES.

Now the hardest part was decrypting the damn thing. Online tools were shit, and trying to use the linux openssl commands led to a lot of dead ends. I eventually was looking at the AES in the crypto challenges using python, and tried to adapt it to my own purposes, using AES-256-ECB mode. At first it didn't work, because my key was too long. After I changed the key from DontCallMeSecurely to DontCallMeSecure, which is 16 bytes, it worked, and the flag was printed out. I used the cat flag.txt output as ciphertext btw. I had to guess that DontCallMeSecurely was the key though, because I didn't see any reference to it under the E command in the disassembly.
