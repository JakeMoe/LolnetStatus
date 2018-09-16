package com.gmail.jakesaddress.lolnetstatus;

import java.util.HashMap;

public class ServerStatus {

  private HashMap<String, String> serverStatus;

  String getStatus(String server) {
    return serverStatus.get(server);
  }

  HashMap<String, String> getStatusAll() {
    return serverStatus;
  }

  void setStatus(String server, String status) {
    serverStatus.put(server, status);
  }

  HashMap<String, String> getServerStatus() {
    return serverStatus;
  }

}
