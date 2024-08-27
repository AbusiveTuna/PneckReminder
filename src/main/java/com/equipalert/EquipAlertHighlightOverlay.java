package com.equipalert;

import net.runelite.api.Client;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.*;
import net.runelite.api.ItemID;

import javax.inject.Inject;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

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
        Color color = EquipAlertPlugin.itemsToHighlight.get(itemId);
        if (color != null)
        {
            Rectangle bounds = widgetItem.getCanvasBounds();

            if(config.heavyOutline()){
                graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 50));
                graphics.fillRect((int) bounds.getX(), (int) bounds.getY(), (int) bounds.getWidth(), (int) bounds.getHeight());

                // Draw the outline
                final BufferedImage outline = itemManager.getItemOutline(itemId, widgetItem.getQuantity(), color);
                graphics.drawImage(outline, (int) bounds.getX(), (int) bounds.getY(), null);
            } else {
                final BufferedImage outline = itemManager.getItemOutline(itemId, widgetItem.getQuantity(), color);
                graphics.drawImage(outline, (int) bounds.getX(), (int) bounds.getY(), null);
            }

        }
    }


}
