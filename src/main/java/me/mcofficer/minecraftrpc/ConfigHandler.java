package me.mcofficer.minecraftrpc;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ConfigHandler {

    private Configuration config;

    // default values
    public String largeImageKey = "minecraft";
    public String largeImageText = "Minecraft";
    public String smallImageKey = "minecraft";
    public String smallImageText = "Minecraft";
    public String clientID = "384232743162216448";

    public ConfigHandler(Configuration config) {
        this.config = config;
    }

    public void syncConfig() {
        try{
            config.load();

            Property largeImageKeyProp = config.get(Configuration.CATEGORY_GENERAL,
                    "largeImageKey",
                    "minecraft",
                    "The key of an image uploaded to the application.");
            Property lateImageTextProp = config.get(Configuration.CATEGORY_GENERAL,
                    "largeImageText",
                    "Minecraft",
                    "The text displayed when hovering over the large image");
            //small image disabled in this fork
            // Property smallImageKeyProp = config.get(Configuration.CATEGORY_GENERAL,
            //         "smallImageKey",
            //         "minecraft",
            //         "The key of an image uploaded to the application.");
            // Property smallImageTextProp = config.get(Configuration.CATEGORY_GENERAL,
            //         "smallImageText",
            //         "Minecraft",
            //         "The text displayed when hovering over the small image");
            Property clientIDProp = config.get(Configuration.CATEGORY_GENERAL,
                    "clientID",
                    "384232743162216448",
                    "The client id retrieved from https://discordapp.com/developers/applications/me");

            largeImageKey = largeImageKeyProp.getString();
            largeImageText = lateImageTextProp.getString();
            //small image disabled in this fork
            // smallImageKey = smallImageKeyProp.getString();
            // smallImageText = smallImageTextProp.getString();
            clientID = clientIDProp.getString();

            config.save();
        }
        catch (Exception e) {
            System.out.println("Error loading Config - Falling back to default values:");
            e.printStackTrace(System.out);
        }
    }
}
