# Ride

## Question

> I may have gotten a bit lost on my way home  
> Note: flag is osuctf{FLAG_IN_ALL_CAPS}

## Solution (by bricks)
At first glance, it seems to be a huge list of floating point numbers. But upon closer inspection is seems that the values between every other element only seem to change slightly. Based on the context of the problem, these values seem to be XY coordinates.

To take advantage of this, we can first reformat the file using a simple script:
```
with open('ride.txt') as f:
    with open('ride_col.txt', 'w') as fout:
        is_x = True
        for l in f.readlines():
            fout.write(l.strip())
            if is_x:
                fout.write(",")
            else:
                fout.write("\n")

            is_x ^= 1
```
This formats the file like so:

> 39.9561702,-82.9998764  
> 39.9561111,-82.9998112  
> 39.9561122,-82.9995704  
> 39.9561573,-82.9993301  
> ...  

Then we can load this value into Excel and plot it as a scatter plot. The resulting plot roughly resembles the osuctf prefix on the left, but is somewhat indecipherable. But you can actually flip the image vertically to get a better view. After that, it becomes clear that the text says: osuctf{OUTSID3}   

**Flag: osuctf{OUTSID3}**

## Solution (by ripcrypto)
1. recognize that the numbers are Lat-Long
2. format the data into a CSV of Lat,Long\n pairs
3. use online tool to plot lat-long CSV pairs
4. read the map of the result

## Solution (by Snack Overflow)
I wrote a python script that took in the numbers from the input file and mapped them to x and y coordinates. Then I plotted it using the matplotlib.pyplot module. The resulting plot was upside-down, so I inverted all of the y coordinates. Then I got my answer. (It was barely legible, but I got it!)

