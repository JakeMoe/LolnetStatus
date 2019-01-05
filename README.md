# LolnetStatus

## Description

Plugin to show server status in a scoreboard while players are in the lobbies

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
  * Fixed NetworkHander to actually do something with state info
  * Updated scoreboard to include server state, and colourise the text
* 0.5 - John Moe - 4 Jan 2019
  * Added space into scoreboard text
  * Added default "UNKNOWN" status if server hasn't reported yet
* 0.6 - John Moe - 5 Jan 2019
  * Filtered out lobbies from server list
  * Modified server status map to String, Status; doing translation in scoreboard now
  