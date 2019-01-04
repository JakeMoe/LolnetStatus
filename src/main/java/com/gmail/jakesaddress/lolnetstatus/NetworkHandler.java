package com.gmail.jakesaddress.lolnetstatus;

import nz.co.lolnet.servermanager.api.ServerManager;
import nz.co.lolnet.servermanager.api.data.Setting;
import nz.co.lolnet.servermanager.api.network.AbstractNetworkHandler;
import nz.co.lolnet.servermanager.api.network.Packet;
import nz.co.lolnet.servermanager.api.network.packet.ListPacket;
import nz.co.lolnet.servermanager.api.network.packet.SettingPacket;
import nz.co.lolnet.servermanager.api.network.packet.StatePacket;

public class NetworkHandler extends AbstractNetworkHandler {

  @Override
  public boolean handle(Packet packet) {
    return (packet instanceof ListPacket && packet.getType().equals(Packet.Type.RESPONSE))
      || (packet instanceof SettingPacket && packet.getType().equals(Packet.Type.REQUEST))
      || (packet instanceof StatePacket && packet.getType().equals(Packet.Type.RESPONSE));
  }

  @Override
  public void handleList(ListPacket packet) {
    Main.getInstance().getLogger().info("Received ListPacket from {} containing {} servers", packet.getSender(), packet.getServers().size());
    packet.getServers().forEach((key, value) -> {
      if (key.toLowerCase().startsWith("sponge")) {
        Main.getInstance().setServerName(key, value);
      }
    });
  }

  @Override
  public void handleSetting(SettingPacket packet) {
    Main.getInstance().getLogger().info("Received SettingPacket from {}", packet.getSender());
    if (packet.getSetting() == null) {
      packet.setSetting(new Setting());
    }

    packet.getSetting().setForwardState(true);
    ServerManager.getInstance().sendResponse(packet);
  }

  @Override
  public void handleState(StatePacket packet) {
    Main.getInstance().getLogger().info("Received StatePacket from {}", packet.getSender());
    Main.getStatusScoreboard().updateAll();
  }

}
