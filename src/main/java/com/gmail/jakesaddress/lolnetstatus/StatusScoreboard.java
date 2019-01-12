/*
 * This file is part of LolnetStatus.
 *
 * StatusScoreboard.java is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * StatusScoreboard.java is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with StatusScoreboard.java.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gmail.jakesaddress.lolnetstatus;

import nz.co.lolnet.servermanager.api.Platform;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.critieria.Criteria;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.scoreboard.objective.displaymode.ObjectiveDisplayModes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

class StatusScoreboard {

  private static final String statusObjectiveName = "ServerStatus";
  private Scoreboard scoreboard;
  private Objective objective;

  StatusScoreboard() {

    scoreboard = Sponge.getServer().getServerScoreboard().orElse(null);
    if (scoreboard == null) {
      Main.getInstance().getLogger().warn("Could not get server scoreboard");
      return;
    }

    objective = scoreboard.getObjective(statusObjectiveName).orElse(null);
    if (objective == null) {
      objective = Objective.builder()
        .criterion(Criteria.DUMMY)
        .displayName(Text.of(TextColors.WHITE, TextStyles.BOLD, "Server Status"))
        .name(statusObjectiveName)
        .objectiveDisplayMode(ObjectiveDisplayModes.INTEGER)
        .build();
      scoreboard.addObjective(objective);
      scoreboard.updateDisplaySlot(objective, DisplaySlots.SIDEBAR);
    }

    updateAll();

  }

  void updateAll() {
    objective.getScores().values().forEach(objective::removeScore);
    Main.getServerNames().forEach((key, value) -> {

      Platform.State status = Main.getServerStatuses().getOrDefault(key, Platform.State.UNKNOWN);
      TextColor statusColor;

      switch (status) {
        case JVM_STARTED:
        case CONSTRUCTION:
        case PRE_INITIALIZATION:
        case INITIALIZATION:
        case POST_INITIALIZATION:
        case CONNECTED:
        case LOAD_COMPLETE:
        case SERVER_ABOUT_TO_START:
        case SERVER_STARTING:
          statusColor = TextColors.YELLOW;
          break;
        case SERVER_STARTED:
          statusColor = TextColors.GREEN;
          break;
        case DISCONNECTED:
        case SERVER_STOPPING:
        case SERVER_STOPPED:
        case JVM_STOPPED:
          statusColor = TextColors.RED;
          break;
        case FROZEN:
          statusColor = TextColors.AQUA;
          break;
        case UNKNOWN:
        default:
          statusColor = TextColors.WHITE;
          break;
      }

      String statusString = status.getFriendlyName().substring(0, 35 - value.length());
      objective.getOrCreateScore(Text.of(TextColors.GOLD, value, TextColors.WHITE, ": ", statusColor, statusString)).setScore(0);

    });

  }
  
  void remove() {
    if (scoreboard == null || objective == null) {
      return;
    }
    
    objective.getScores().values().forEach(objective::removeScore);
    scoreboard.removeObjective(objective);
  }

  Scoreboard getScoreboard() {
    return scoreboard;
  }

}
