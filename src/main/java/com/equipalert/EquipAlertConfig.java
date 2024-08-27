package com.equipalert;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("equipAlert")
public interface EquipAlertConfig extends Config {
    @ConfigItem(keyName = "phoenix", name = "Enable Phoenix Necklace", description = "Highlights Phoenix Necklaces", position = 1)
    default boolean phoenix() 
    {
        return true;
    }

    @ConfigItem(keyName = "binding", name = "Enable Binding Necklace", description = "Highlights Binding Necklaces", position = 2)
    default boolean binding() 
    {
        return true;
    }

    @ConfigItem(keyName = "slaughter", name = "Enable Bracelet of Slaughter", description = "Highlights Bracelets of Slaughter", position = 3)
    default boolean slaughter() 
    {
        return false;
    }

    @ConfigItem(keyName = "expeditious", name = "Enable Expeditious Bracelet", description = "Highlights Expeditious Bracelets", position = 4)
    default boolean expeditious() 
    {
        return false;
    }

    @ConfigItem(keyName = "dodgy", name = "Enable Dodgy Necklace", description = "Highlights Dodgy Necklaces", position = 5)
    default boolean dodgy() 
    {
        return false;
    }

    @ConfigItem(keyName = "recoil", name = "Enable Ring of Recoil", description = "Highlights Rings of Recoil", position = 6)
    default boolean recoil() 
    {
        return false;
    }

    @ConfigItem(keyName = "flamtaer", name = "Enable Flamtaer Bracelets", description = "Highlights Flamtaer Bracelets", position = 7)
    default boolean flamtaer() 
    {
        return false;
    }

    @ConfigItem(keyName = "chemistry", name = "Enable Amulet Of Chemistry", description = "Highlights Amulets Of Chemistry", position = 8)
    default boolean chemistry() 
    {
        return false;
    }

    @ConfigItem(keyName = "bounty", name = "Enable Amulet of Bounty", description = "Highlights Amulets of Bounty", position = 9)
    default boolean bounty() 
    {
        return false;
    }

    @ConfigItem(keyName = "faith", name = "Enable Necklace of Faith", description = "Highlights necklace of faiths", position = 10)
    default boolean faith() 
    {
        return false;
    }

    @ConfigItem(keyName = "clay", name = "Enable Bracelet of Clay", description = "Highlights Bracelets of Clay", position = 11)
    default boolean clay() 
    {
        return false;
    }

    @ConfigItem(keyName = "abyssal", name = "Enable Abyssal Bracelet", description = "Highlights Abyssal Bracelets", position = 12)
    default boolean abyssal() 
    {
        return false;
    }

    @ConfigItem(keyName = "efaritay", name = "Enable Efaritay's Aid", description = "Highlights Efaritay's Aid Rings", position = 13)
    default boolean efaritay() 
    {
        return false;
    }

    @ConfigItem(keyName = "forging", name = "Enable Ring of Forging", description = "Highlights Rings of Forging", position = 14)
    default boolean forging() 
    {
        return false;
    }

    @ConfigItem(keyName = "inoculation", name = "Enable Inoculation Bracelet", description = "Highlights Inoculation Bracelets", position = 15)
    default boolean inoculation() 
    {
        return false;
    }

    @ConfigItem(keyName = "pursuit", name = "Enable Ring of Pursuit", description = "Highlights Rings of Pursuit", position = 16)
    default boolean pursuit() 
    {
        return false;
    } 

}