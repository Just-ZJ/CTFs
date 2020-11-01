# my_favorite_shape

## Question

> You discover that a spy has infiltrated the OSU campus and is sending secrets back to "that state up north" by transmitting a signal at constant output power to some receiver stations placed around campus when they go on their daily walk. Hoping to capture the secret messages, you find each of the stations, connect to a debug port, and record the output. The combined output of the debug ports is available in the provided file. Looks like you still have some decoding to do... what are all these Received Power values good for?

> Challenge creator's notes:
> - To confirm the previous update, it is intended that you are given Received Power values, not RSSI and not (directly) distance
> - This challenge is not meant to be tied to any real-world location.
> - You can ignore (or "make reasonable assumptions" about) details for any antennas or wavelengths for this problem. You shouldn't have to think about them to solve this problem.

## Solution (by Snack Overflow)
After downloading and viewing the output.log file included for this problem, the first step in solving this one was obviously to google what RSSI values are. Because come on, who knows this off the top of their head unless they work in wireless networking? (NOTE: I see this has now been changed to "received power" levels. Understandable, as RSSI values are usually measured in decibels, whereas the values given seem to be proportions.) After learning that these measured the strength of the signal received at each station, I set about making a mathematical model of this situation.

So the one piece of outside info that is absolutely essential to this problem is the inverse square law. The power received at any one station is proportional to 1/(r^2 ), where r is the distance from the station to the point where the signal originated. This law is basically universal among wireless signals.

Since we're using an x-y coordinate system, r^2 = x^2 + y^2. Now we're getting somewhere. The power received at the first station at (0,0) should be proportional to 1/(x^2 + y^2 ). The power received at the station at (150, 400) should be proportional to 1/((x-150)^2 + (y-400)^2 ). And lastly, the power received at the third station at (300, 0) should be proportional to 1/((x-300)^2 + y^2 ). x and y, by the way, are the x and y coordinates where the signal originated.

We also have the power level at three stations, which I called a, b, and c. Now this next assumption may not be quite as the puzzle designer intended, but I interpreted the values given as proportions. In other words, for some broadcast strength e, the power reading at the first station would be equal to a*e, and would be equal to e/(x^2 + y^2 ). So I divided it out of all three equations, and got the following:

- a = 1/(x^2 + y^2 )
- b = 1/((x-150)^2 + (y-400)^2 )
- c = 1/((x-300)^2 + y^2 )
And then all that was left to do was solve that system of equations... oh joy...

I won't go into too much detail, but that was pages worth of math. Still, it got me a final result of

- y = (1/a + 1/c - 2/b + 275000)/1600
- x = (1/a - 1/b) + 182500 - 800*y
And that was enough to start scripting. I wrote a Python script that took in the output.log file and, for each set of three station values, calculated these x and y coordinates. There were also values where a, b, c were all equal to 0, so I had it print "no signal" during those times. And then, knowing that I needed to plot these somehow, I installed the matplotlib module and used its Pyplot construct to make and display a plot. This led to a scatterplot that looked like just a bunch of points. Ok, a bit disheartening, but still, we have a plot.

I realized that I needed to get some lines drawn on this. I had a suspicion, so I opened up GIMP and played "connect the dots" with the first five coordinates. That's when it clicked: every set of points traced out a letter, followed by a period of "no signal." So I moved the plot function inside my loop; if a,b, and c were equal to 0, then the program would spit out a plot with lines and wait for me to close it before continuing. This allowed me to go through each letter, writing it down by hand as the code ran through each set of points. And that was the flag!

(Author's note: this is what got me the final solution. Not shown here are even more pages of math where I tried a different - and wrong - model, as well as all the minor issues that came up in using a less familiar language. I'm not quite used to Python yet.)

## Solution (by ripcrypto)
This challenge was messed up, but very rewarding.

They tell you there are 3 receivers and they give you like 700 data points for received power values at these 3 locations.

Whenever you see three distance related data points you can know that you may be able to use trilateration. Trilateration is what most people mean to say when they say triangulation.

To do trilateration though you need to get from received power (given) to distance. By assuming that all the receivers have uniform gain at all angles you can use the equation:

Prx=Ptx/(4piR^2) where R is the radius used in trilateration.

from here you just plot all the points in order. They will spell characters where the 0 power measurements are spaces.
