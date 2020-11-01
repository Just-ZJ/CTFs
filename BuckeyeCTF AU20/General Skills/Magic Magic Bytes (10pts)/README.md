# Magic Magic Bytes

## Question

>

## Solution

1. Use an [online hexadecimal editor](https://hexed.it/) to check if the file type is correct.
2. Since it is a zip file, change the file extension to .zip and unzip the file.
3. The unzipped file would contain another zip file.
4. Upload the 2<sup>nd</sup> zip file to [online hexadecimal editor](https://hexed.it/) and the flag would be shown.
5. Export and open with notepad to copy the flag(osuctf{n3v3R_tRU5t_tH3_F1l3_3XT3n510N})

**Flag: osuctf{n3v3R_tRU5t_tH3_F1l3_3XT3n510N}**

## Solution (by b01lers)

If we run file not_a_text_file.txt we get not_a_text_file.txt: Zip archive data, at least v2.0 to extract. Run unzip not_a_text_file.txt and we get a folder that contains a file with a .zip extension, but is actually text. Cat it to get flag.

## Solution (by WCSC)

Based on the challenge title, I expected this was a file with the magic number altered. Opening with [HxD hex editor](https://mh-nexus.de/en/hxd/), I saw "PK" as the first bytes and suspected this was a PK zip file. Renamed the extension to .zip, and opened with [7zip](https://www.7-zip.org/) to extract not_a_zip_file.zip, which I then open with [HxD](https://mh-nexus.de/en/hxd/) to get the flag.
