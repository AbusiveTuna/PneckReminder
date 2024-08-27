package com.equipalert;

import javax.inject.Inject;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameTick;
import net.runelite.api.ItemID;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;


@Slf4j
@PluginDescriptor(
	name = "EquipAlert"
)
public class EquipAlertPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private EquipAlertHighlightOverlay equipAlertHighlightOverlay;

	@Inject
	private EquipAlertConfig config;

	private boolean hasAmuletEquipped = false;
	private boolean hasBraceletEquipped = false;
	private boolean hasRingEquipped = false;

	public static Map<Integer, Color> itemsToHighlight = new HashMap<>();

	@Provides
	EquipAlertConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(EquipAlertConfig.class);
	}

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(equipAlertHighlightOverlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(equipAlertHighlightOverlay);
	}

	@Subscribe
	public void onItemContainerChanged(ItemContainer itemContainer)
	{
		//we only need to check the inventory when we detect a change
		if(itemContainer.getId() == InventoryID.INVENTORY){

			ItemContainer inventory = itemContainer;

			if (inventory != null && (!hasAmuletEquipped || !hasBraceletEquipped || !hasRingEquipped))
			{
				//resets HashMap
				itemsToHighlight.clear();

				Item[] playerInventory = inventory.getItems();

				for(Item item : playerInventory)
				{
					switch(item.getId())
					{
						case ItemID.PHOENIX_NECKLACE:
							if(config.phoenix())
							{
								itemsToHighlight.put(ITEMID.PHOENIX_NECKLACE, Color.RED);
							}
							break;
						case ItemID.BINDING_NECKLACE:
							if(config.binding())
							{
								itemsToHighlight.put(ITEMID.BINDING_NECKLACE, Color.GREEN);
							}
							break;
						case ItemID.BRACELET_OF_SLAUGHTER:
							if(config.slaughter())
							{
								itemsToHighlight.put(ITEMID.BRACELET_OF_SLAUGHTER, Color.RED);
							}
							break;
						case ItemID.EXPEDITIOUS_BRACELET:
							if(config.expeditious())
							{
								itemsToHighlight.put(ITEMID.EXPEDITIOUS_BRACELET, Color.YELLOW);
							}
							break;
						case ItemID.DODGY_NECKLACE:
							if(config.dodgy())
							{
								itemsToHighlight.put(ITEMID.DODGY_NECKLACE, Color.RED);
							}
							break;
						case ItemID.RING_OF_RECOIL:
							if(config.recoil())
							{
								itemsToHighlight.put(ITEMID.RING_OF_RECOIL, Color.RED);
							}
							break;
						case ItemID.FLAMTAER_BRACELET:
							if(config.flamtaer())
							{
								itemsToHighlight.put(ITEMID.FLAMTAER_BRACELET, Color.GREEN);
							}
							break;
						case ItemID.AMULET_OF_CHEMISTRY:
							if(config.chemistry())
							{
								itemsToHighlight.put(ITEMID.AMULET_OF_CHEMISTRY, Color.GREEN);
							}
							break;
						case ItemID.AMULET_OF_BOUNTY:
							if(config.bounty())
							{
								itemsToHighlight.put(ITEMID.AMULET_OF_BOUNTY, Color.YELLOW);
							}
							break;
						case ItemID.NECKLACE_OF_FAITH:
							if(config.faith())
							{
								itemsToHighlight.put(ITEMID.NECKLACE_OF_FAITH, Color.PINK);
							}
							break;
						default:
							break;
					}
				}
			}
		}
	}
	@Subscribe
	public void onGameTick(GameTick gameTick)
	{
		ItemContainer playerEquipment = client.getItemContainer(InventoryID.EQUIPMENT);
	 	hasAmuletEquipped = checkSlot(playerEquipment, EquipmentInventorySlot.AMULET.getSlotIdx());
		hasBraceletEquipped = checkSlot(playerEquipment, EquipmentInventorySlot.BRACELET.getSlotIdx());
		hasRingEquipped = checkSlot(playerEquipment, EquipmentInventorySlot.RING.getSlotIdx());
	}

	public boolean checkSlot(ItemContainer playerEquipment, EquipmentInventorySlot slotType)
	{
		if (playerEquipment != null)
		{
			Item item = playerEquipment.getItem(slotType);
			return item != null;
		}
		return false;
	}
}
