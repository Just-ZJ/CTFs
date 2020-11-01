# debugger

## Question

> Attached is a simple executable and source code -- it decrypts the flag and then exits.

Using a debugger such as gdb, make the program stop before it clears the decrypted flag and use gdb to print the flag out.

## Solution (by WCSC)
If you download pwndbg, this is even easier, as the flag will show up immediately when you place the breakpoint. First, open the program in gdb. Type run. Next, run disass main to dissassemble the program. You can see the call to memset, which is the function that clears memory. This occurs at memory address 0x400647. Run b *400647 to place a breakpoint, and run the program again. In pwndbg, you'll see the beginning of the flag in the dissassembly and on the stack. You can now print the flag by printing the top of the stack: x/s $rsp

## Solution (by b01lers)
```
for i in [87, 75, 77, 91, 76, 94, 67, 90, 74, 77, 76, 77, 75, 103, 84, 8, 78, 93, 75, 103, 90, 74, 11, 89, 83, 72, 87, 81, 86, 76, 75, 69]:
  print(chr(i ^ 0x38), end="")
```
## Solution (by bricks)
After looking at the source code, it looks like the ideal place to set a breakpoint would be on the memset call. We can open up pwn in gdb:
```
(gdb) disassemble main
Dump of assembler code for function main:
   0x0000000000400577 <+0>: push   %rbp
   0x0000000000400578 <+1>: mov    %rsp,%rbp
...
   0x0000000000400644 <+205>:   mov    %rax,%rdi
   0x0000000000400647 <+208>:   callq  0x400480 <memset@plt>
   0x000000000040064c <+213>:   lea    0xe0(%rip),%rdi        # 0x400733
End of assembler dump.
```
We can set a breakpoint at the memset call like so:
```
(gdb) break *0x0000000000400647
Breakpoint 1 at 0x400647: file pwn.c, line 11.
```
Then we can run the program. To see the flag, we can do print decrypted_flag which gives us osuctf{brutus_l0ves_br3akpoints}

## Solution (by Snack Overflow)
Began by uploading the pwn file and opening it with gdb. Began by opening the .c file to see what is happening. Saw that the decrypted flag was produced after the four loop. So I started by setting a break point at main, the used the "step" command until I was at the start of the loop and then used "step 64" to produce the flag. Finally, I used p"print decrypted_flag" to display the flag.

## Solution (by ripcrypto)
1. open the file up and find the string the indicates where to look for the decrypted key
2. set a breakpoint at that address in dbg
3. run until the breakpoint
4. print the variable
5. profit

## Solution (by We Are Pogging)
Go to https://www.onlinegdb.com/, paste the code in there. Next to the line number, click until you see a red dot. (Known as a break point before the line(s) that delete the flag). Start debugger, click start then you can see variable values in the variable list.

What this does is it pauses the execution so that you can see the flag before it gets removed.

## Solution (by Oh, right. The poison. The poison for Kuzco, the poison chosen especially to kill Kuzco, Kuzco's poison.)
First open GDB with command

gdb pwn

Which then opens gdb. We need to set a break point after the flag is decrypted but before it is deleted. Let's check the source code.

cat pwn.c

We see line 8 is where we need a break. Going into gdb...

break pwn.c:8

run

Now the program has stopped where we want it to. Simply print the flag and we are done!

print decrypted_flag

## Solution (by Something witty 😛)
Using gdb on the c file, one can add in a break point on line 10 (br 10), where the bytes have been decrypted and not yet wiped. Run the code and when it stops, run print arr to get the flag.

