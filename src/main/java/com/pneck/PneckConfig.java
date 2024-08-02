package com.pneck;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("didICompost")
public interface PneckConfig extends Config
{
    @ConfigItem(
            keyName = "neckOfFaith",
            name = "Enable Necklace of Faith",
            description = "Highlights necklace of faiths",
            position = 1
    )
    default boolean neckOfFaith()
    {
        return false;
    }

}