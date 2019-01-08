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
        .displayName(Text.of(TextColors.GOLD, "Lolnet Server Status"))
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
      int score;
      TextColor statusColor;

      switch (status) {
        case JVM_STARTED:
        case CONSTRUCTION:
        case PRE_INITIALIZATION:
        case INITIALIZATION:
        case POST_INITIALIZATION:
          score = 1;
          statusColor = TextColors.GOLD;
          break;
        case CONNECTED:
        case LOAD_COMPLETE:
        case SERVER_ABOUT_TO_START:
        case SERVER_STARTING:
          score = 1;
          statusColor = TextColors.YELLOW;
          break;
        case SERVER_STARTED:
          score = 0;
          statusColor = TextColors.GREEN;
          break;
        case DISCONNECTED:
        case SERVER_STOPPING:
        case SERVER_STOPPED:
        case JVM_STOPPED:
          score = 2;
          statusColor = TextColors.RED;
          break;
        case FROZEN:
          score = 3;
          statusColor = TextColors.BLUE;
          break;
        case UNKNOWN:
        default:
          score = 3;
          statusColor = TextColors.WHITE;
          break;
      }

      objective.getOrCreateScore(Text.of(TextColors.GOLD, value, TextColors.WHITE, ": ", statusColor, status.getFriendlyName())).setScore(score);

    });

  }

  Scoreboard getScoreboard() {
    return scoreboard;
  }

}
