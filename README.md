# LolnetStatus

## Description

Plugin to show server status in a scoreboard while players are in the lobbies.

This plugin is designed to run on a Sponge server, and utilizes the ServerManager plugin to get server information.

## Dependencies

LolnetStatus requires ServerManager by [LXGaming](https://github.com/LXGaming) to acquire server status information.

## Installation and configuration

Simply copy the compiled jar file into your server's mods folder, and away you go. No configuration required.

## Copyright

LolnetStatus is available under the terms of the GPL v3 license.

![GPLv3 Logo](gplv3-88x31.png)

## Version History

* 0.1 - John Moe - 14 Sept 2018
  * Initial commit
* 0.2 - John Moe - 2 Jan 2019
  * Fixed up the scoreboard and objectives, which now display in-game
* 0.3 - John Moe - 4 Jan 2019
  * Refactored code line up with ServerManager
  * Added NetworkHandler for ServerManager
  * Finished up main plugin logic to test base functionality
* 0.4 - John Moe - 4 Jan 2019
  * Fixed NetworkHandler to actually do something with state info
  * Updated scoreboard to include server state, and colourise the text
* 0.5 - John Moe - 4 Jan 2019
  * Added space into scoreboard text
  * Added default "UNKNOWN" status if server hasn't reported yet
* 0.6 - John Moe - 5 Jan 2019
  * Filtered out lobbies from server list
  * Modified server status map to String, Status; doing translation in scoreboard now
* 0.7 - John Moe - 5 Jan 2019
  * Changed Map.forEachEntry out for Map.forEach
  * Fixed scoreboard state order and colors
* 0.8 - Alex Thomson - 7 Jan 2019
  * Updated for ServerManager v2.1.0
* 0.9 - John Moe - 8 Jan 2019
  * Updated ServerManager to v2.1.1
  * Added new CONNECTED and DISCONNECTED status to scoreboard
  * Added scores to each status to group servers together
* 0.10 - Alex Thomson - 9 Jan 2019
  * Remove debug messages from Listener
  * Remove scoreboard on Server Shutdown
  * Tweak colors and remove score order
* 0.11 - John Moe - 9 Jan 2019
  * Added GPLv3 license
  * Fixed up this README.md
  * Initial pass of command code written
* 0.12 - John Moe - 12 Jan 2019
  * Fixed score name length issue