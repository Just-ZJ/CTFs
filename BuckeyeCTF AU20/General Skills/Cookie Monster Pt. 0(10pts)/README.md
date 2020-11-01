# Cookie Monster Pt. 0

## Question

> Connect to this challenge at http://pwn.osucyber.club:13382

## Solution

First, i went to the webside and looked at the source code: [view-source](http://pwn.osucyber.club:13382/source).

```
if session.get('role', 'users') == 'admin': (line45)
```

picked up my attention that i had to make the cookie have the value admin somehow. I realised that it was encoded in base64 and decoded my cookie content (eyJuYW1lIjoiYWRtaW4iLCJyb2xlIjoidXNlcnMifQ==) and got {"name":"admin","role":"users"}. Then i went ahead and encoded {"name":"admin","role":"admin"} and got eyJuYW1lIjoiYWRtaW4iLCJyb2xlIjoiYWRtaW4ifQ==. Then, i used [burpsite](https://portswigger.net/burp) to change the cookie value to eyJuYW1lIjoiYWRtaW4iLCJyb2xlIjoiYWRtaW4ifQ==, forward the request and then found the flag.

## Solution (by bricks)
