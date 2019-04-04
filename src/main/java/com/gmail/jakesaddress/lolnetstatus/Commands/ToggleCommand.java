/*
 * This file is part of LolnetStatus.
 *
 * ToggleCommand.java is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ToggleCommand.java is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ToggleCommand.java.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gmail.jakesaddress.lolnetstatus.Commands;

import com.gmail.jakesaddress.lolnetstatus.Main;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.text.Text;

public class ToggleCommand implements CommandExecutor {

  @Override
  public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
    if (src instanceof Player) {
      Player player = (Player) src;
      if (player.getScoreboard() == Main.getStatusScoreboard().getScoreboard()) {
        player.setScoreboard(Scoreboard.builder().build());
      } else {
        player.setScoreboard(Main.getStatusScoreboard().getScoreboard());
      }
      return CommandResult.success();
    } else {
      src.sendMessage(Text.of("This command must be run by a player"));
      return CommandResult.empty();
    }
  }
}
