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

    final int PHOENIX_NECKLACE_ID = 11090;

    @Inject
    private PneckHighlightOverlay(Client client, PneckReminderPlugin plugin, ItemManager itemManager)
    {
        this.client = client;
        this.plugin = plugin;
        this.itemManager = itemManager;
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
    }

}
