/*
 * This file is part of LolnetStatus.
 *
 * Main.java is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Main.java is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Main.java.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gmail.jakesaddress.lolnetstatus;

import com.gmail.jakesaddress.lolnetstatus.Commands.HideCommand;
import com.gmail.jakesaddress.lolnetstatus.Commands.ReloadCommand;
import com.gmail.jakesaddress.lolnetstatus.Commands.ShowCommand;
import com.google.inject.Inject;
import nz.co.lolnet.servermanager.api.Platform;
import nz.co.lolnet.servermanager.api.ServerManager;
import nz.co.lolnet.servermanager.api.network.packet.ListPacket;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameConstructionEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import java.util.concurrent.ConcurrentHashMap;

@Plugin(authors = "Cluracan",
        description = "Server status scoreboard plugin",
        id = "lolnetstatus",
        name = "Lolnet Status",
        version = "0.16")
public class Main {

  private static final String project = "LolnetStatus";
  private static final String version = "0.16";

  private static Main instance;
  private static ConcurrentHashMap<String, String> serverNames;
  private static ConcurrentHashMap<String, Platform.State> serverStatuses;
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

    ServerManager.getInstance().registerNetworkHandler(NetworkHandler.class);
    logger.info("Registered NetworkHandler with ServerManager");

    serverNames = new ConcurrentHashMap<>();
    serverStatuses = new ConcurrentHashMap<>();
    statusScoreboard = new StatusScoreboard();
    logger.info("Created and assigned StatusScoreboard");

    initializeScoreboard();

    CommandSpec hideCommandSpec = CommandSpec.builder()
      .description(Text.of("Hides the status board"))
      .executor(new HideCommand())
      .permission("lolnetstatus.command.hide")
      .build();

    CommandSpec reloadCommandSpec = CommandSpec.builder()
      .description(Text.of("Reloads the status board"))
      .executor(new ReloadCommand())
      .permission("lolnetstatus.command.reload")
      .build();

    CommandSpec showCommandSpec = CommandSpec.builder()
      .description(Text.of("Shows the status board"))
      .executor(new ShowCommand())
      .permission("lolnetstatus.command.show")
      .build();

    CommandSpec commandSpec = CommandSpec.builder()
      .description(Text.of("LolnetStatus command"))
      .child(hideCommandSpec, "hide")
      .child(reloadCommandSpec, "reload")
      .child(showCommandSpec, "show")
      .permission("lolnetstatus.command")
      .build();

    Sponge.getCommandManager().register(this, commandSpec, "lolnetstatus", "ss");

  }
  
  @Listener
  public void onGameStoppingServer(GameStoppingServerEvent event) {
    if (statusScoreboard != null) {
      statusScoreboard.remove();
    }
  }

  public void initializeScoreboard() {
    serverNames.clear();
    serverStatuses.clear();
    logger.info("Cleared server names and statuses");
    ServerManager.getInstance().sendRequest(new ListPacket.Full());
    logger.info("Requested ListPack.Full from ServerManager");
  }

  public static Main getInstance() {
    return instance;
  }

  Logger getLogger() {
    return logger;
  }

  static ConcurrentHashMap<String, String> getServerNames() {
    return serverNames;
  }

  static ConcurrentHashMap<String, Platform.State> getServerStatuses() {
    return serverStatuses;
  }

  public static StatusScoreboard getStatusScoreboard() {
    return statusScoreboard;
  }

  static void setServerName(String id, String displayName) {
    serverNames.put(id, displayName);
  }

  static void setStatus(String id, Platform.State status) {
    serverStatuses.put(id, status);
  }

}
