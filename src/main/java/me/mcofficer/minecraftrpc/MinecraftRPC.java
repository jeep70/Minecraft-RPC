package me.mcofficer.minecraftrpc;

import me.mcofficer.minecraftrpc.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;


@Mod(modid = MinecraftRPC.modId, name = MinecraftRPC.name, version = MinecraftRPC.version)
public class MinecraftRPC {

    public static final String modId = "minecraftrpc";
    public static final String name = "Minecraft-RPC";
    public static final String version = "1.2";

    public EventHandler eventHandler = new EventHandler(this);
    public RPCClient rpcClient;
    public ConfigHandler configHandler;

    @SidedProxy(serverSide = "me.mcofficer.minecraftrpc.proxy.CommonProxy", clientSide = "me.mcofficer.minecraftrpc.proxy.ClientProxy")
    public static CommonProxy proxy;

    @Mod.Instance(modId)
    public static MinecraftRPC instance;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        configHandler = new ConfigHandler(new Configuration(event.getSuggestedConfigurationFile()));
        configHandler.syncConfig();
        MinecraftForge.EVENT_BUS.register(eventHandler);
        this.rpcClient = new RPCClient(configHandler.clientID);
        proxy.rpcinit(this);
        proxy.rpcupdate(this, "Loading...", configHandler);
    }

    @Mod.EventHandler
    public void onPostInitEvent(FMLPostInitializationEvent event) {
        proxy.rpcupdate(this, "in Main Menu", configHandler);
    }
}
