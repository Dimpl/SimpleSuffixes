SimpleSuffixes
==============

A little plugin allowing players to set their on prefix and suffix! Also allows players to listen to others, emboldening and colouring their chat!


Commands
========

/prefix (/pre) or /suffix (/suf): Sets the player's prefix or suffix (default for pex, configurable in the config).
/prefixr (/prer) or /suffixr (/sufr): Resets another player's prefix or suffix (moderator privilege).
/listen (/follow): Listens to a player.
/listening (/following): Lists players being listened to.


Config
======
suffix-cmd or prefix-cmd: The command for setting the suffix or prefix.
suffix-max-length or prefix-max-length: Max length for suffix or prefix.
allowed-regex: Regex of characters that don't count towards the max length.
word-blacklist: Blacklist of character strings not allowed in prefixes or suffixes.
word-stafftags: Blacklist of character strings not allowed in prefixes or suffixes of non-staff.


Permissions
===========
simsuf.p or simsuf.s: Use of /pre and /suf.
simsuf.r: Use of /prer and /sufr.
simsuf.staff: Use of staff tags in prefix or suffix.