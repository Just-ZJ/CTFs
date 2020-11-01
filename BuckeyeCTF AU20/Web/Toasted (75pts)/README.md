# Toasted

## Question

> I wanted to be able to make toast from bed, so I bought an internet connected toaster. They haven't added ifttt support yet, but luckily it has a somewhat well documented web API :)  
> Connect to this challenge at pwn.osucyber.club:13372

## Solution (by WCSC)

For this challenge, you'll want to be familiar with GET and POST requests in HTTP.

When you visit the url, you'll find a website that controls a smart toaster. The website provides an API help, which has 6 methods available. Reading through them, two stuck out:

-  POST /api/generate_maintenance_token
-  GET /api/download_backup (maintenance token required)
   Maintenance usually has access to more functionality, so the backup seems to be where we will find the flag. We will need to generate a maintenance token. To do so, I mad a POST request to http://pwn.osucyber.club:13372/api/generate_maintenance_token. This endpoint complained that I need an API token. Drat.

Looking through the website, the http://pwn.osucyber.club:13372/quick-toast page makes a request to the API. Taking a look at the Networks Developer Tools in Chrome, the API token can be found hardcoded in the Javascript: gSNEaD868LJd1DldhZUglykfGwu_NbcLu9d1wmT5luLFTfHV2eVQYI8EupRMi71Cz6qydOc0kgXnGcDoPuUkkA

I now repeated the POST request for the maintenance token, but now we need a serial number. Making a GET request (http://pwn.osucyber.club:13372/api/status?token=gSNEaD868LJd1DldhZUglykfGwu_NbcLu9d1wmT5luLFTfHV2eVQYI8EupRMi71Cz6qydOc0kgXnGcDoPuUkkA) to the status page, I found the following:

{ "status": 0, "data": { "model": "Hot Stuff 1337", "num_toasted": "34", "serial": "60AKGPCIAX1AYIVN36M7MSIOXCRQ17ET2U17VUSS", "time": "2020-10-24T00:01:23.119Z" } }

Great, we have now the serial number now! I repeated the maintenance request again with both the token and serial, but this unfortunately still failed. I found adding the model number to the request, however, succeeded (the error message seems a bit misleading). This granted the token:

{ "status": 0, "token": "Ck2RtOs2RE1JTBnrOzEyaoC4fl8XfsyeoWtARkoc9ZAXwDAvyIHqMBzpBQhnYJT3ybXlu1BrbIfvVWPIkLpEdw" }

Great! Now just ask for the flag at http://pwn.osucyber.club:13372/api/download_backup?token=Ck2RtOs2RE1JTBnrOzEyaoC4fl8XfsyeoWtARkoc9ZAXwDAvyIHqMBzpBQhnYJT3ybXlu1BrbIfvVWPIkLpEdw and you're good to go!

## Solution (by bricks)

I found a main.js file using the Debugger in Firefox Devtools with the following code:
```
// cart logic
$('#toast-form').submit(function (event) {
    var sec = $("#toast-time").val()
\$.post("/api/toast", {"token": "gSNEaD868LJd1DldhZUglykfGwu_NbcLu9d1wmT5luLFTfHV2eVQYI8EupRMi71Cz6qydOc0kgXnGcDoPuUkkA", "time": sec}).done(function (data) {
if (data.status == 0) {
M.toast({html: data.message})
}
}).fail(function (xhr) {
let data = JSON.parse(xhr.responseText);
M.toast({html: data.error})
})
})
```
Looks like there was a hardcoded token that we could use for the API calls.

I started by trying out every API call, with the token supplied. Eventually, I found that this gave me some interesting information with this call:

$ curl "http://pwn.osucyber.club:13372/api/status?token=${token}"
```
{"status":0,"data":{"model":"Hot Stuff 1337","num_toasted":"279","serial":"60AKGPCIAX1AYIVN36M7MSIOXCRQ17ET2U17VUSS","time":"2020-10-24T04:28:11.762Z"}}
```
Cool, we got a serial number and the number of toasts toasted. Great. After some more poking around, we can see that api/generate_maintenance_token needs the serial number of the toaster. After supplying the serial number, we get a maintenance token: Ck2RtOs2RE1JTBnrOzEyaoC4fl8XfsyeoWtARkoc9ZAXwDAvyIHqMBzpBQhnYJT3ybXlu1BrbIfvVWPIkLpEdw.

Now we can call api/download_backup with this new token:

$ curl "http://pwn.osucyber.club:13372/api/download_backup?token=${mtoken}"
osuctf{dont_buy_an_int3rnet_connected_t0aster}
