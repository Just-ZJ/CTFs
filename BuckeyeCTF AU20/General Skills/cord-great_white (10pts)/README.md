# cord-great_white

## Question

> Wow, this challenge has a weird name... maybe the filename expresses it better?

## Solution (by b01lers)
We see a request for /flag.png...so go to File->Export Objects in wireshark and export that file. Open it feh flag.png and we get the flag.

## Solution (by bricks)
You can open this file in Wireshark, which will show a bunch of packets. We have some HTTP packets, so after some googling around, I realized that you could do File -> Export Exports -> HTTP, which would allow you export several files including flag.png to a separate folder. Opening flag.png shows an image with this text: osuctf{p4ck3ts_4r3_c00L}

## Solution (by ripcrypto)
The first step is to run the tool through wireshark. This gives you the packets that have been captured. Wireshark gives you strings it finds as output. With this you see there is an html file with a png in it that is called flag. If you export as http, you can get the html sent over, including the actual flag image.

## Solution (by Snack Overflow)
Open the file in wire shark, I then ordered the network packets by protocol. I then noticed flag.png. I then went to file- export objects then selected flag.png. And there it was.
