package com.gmail.jakesaddress.lolnetstatus;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.scoreboard.Scoreboard;

public class Listeners {

  @Listener
  public void onClientConnectionJoin(ClientConnectionEvent.Join event) {
    event.getTargetEntity().setScoreboard(Main.getStatusScoreboard().getScoreboard());
  }

  @Listener
  public void onClientConnectionDisconnect(ClientConnectionEvent.Disconnect event) {
    event.getTargetEntity().setScoreboard(Scoreboard.builder().build());
  }

}
