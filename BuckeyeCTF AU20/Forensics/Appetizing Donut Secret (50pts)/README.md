# Appetizing Donut Secret

## Question

> The Neble-Trutwerth Family Shop, famous in town for their amazing donuts, has contacted you because an unexpected family death means no one has the secret recipe any more. The original owner always said he had the recipe hidden on his favorite flash drive, but the family couldn't find the donut recipe when they plugged it in. They've given you an image of the drive, so it's up to you to find the recipe and save the town's beloved donut shop!

## Solution

Open the file on an [online hexadecimal editor](https://hexed.it/#base64:SECRETS.dsk;b3N1Y3Rme3AxYW5rNzBuX2M0bjdfbnRmNX0=) and ctrl + F "ctf"

## Solution (by bricks)

The first thing I tried was opening the file in [7-zip](https://www.7-zip.org/) to get more info on it. Then, I discovered I could see more files inside of the Secrets file. I immediately fell for the bait images of assorted memes that were named like a solution. I eventually narrowed my search down to one file but it would not open for me. I right clicked the file and selected "alternate streams". This lead me to a textfile that contained the flag.

## Solution (by b01lers)

I used xxd, piped the output into 'less' and searched for 'osuctf'

```
00016e70: 6573 706f 6f6e 2061 6374 6976 6520 6472  espoon active dr
00016e80: 7920 7965 6173 740a 332f 3420 6375 7020  y yeast.3/4 cup
00016e90: 626c 6163 6b62 6572 7279 206a 616d 0a32  blackberry jam.2
00016ea0: 2071 7561 7274 7320 7665 6765 7461 626c   quarts vegetabl
00016eb0: 6520 6f69 6c20 666f 7220 6672 7969 6e67  e oil for frying
00016ec0: 0a6f 7375 6374 667b 7031 616e 6b37 306e  .osuctf{p1ank70n
00016ed0: 5f63 346e 375f 6e74 6635 7d0a 0000 0000  _c4n7_ntf5}.....
00016ee0: ffff ffff 0000 0000 0000 0000 0000 0000  ................
00016ef0: 0000 0000 0000 0000 0000 0000 0000 0000  ................
00016f00: 0000 0000 0000 0000 0000 0000 0000 0000  ................
00016f10: 0000 0000 0000 0000 0000 0000 0000 0000  ................
00016f20: 0000 0000 0000 0000 0000 0000 0000 0000  ................
```

## Solution (by Cyber@UC)

strings SECRETS| grep osu

## Solution (by Something witty 😛)

If you do testdisk SECRETS (with "None" partition), it will list all the files that had been deleted on the drive. You can copy all those files out to your current directory and then use grep on those files to search for a the flag.
