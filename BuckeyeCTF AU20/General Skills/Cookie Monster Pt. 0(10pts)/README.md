# Cookie Monster Pt. 0

## Question

> Connect to this challenge at http://pwn.osucyber.club:13382

## Solution

First, i went to the webside and looked at the source code: [view-source](http://pwn.osucyber.club:13382/source).

```
if session.get('role', 'users') == 'admin': (line45)
```

picked up my attention that i had to make the cookie have the value admin somehow. I realised that it was encoded in base64 and decoded my cookie content *eyJuYW1lIjoiYWRtaW4iLCJyb2xlIjoidXNlcnMifQ==* and got *{"name":"admin","role":"users"}*. Then i went ahead and encoded *{"name":"admin","role":"admin"}* and got *eyJuYW1lIjoiYWRtaW4iLCJyb2xlIjoiYWRtaW4ifQ==*. Then, i used [burpsite](https://portswigger.net/burp) to change the cookie value to *eyJuYW1lIjoiYWRtaW4iLCJyb2xlIjoiYWRtaW4ifQ==*, forward the request and then found the flag.

## Solution (by bricks)
Looking at the source code, we seem to be storing a cookie named SESSIONID0 with login credentials. This cookie is set whenever the login form is submitted:
```
        if form := request.form:
            try:
                cookie = create_session(form.get('username', ''))
                res.set_cookie(current_app.config.get('COOKIE_ID', 'SESSIONID'), cookie.decode('ascii'), max_age=3600)
            except SessionError as e:
                return redirect(url_for('login', err=e.args))
        return res
        ```
Looking at the create_session() function more closely, we see a few interesting things:
```
        session = {
            'name': username,
            'role': 'users'
        }
        cookie_data = json.dumps(session, separators=(',', ':'), sort_keys=True).encode('ascii', errors='replace')
        return base64.b64encode(cookie_data)
        ```
Basically this returns the value of the SESSIONID0 cookie. We see that in the flag() function, this value is checked like so:
```
        if session.get('role', 'users') == 'admin':
            flag_text = current_app.config['FLAG']
            ```
As long as we have the admin role, we can get the flag! So next we can manually Base64 encode {"name":"12345","role":"admin"}, and set the cookie to that value using your browser's developer tools. If we click the login button, we get the flag.
