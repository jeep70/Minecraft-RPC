package me.mcofficer.minecraftrpc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class EventHandler {

    MinecraftRPC main;

    public EventHandler (MinecraftRPC main) {
        this.main = main;
    }

    @SubscribeEvent
    public void onClientConnectedToServerEvent(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        if (event.isLocal())
            main.proxy.rpcupdate(main, "Singleplayer", main.configHandler);
        else
            main.proxy.rpcupdate(main, "Multiplayer", main.configHandler);
    }

    @SubscribeEvent
    public void onClientDisconnectedFromServerEvent(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        main.proxy.rpcupdate(main, "in Main Menu", main.configHandler);
    }
}
