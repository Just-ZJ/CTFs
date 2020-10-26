# Postmodern Petri Dish

## Question

> https://discord.gg/4ewh3QQ DM @authbot: \$help

## Solution

By DM-ing @authbot, \$help  
A list of options were given:  
> $ping  
$coinflip  
$auth  
$help  
$info  

A [link](https://github.com/qxxxb/auth_bot) was given when the command $info was sent in the autbot chat.

If we [look at the code](https://github.com/Just-ZJ/CTFs/blob/main/BuckeyeCTF%20AU20/Reverse%20Engineering/Authbot%20(50pts)/auth_bot-master/main.py), we see a hidden option that isn't listed: $debug_log [Line 110]

If we send the command $debug_log at the authbot chat, we get:  
> 2020-10-23 23:39:08 INFO     Logged in as authbot#4452  
2020-10-23 23:39:13 DEBUG    User ath0#0294 authed as admin with password hash c023d5796452ad1d80263a05d11dc2a42b8c19c5d7c88c0e84ae3731b73a3d34  

Puting *c023d5796452ad1d80263a05d11dc2a42b8c19c5d7c88c0e84ae3731b73a3d34* into [crackstation](https://crackstation.net/), we get the password gobucks.  
Run *$auth gobucks* and we get access to the flag channel. 

**Flag: osuctf{d0n7_lOG_y0UR_Au7h_57r1Ngs}**
