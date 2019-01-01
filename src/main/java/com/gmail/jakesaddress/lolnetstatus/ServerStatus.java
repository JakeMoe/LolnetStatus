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
