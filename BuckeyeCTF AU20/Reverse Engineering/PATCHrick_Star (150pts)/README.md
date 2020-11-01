# Postmodern Petri Dish

## Question

>

## Solution (by WCSC)

For this challenge, you'll want some sort of software that's able to patch binaries. The demo version of binary ninja should work, but I did it using Ghidra and the awesome savePatch Plugin.

For this challenge, there are a few places you'll want to patch. The first will be the two calls to the function print_patrick. Just nop those out, they don't do anything.

Next, you'll want to fix the loop in main so it doesn't run forever. You can also just jmp over it using gdb if you prefer. Here is the line you want to change and what you want to change it to.

    00400c96      80 bd bf fe ff ff 7f        CMP        byte ptr [RBP + -0x141],0x7f
    00400c96      80 bd bf fe ff ff 01        CMP        byte ptr [RBP + -0x141],0x1

Now let's take a look at the decrypt_flag function. At the if statement, it's simply setting the entire flag to 0 instead of writing the value we want as the if statement is always false. To fix this, I simply inverted the jmp following the compare. Instead of JNZ, I made the instruction JBE. You can tell if this works in Ghidra as suddenly the decompiler will share the branches as flipped.

Now, you can just run the program!

## Solution (by b01lers)

Use binary ninja to patch out all of the 'nanosleep' calls.

There were a couple calls in main, and one in in the call to decrypt_flag.

## Solution (by ripcrypto)

So for this problem we were given a binary file. Running it through the file command lets us know it's an executable, and when we execute it in linux, it can't open the patrick1.txt and patrick2.txt files. I create those files, put "lol" in them, and the program runs normally, and prints an endless stream of spongebob "quotes". Opening the file in ghidra, I see where it decrypts and prints the flag. It won't get there though because its stuck in an infinite while loop printing spongebob quotes, so I just patched out the jump instruction that represented the while loop to a NOP instruction using ghidra's patch instruction function. I save the program, run it again, and get a little farther, but still get stuck after printing patrick2.txt. The decrypt_flag function, right above the printf that prints the flag, was locking up and waiting forever. I patched out the conditional that checks the time, and the nanosleep function call with nops, and it printed out the flag successfully.

## Solution (by Cyber@UC)
```
patched the binary

#!/usr/bin/python3

topatch = [bytes([0xe8, 0x45, 0xfb, 0xff, 0xff]),
           bytes([0xe8, 0xa6, 0xf8, 0xff, 0xff]),
           bytes([0x48, 0x8b, 0x45, 0xe8, 0x48, 0x83, 0xc0, 0x48, 0xc6, 0x00, 0x00]),
           bytes([0xe8, 0x25, 0xfb, 0xff, 0xff]),
           bytes([0xe8, 0xdc, 0xfa, 0xff, 0xff])
          ]

patch_replacement = [#(bytes([0x48, 0x83, 0x7d, 0xf8, 0x00]), bytes([0x48, 0x83, 0x7d, 0xf8, 0x01]))
                    ]

with open('PATCHrick_Star', 'rb') as f:
    c = f.read()

for topatch_bytes in topatch:
    patch = b'\x90'*len(topatch_bytes)
    c = c.replace(topatch_bytes, patch)

for t, p in patch_replacement:
    c = c.replace(t, p)

with open('PATCHrick_Star.pat', 'wb') as f:
    f.write(c)
```
