# Postmodern Petri Dish

## Question

> we had a flag on a subdomain but something happened and it got taken down :(  
> can you find the flag?  
> off limits:  
> wiki.osucyber.club  
> osucyber.club  
> live.osucyber.club

## Solution (by bricks)

Finding Subdomain:  
[Sublist3r](https://github.com/aboul3la/Sublist3r)  
[NMMAPER (online)](https://www.nmmapper.com/sys/tools/subdomainfinder/)  
Use the website above to search for subdomains with osucyber.club

One that looks interesting is: [9vyloyc3ojspmtuhtm6ejq.osucyber.club](9vyloyc3ojspmtuhtm6ejq.osucyber.club).

Since the challenge mentions that the site was recently taken down, we can maybe check on [web.archive.org](web.archive.org) to find an old version.  
Indeed, we can find [an old version](https://web.archive.org/web/20201022183102/http://9vyloyc3ojspmtuhtm6ejq.osucyber.club/).  
Inspecting the source of the page and searching for osuctf, we find: <!-- osuctf{wayback_mach1n3_mucks_fichigan} -- >

**Flag: osuctf{wayback_mach1n3_mucks_fichigan}**
