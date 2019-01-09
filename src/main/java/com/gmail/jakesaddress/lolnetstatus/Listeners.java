/*
 * This file is part of LolnetStatus.
 *
 * Listeners.java is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Listeners.java is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Listeners.java.  If not, see <https://www.gnu.org/licenses/>.
 */

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
