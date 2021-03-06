# YaffSquatch

## Question

> We pwned one of the challenge authors and dumped their home directory. Unfortunately, before we gained access they deleted everything and cleared their browsing history. Can you help us find the flag for the challenge they were working on?

## Solution (by WCSC)

For this challenge, we downloaded the [Chrome Cache tool from Nirsoft](https://www.nirsoft.net/utils/chrome_cache_view.html)

We then opened the cache in this tool, which recovered many cached files and their URLs. Based on the Recently Watched title, we sorted by URL and searched for Youtube. The first one was pretty cool, check it out. But that was just a distraction. The second one was also pretty cool [https://www.youtube.com/watch?v=IAKxlSplp-c](https://www.youtube.com/watch?v=IAKxlSplp-c), and if you sort the comments by most recent, you'll find the flag right at the top.

## Solution (by bricks)

First place to check was the History database file in the chrome config folder:

sqlite> SELECT \* FROM urls;
1|https://www.youtube.com/watch?v=dQw4w9WgXcQ|Rick Astley - Never Gonna Give You Up (Video) - YouTube|1|1|13247388071823408|0
Ok, not nice. Looks like we have to try some other stuff. The next thing I tried was hindsight, which gave me some more info.

```
cookie (created)  2020-10-16 23:00:35.129 .youtube.com/ GPS <encrypted>
cookie (created)  2020-10-16 23:00:35.130 .youtube.com/ VISITOR_INFO1_LIVE  <encrypted>
cookie (created)  2020-10-16 23:00:37.160 accounts.google.com/  __Host-GAPS <encrypted>
cookie (created)  2020-10-16 23:00:37.612 .doubleclick.net/ IDE <encrypted>
cookie (created)  2020-10-16 23:01:02.774 .google.com/  NID <encrypted>
url 2020-10-16 23:01:11.823 https://www.youtube.com/watch?v=dQw4w9WgXcQ Rick Astley - Never Gonna Give You Up (Video) - YouTube
preference  2020-10-16 23:01:24.412 https://www.youtube.com:443,* site_engagement [in Preferences.profile.content_settings.exceptions]  {'last_modified': '13247388084412754', 'setting': {'lastEngagementTime': 1.324738808441271e+16, 'lastShortcutLaunchTime': 0.0, 'pointsAddedToday': 3.12, 'rawScore': 3.12}}
preference  2020-10-16 23:01:25.799 https://www.youtube.com:443,* media_engagement [in Preferences.profile.content_settings.exceptions] {'last_modified': '13247388085799130', 'setting': {'audiblePlaybacks': 1, 'audioContextPlaybacks': 0, 'hasHighScore': False, 'highScoreChanges': 0, 'lastMediaPlaybackTime': 1.324738808141319e+16, 'mediaElementPlaybacks': 1, 'mediaPlaybacks': 1, 'significantPlaybacks': 1, 'visits': 1}}
```

Looks like we had some cookies with encrypted values. I spent forever trying to decrypt them using pycookiecheat but I couldn't get it to work.

Finally I decided to use the sketchy, closed-source, Windows-only ChromeCacheViewer. After opening it up and going through the entries, I noticed another URL I hadn't seen before: [https://www.youtube.com/watch?v=IAKxlSplp-c](https://www.youtube.com/watch?v=IAKxlSplp-c).

Going to comments section and sorting by newest, I found a suspicious comment by Andrew H: osuctf{maybe_i_can_just_wipe_it_off}

Flag: **osuctf{maybe_i_can_just_wipe_it_off}**
