package com.gmail.jakesaddress.lolnetstatus;

import com.google.inject.Inject;
import nz.co.lolnet.servermanager.api.ServerManager;
import nz.co.lolnet.servermanager.api.network.packet.ListPacket;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameConstructionEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;

import java.util.concurrent.ConcurrentHashMap;

@Plugin(authors = "Cluracan",
        description = "Server status scoreboard plugin",
        id = "lolnetstatus",
        name = "Lolnet Status",
        version = "0.3")
public class Main {

  private static final String project = "LolnetStatus";
  private static final String version = "0.3";

  private static Main instance;
  private static ConcurrentHashMap<String, String> serverNames;
  private static ConcurrentHashMap<String, String> serverStatuses;
  private static StatusScoreboard statusScoreboard;

  @Inject
  private Logger logger;

  @Listener
  public void onGameConstruction(GameConstructionEvent event) {
    instance = this;
    logger.info("{} {} starting", project, version);
  }

  @Listener
  public void onGameInitialization(GameInitializationEvent event) {
    Sponge.getEventManager().registerListeners(this, new Listeners());
    logger.info("Registered listeners");
  }

  @Listener
  public void onServerStarted(GameStartedServerEvent event) {
    serverNames = new ConcurrentHashMap<>();
    serverStatuses = new ConcurrentHashMap<>();
    statusScoreboard = new StatusScoreboard();
    logger.info("Created and assigned StatusScoreboard");
    ServerManager.getInstance().registerNetworkHandler(NetworkHandler.class);
    logger.info("Registered NetworkHandler with ServerManager");
    ServerManager.getInstance().sendRequest(new ListPacket());
  }

  static Main getInstance() {
    return instance;
  }

  Logger getLogger() {
    return logger;
  }

  static ConcurrentHashMap<String, String> getServerNames() {
    return serverNames;
  }

  static ConcurrentHashMap<String, String> getServerStatuses() {
    return serverStatuses;
  }

  static StatusScoreboard getStatusScoreboard() {
    return statusScoreboard;
  }

  void setServerName(String id, String displayName) {
    serverNames.put(id, displayName);
  }

  void setStatus(String id, String status) {
    serverStatuses.put(id, status);
  }

}
