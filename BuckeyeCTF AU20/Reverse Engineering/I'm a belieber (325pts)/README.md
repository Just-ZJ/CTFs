# Authbot

## Question

> Note: Flag format is flag{...}

## Solution (by b01lers)
Good lord. Okay, first, download [binary ninja tools](https://github.com/SiD3W4y/binja-toolkit)

Next, load the binary and add the map (the given mapfile is close to the right format, just change tags to lowercase and remove sizes). This will let us....actually reverse it.

If we look at the conspicuously named check_key function we see: hlil

In this function we see a few branches. First, we know by pressing random stuff that start makes Justin blink ;) so we can gather that's what is doing switch_bg(). Next, we have some math I didn't bother reversing followed by a couple checks. First, if we have key_val == 8 or the division result doesn't equal the original input value, we reset the keyArrIndex to zero. Next, we know at the bottom that we want keyArrIndex > 0xa in order to print the flag. Could we just get the flag? Maybe. But as we'll see, the flag is just our input, so that is a harder problem. Instead what we want to do is use mgba to set 3 breakpoints:

1. 0x8000418 -> we need to have a breakpoint if our input was correct
2. 0x80003ae -> we need to have a breakpoint if our input was wrong
3. x80003de -> once we pass the check, to make sure we don't miss the flag

From there we can just use mgba to bash the sequence. Start with A, if we hit the correct breakpoint we know the correct input is A, if we don't, it's B. How do I know it's either A or B for each? The value in r0 is always 1 or 2, which corresponds to A or B by uh...inspection or something.

If we bash out the whole 10 character key and hit c we get the flag printed out onscreen.
