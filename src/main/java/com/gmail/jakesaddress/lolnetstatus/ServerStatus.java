/*
 * This file is part of LolnetStatus.
 *
 * ServerStatus.java is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ServerStatus.java is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ServerStatus.java.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gmail.jakesaddress.lolnetstatus;

import java.util.HashMap;

class ServerStatus {

  private HashMap<String, String> serverStatus;

  ServerStatus() {
    serverStatus = new HashMap<>();
  }

  String getStatus(String server) {
    return serverStatus.get(server);
  }

  HashMap<String, String> getStatusAll() {
    return serverStatus;
  }

  void setStatus(String server, String status) {
    Main.getInstance().getLogger().info("Server: " + server);
    Main.getInstance().getLogger().info("Status: " + status);
    serverStatus.put(server, status);
  }

  HashMap<String, String> getServerStatus() {
    return serverStatus;
  }

}
