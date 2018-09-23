package com.gmail.jakesaddress.lolnetstatus;


import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameConstructionEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;

@Plugin(authors = "Cluracan",
        description = "Server status scoreboard plugin",
        id = "lolnetstatus",
        name = "Lolnet Status",
        version = "0.1")
public class Main {

  private static final String project = "LolnetStatus";
  private static final String version = "0.1";

  private static Main instance;
  private StatusScoreboard statusScoreboard;

  @Inject
  private Logger logger;

  @Listener
  public void onGameConstruction(GameConstructionEvent event) {
    instance = this;
    logger.info(project + " " + version + " starting");
  }

  @Listener
  public void onGamePreInitialization(GamePreInitializationEvent event) {
    statusScoreboard = new StatusScoreboard();
  }

  @Listener
  public void onGameInitialization(GameInitializationEvent event) {
    Sponge.getEventManager().registerListeners(this, new Listeners());
  }

  static Main getInstance() {
    return instance;
  }

  Logger getLogger() {
    return logger;
  }

  StatusScoreboard getStatusScoreboard() {
    return statusScoreboard;
  }

}