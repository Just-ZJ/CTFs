# Subdomain

## Question

> Epoch 1603403466  
> btw, you will want to know that the creators of this problem are students at the ohio state university  
> due to circumstances beyond our control, this problem now spans both web, reversing, (as well as OSINT)! It is still very solvable, but we made it worth a lot more points.

<details open>
<summary>Hint: </summary>
<br>
when things look a bit noisy you'll know you're in the right place
</details>

## Solution (by b01lers)

1. Google "fontana ohio state"
2. it is a building and we have a timestamp -> prob webcamera
3. found https://www.teleport.io/feed/d0b8xiqj1ib9sfa7ptyi and it is broken
4. rev api, get the frameframeframeframeframe

## Solution (by ripcrypto)

1. identify epoch is time, get the time
2. google fontana and find that it is a building with a live camera feed
3. feed br0ke
4. find API documentation
5. POST arguments to get the frame at the time from the epoch
