package com.pneck;

import net.runelite.api.Client;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.*;

import javax.inject.Inject;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PneckHighlightOverlay extends WidgetItemOverlay
{
    private final Client client;
    private final PneckReminderPlugin plugin;
    private final ItemManager itemManager;
    private PneckConfig config;
    final int PHOENIX_NECKLACE_ID = 11090;
    final int NECKLACE_OF_FAITH_ID = 21157;

    @Inject
    private PneckHighlightOverlay(Client client, PneckReminderPlugin plugin, ItemManager itemManager, PneckConfig config)
    {
        this.client = client;
        this.plugin = plugin;
        this.itemManager = itemManager;
        this.config = config;
        showOnInventory();
    }

    @Override
    public void renderItemOverlay(Graphics2D graphics, int itemId, WidgetItem widgetItem)
    {
        Rectangle bounds = widgetItem.getCanvasBounds();

        if (itemId == PHOENIX_NECKLACE_ID && PneckReminderPlugin.highlightPneck)
        {
            final BufferedImage outline = itemManager.getItemOutline(itemId, widgetItem.getQuantity(), Color.red);
            graphics.drawImage(outline, (int) bounds.getX(), (int) bounds.getY(), null);
        }

        if(config.neckOfFaith()) {
            if (itemId == NECKLACE_OF_FAITH_ID && PneckReminderPlugin.highlightFaith) {
                final BufferedImage outline = itemManager.getItemOutline(itemId, widgetItem.getQuantity(), Color.pink);
                graphics.drawImage(outline, (int) bounds.getX(), (int) bounds.getY(), null);
            }
        }

    }

}
