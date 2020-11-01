# Undelete

## Question

> Help! I want to learn about x86 internals, but I've accidentally deleted the lecture material. I know it used to be on this 256MB FAT32 partition somewhere...  
> File Download: https://drive.google.com/file/d/1J2MPeORRHaGFqBdIHR_ZP7KSDBhzN5nm/view?usp=sharing

## Solution (by b01lers)
All we have to do is:
```
tar -xzvf battelle_files.tar.gz
```
and we get:
```
$ ls
ctfd-description.txt  flag  lipsum_generator.py  partition.img  README.md
$ cat flag
osuctf{d3l3t1ng_1sn7_ov3rwr1t1ng}
```

## Solution (by bricks)
using the command gzip -d battelle_files.tar.gz to get a tar file then use tar -xvf battelle_files.tar open the text file called flag and you get the flag

## Solution (by ripcrypto)
1. Open the tar and have a look inside
2. turns out in my tar viewing software it doesnt appear as deleted
3. so just read it
4. profit

## Solution (by We Are Pogging)
Downloading the file we realized that this was a winky weird file with .tar.gz. Never seen this file so we looked to our brother google for assistance. Really smart fello I must say. Apparently .gz is like a zip file that can be opened through gzip. We initially didn't know how to view files in linux using cat so we opened the file in notepad and poof, there the flag was given to us by the best company Battelle along with giberish cause we were using notepad.
