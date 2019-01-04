package com.gmail.jakesaddress.lolnetstatus;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.critieria.Criteria;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.scoreboard.objective.displaymode.ObjectiveDisplayModes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.concurrent.ConcurrentHashMap;

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
    Main.getServerNames().forEachEntry(1L, (ConcurrentHashMap.Entry<String, String> e) -> {
      objective.getOrCreateScore(Text.of(e.getValue())).setScore(0);
    });

  }

  Scoreboard getScoreboard() {
    return scoreboard;
  }

}
