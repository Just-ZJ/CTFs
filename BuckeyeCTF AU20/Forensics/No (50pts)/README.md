# No

## Question

> Note: Flag format is flag{...}

## Solution (by Something witty 😛)

Basically Found This One By Luck My original idea was that one of the component's PDF specifications was altered in some way, making it invisible to the PDF reader. Thus, I opened up No.pdf in a hex editor and scanned through the PDF specifications. Around where the author is defined, there is a string embedded that says "You looking for something?", followed by a jibberish-looking string. After decoding that jibberish-looking string in base64, the flag was revealed.

## Solution (by Cyber@UC)

ran strings, saw the string that said you lookin for something, then base64 decoded the text near that

## Solution (by bricks)

At first glance, it looked like there was some stego is going on with the image. After extracting the image using pdfimages and running it through some tools, I couldn't find anything. I then decided to analyze the PDF a little more closely. As I slowly scrolled through the output of strings No.pdf, I found: You looking for something? followed by ZmxhZ3tUb3JvbnRvc1ByZXR0eUNvb2xFaD99. This seemed to be a Base 64 encoded string. After decoding it using echo ZmxhZ3tUb3JvbnRvc1ByZXR0eUNvb2xFaD99 | base64 -d, I got flag{TorontosPrettyCoolEh?}
