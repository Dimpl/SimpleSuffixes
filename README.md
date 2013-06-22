SimpleSuffixes
==============

A little plugin allowing players to set their on prefix and suffix! Also allows players to listen to others, emboldening and colouring their chat!


Commands
========
  /suffix or /suf: Allows a player to set their own suffix.

  /prefix or /pre: Allows a player to set their own prefix.

  /suffixr or /sufr: Allows someone to reset another player's suffix.

  /listen or /follow: Toggles listening to another player, emboldening and colouring their chat.

  /listening or /following: Lists the players being listened to.

  /initiate or /init: Sets a player as initiate.

  /initiates or /inits: Lists the players who are initiates.


Config
======
  suffix-cmd or prefix-cmd: The command for setting the suffix or prefix.

  suffix-max-length or prefix-max-length: Max length for suffix or prefix.

  allowed-regex: Regex of characters that don't count towards the max length.

  word-blacklist: Blacklist of character strings not allowed in prefixes or suffixes.

  word-stafftags: Blacklist of character strings not allowed in prefixes or suffixes of non-staff.

  initiates: Players that are set as initiates (do not edit).

  inittag: The tag that initiates receive.


Permissions
===========
  simsuf.s or simsuf.p: Allows a player to set their own suffix or prefix.

  simsuf.r: Allows someone to reset another player's suffix or prefix.

  simsuf.i: Allows someone to set another player's prefix to initiate.

  simsuf.staff: Allows someone to use a staff title in their suffix/prefix.
