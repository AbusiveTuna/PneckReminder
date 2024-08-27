package com.equipalert;

import net.runelite.api.Client;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.*;
import net.runelite.api.ItemID;

import javax.inject.Inject;
import java.awt.*;
import java.awt.image.BufferedImage;

public class EquipAlertHighlightOverlay extends WidgetItemOverlay
{
    private final Client client;
    private final EquipAlertPlugin plugin;
    private final ItemManager itemManager;
    private EquipAlertConfig config;

    @Inject
    private EquipAlertHighlightOverlay(Client client, EquipAlertPlugin plugin, ItemManager itemManager, EquipAlertConfig config)
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

        for (Map.Entry<Integer, Color> entry : EquipAlertPlugin.itemsToHighlight.entrySet()) {
            Integer itemToHighlight = entry.getKey();
            Color color = entry.getValue();

            final BufferedImage outline = itemManager.getItemOutline(itemToHighlight, widgetItem.getQuantity(), color);
            graphics.drawImage(outline, (int) bounds.getX(), (int) bounds.getY(), null);
        }

    }

}
