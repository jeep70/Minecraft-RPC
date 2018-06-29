package me.mcofficer.minecraftrpc;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;

import javax.annotation.Nullable;

public class RPCClient {

    public String CLIENT_ID;

    public RPCClient (String clientId){
        this.CLIENT_ID = clientId;
    }

    private static Thread callbackRunner;

    public synchronized void init()
    {
        DiscordEventHandlers handlers = new DiscordEventHandlers();

        DiscordRPC.INSTANCE.Discord_Initialize(CLIENT_ID, handlers, true, null);

        if (callbackRunner == null)
        {
            callbackRunner = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    DiscordRPC.INSTANCE.Discord_RunCallbacks();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ignored) {}
                }
            }, "RPC-Callback-Handler");

            callbackRunner.start();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(DiscordRPC.INSTANCE::Discord_Shutdown));

        System.out.println("RPCClient has been started.");

    }

    public void updatePresence(@Nullable String details, ConfigHandler configHandler)
    {
        DiscordRichPresence presence = new DiscordRichPresence();
        presence.largeImageKey = configHandler.largeImageKey;
        presence.largeImageText = configHandler.largeImageText;
        //small image disabled in this fork
        // presence.smallImageKey = configHandler.smallImageKey;
        // presence.smallImageText = configHandler.smallImageText;
        if (details != null){
            presence.details = details;
            presence.startTimestamp = System.currentTimeMillis() / 1000;
        }

        DiscordRPC.INSTANCE.Discord_UpdatePresence(presence);
    }

}
