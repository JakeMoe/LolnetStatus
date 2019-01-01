package com.gmail.jakesaddress.lolnetstatus;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;

public class Listeners {

  @Listener
  public void onClientConnectionJoin(ClientConnectionEvent.Join event) {
    Main.getInstance().getLogger().info("In client join event");
    event.getTargetEntity().setScoreboard(Main.getInstance().getStatusScoreboard().getScoreboard());
  }

  @Listener
  public void onClientConnectionDisconnect(ClientConnectionEvent.Disconnect event) {
    Main.getInstance().getLogger().info("In client disconnect event");
    event.getTargetEntity().setScoreboard(null);
  }

}
