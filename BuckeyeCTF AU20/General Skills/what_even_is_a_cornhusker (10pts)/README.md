# what_even_is_a_cornhusker

## Question

> i took this screenshot of a flag i saw on tv, maybe there's something hidden in the image?

## Solution (by bricks)
Using the application steghide given to us in the linux vm, I tried to extract a hiddle text file in the jpg, after a few guesses of the password, I tried what_even_is_a_cornhusker as the password, which worked and revealed the flag in a hidden text file.

## Solution (by Oh, right. The poison. The poison for Kuzco, the poison chosen especially to kill Kuzco, Kuzco's poison.)
GIMP and xxd revealed nothing so tried steghide.

Had to use steghide extract -sf flag.jpg the passphrase was the challange name. Key was then reveled.
