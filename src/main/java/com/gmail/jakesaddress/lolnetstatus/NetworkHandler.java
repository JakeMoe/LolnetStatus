package com.gmail.jakesaddress.lolnetstatus;

import nz.co.lolnet.servermanager.api.Platform;
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
  public void handleListFull(ListPacket.Full packet) {
    Main.getInstance().getLogger().info("Received ListPacket from {} containing {} servers", packet.getSender(), packet.getImplementations().size());
    packet.getImplementations().forEach((key, value) -> {
      if (key.getType() == Platform.Type.SPONGE && !key.getName().toLowerCase().startsWith("lobby")) {
        Main.setServerName(key.getId(), key.getName());
        Main.setStatus(key.getId(), value.getState());
      }
    });
    
    Main.getStatusScoreboard().updateAll();
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
    if (Main.getServerNames().containsKey(packet.getSender())) {
      Main.getInstance().getLogger().info("Received StatePacket from {}", packet.getSender());
      Main.setStatus(packet.getSender(), packet.getState());
      Main.getStatusScoreboard().updateAll();
    } else {
      Main.getInstance().getLogger().info("Received StatePacket from unknown server {}", packet.getSender());
    }
  }

}
