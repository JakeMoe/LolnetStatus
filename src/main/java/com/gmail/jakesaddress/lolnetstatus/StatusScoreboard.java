package com.gmail.jakesaddress.lolnetstatus;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.critieria.Criteria;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.scoreboard.objective.displaymode.ObjectiveDisplayModes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class StatusScoreboard {

  private ServerStatus serverStatus;
  private Scoreboard scoreboard;
  private Objective objective;

  public StatusScoreboard() {

    serverStatus = new ServerStatus();

    scoreboard = Sponge.getServer().getServerScoreboard().orElse(null);
    if (scoreboard == null) {
      Main.getInstance().getLogger().warn("Could not get server scoreboard");
      return;
    }

    objective = Objective.builder()
      .criterion(Criteria.DUMMY)
      .displayName(Text.of(TextColors.GOLD + "Server Status"))
      .name("ServerStatus")
      .objectiveDisplayMode(ObjectiveDisplayModes.INTEGER)
      .build();

    scoreboard.addObjective(objective);
    scoreboard.updateDisplaySlot(objective, DisplaySlots.SIDEBAR);

    updateServerStatusAll();

  }

  public void updateServerStatus(String server) {
    String status = "Unimplemented"; // Get the server's status.... somehow
    objective.getOrCreateScore(Text.of(server)).setScore(0);
    serverStatus.setStatus(server, status);
  }

  public void updateServerStatusAll() {
    String[] serverList = new String[] {"one", "two"};
    for (String server : serverList) {
      updateServerStatus(server);
    }
  }

  public Scoreboard getScoreboard() {
    return scoreboard;
  }

}
