# Flagbin

## Question

> I found a site for pasting flags! Secretly of course.  
> Note: please don't share any actual flags with other teams, thx  
> Connect to this challenge at http://pwn.osucyber.club:13370  

## Solution (by ripcrypto)
1. Use a fuzzer to find the sitemap xml file
2. format the file to be only URLs
3. curl all of the contents of these pages into a single file
4. look for the key
5. profit

## Solution (by bricks)
Found http://pwn.osucyber.club:13370/sitemap.xml in the http://pwn.osucyber.club:13370/robots.txt file. Created a .sh file to curl each url. Had > 1000 urls.

ex file.sh limited to 3 urls:
- curl http://pwn.osucyber.club:13370/paste/48e03df4-9d0a-42ba-8b67-c0d25c2e148e
- curl http://pwn.osucyber.club:13370/paste/7b4c4ce5-7b61-550a-9918-3efe006ed13b
- curl http://pwn.osucyber.club:13370/paste/381bea2b-6ef8-56ef-831b-f03540e7b6e8

Ran the .sh file to output file and grepped for "osu".

