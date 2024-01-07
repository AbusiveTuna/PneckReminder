package com.pneck;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameTick;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;


@Slf4j
@PluginDescriptor(
	name = "PneckReminder"
)
public class PneckReminderPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private PneckHighlightOverlay pneckHighlightOverlay;

	public static boolean highlightPneck = false;

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(pneckHighlightOverlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(pneckHighlightOverlay);
	}

	@Subscribe
	public void onGameTick(GameTick gameTick)
	{
		ItemContainer inventory = client.getItemContainer(InventoryID.INVENTORY);
		boolean hasAmuletEquipped = checkAmuletSlot();
		highlightPneck = false;

		if (inventory != null && hasAmuletEquipped == false)
		{
			Item[] playerInventory = inventory.getItems();

			for(int i = 0; i < playerInventory.length; i++)
			{
				if(playerInventory[i].getId() == 11090)
				{
					highlightPneck = true;
				}
			}
		}
	}

	public boolean checkAmuletSlot()
	{
		ItemContainer playerEquipment = client.getItemContainer(InventoryID.EQUIPMENT);
		if(playerEquipment != null)
		{
			Item amulet = playerEquipment.getItem(EquipmentInventorySlot.AMULET.getSlotIdx());
			if(amulet != null)
			{
				return true;
			}
		}
		else
		{
			return false;
		}

		return false;
	}

}
