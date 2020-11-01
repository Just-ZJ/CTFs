# Ride

## Question

> This quine fell off the back of a truck and some parts fell off. Luckily of the parts that broke (s and f), f can be reconstructed by running the quine. s, on the other hand, needs some work to get this quine back in working order. Be careful of those pesky line endings.

## Solution (by Snack Overflow)

Step 1 was, as always, to google what a "quine" is. It's a piece of code that copies itself... huh.

Step 2 was to sit down and dissect that python script, and in particular that print statement. That is one dense line of code, so I wrote it out by hand and, piece by piece, wrote down what each section meant. (I have like 5 more tabs open in Google searching for all of the different functions used in there.)

Step 3 was to realize that this code would copy itself by using the variables to reference portions of itself. So I copy/pasted the entire body of code into the variable s, making some minor edits like "s={s}" and "f={f}" so that the format() function would recognize each one as a variable.

Step 4 was to run it and tweak that string it until I got the flag. (Also I apparently needed a newline at the end. I don't think it came with that, but I could have just accidentally deleted it.)
