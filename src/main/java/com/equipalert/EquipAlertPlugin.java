package com.equipalert;

import javax.inject.Inject;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.ItemID;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ProfileChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@PluginDescriptor(name = "EquipAlert")
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
	EquipAlertConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(EquipAlertConfig.class);
	}

	@Override
	protected void startUp() throws Exception 
	{
		migrateConfig();
		overlayManager.add(equipAlertHighlightOverlay);
	}

	@Override
	protected void shutDown() throws Exception 
	{
		overlayManager.remove(equipAlertHighlightOverlay);
	}

	@Subscribe
	public void onProfileChanged(ProfileChanged profileChanged) 
	{
		migrateConfig();
	}

	@Subscribe
	public void onGameTick(GameTick gameTick) 
	{
		ItemContainer playerEquipment = client.getItemContainer(InventoryID.EQUIPMENT);
		boolean amuletEquipped = checkSlot(playerEquipment, EquipmentInventorySlot.AMULET.getSlotIdx());
		boolean braceletEquipped = checkSlot(playerEquipment, EquipmentInventorySlot.GLOVES.getSlotIdx());
		boolean ringEquipped = checkSlot(playerEquipment, EquipmentInventorySlot.RING.getSlotIdx());

		updateMap();

		if (amuletEquipped) 
		{
			itemsToHighlight.remove(ItemID.PHOENIX_NECKLACE);
			itemsToHighlight.remove(ItemID.BINDING_NECKLACE);
			itemsToHighlight.remove(ItemID.DODGY_NECKLACE);
			itemsToHighlight.remove(ItemID.AMULET_OF_CHEMISTRY);
			itemsToHighlight.remove(ItemID.AMULET_OF_BOUNTY);
			itemsToHighlight.remove(ItemID.NECKLACE_OF_FAITH);
		}

		if (braceletEquipped) 
		{
			itemsToHighlight.remove(ItemID.BRACELET_OF_SLAUGHTER);
			itemsToHighlight.remove(ItemID.EXPEDITIOUS_BRACELET);
			itemsToHighlight.remove(ItemID.FLAMTAER_BRACELET);
			itemsToHighlight.remove(ItemID.BRACELET_OF_CLAY);
			itemsToHighlight.remove(ItemID.INOCULATION_BRACELET);
		}

		if (ringEquipped) 
		{
			itemsToHighlight.remove(ItemID.RING_OF_RECOIL);
			itemsToHighlight.remove(ItemID.EFARITAYS_AID);
			itemsToHighlight.remove(ItemID.RING_OF_FORGING);
			itemsToHighlight.remove(ItemID.RING_OF_PURSUIT);
		}

		hasAmuletEquipped = amuletEquipped;
		hasBraceletEquipped = braceletEquipped;
		hasRingEquipped = ringEquipped;

	}

	private void updateMap() 
	{
		// grabs the inventory item container
		ItemContainer inventory = client.getItemContainer(InventoryID.INVENTORY);

		if (inventory != null && (!hasAmuletEquipped || !hasBraceletEquipped || !hasRingEquipped)) 
		{
			// resets HashMap
			itemsToHighlight.clear();

			Item[] playerInventory = inventory.getItems();

			for (Item item : playerInventory) 
			{
				switch (item.getId()) 
				{
					case ItemID.PHOENIX_NECKLACE:
						if (config.phoenix()) 
						{
							itemsToHighlight.put(ItemID.PHOENIX_NECKLACE, Color.RED);
						}
						break;
					case ItemID.BINDING_NECKLACE:
						if (config.binding()) 
						{
							itemsToHighlight.put(ItemID.BINDING_NECKLACE, Color.GREEN);
						}
						break;
					case ItemID.BRACELET_OF_SLAUGHTER:
						if (config.slaughter()) 
						{
							itemsToHighlight.put(ItemID.BRACELET_OF_SLAUGHTER, Color.RED);
						}
						break;
					case ItemID.EXPEDITIOUS_BRACELET:
						if (config.expeditious()) 
						{
							itemsToHighlight.put(ItemID.EXPEDITIOUS_BRACELET, Color.YELLOW);
						}
						break;
					case ItemID.DODGY_NECKLACE:
						if (config.dodgy()) 
						{
							itemsToHighlight.put(ItemID.DODGY_NECKLACE, Color.RED);
						}
						break;
					case ItemID.RING_OF_RECOIL:
						if (config.recoil()) 
						{
							itemsToHighlight.put(ItemID.RING_OF_RECOIL, Color.RED);
						}
						break;
					case ItemID.FLAMTAER_BRACELET:
						if (config.flamtaer()) 
						{
							itemsToHighlight.put(ItemID.FLAMTAER_BRACELET, Color.GREEN);
						}
						break;
					case ItemID.AMULET_OF_CHEMISTRY:
						if (config.chemistry()) 
						{
							itemsToHighlight.put(ItemID.AMULET_OF_CHEMISTRY, Color.GREEN);
						}
						break;
					case ItemID.AMULET_OF_BOUNTY:
						if (config.bounty()) 
						{
							itemsToHighlight.put(ItemID.AMULET_OF_BOUNTY, Color.YELLOW);
						}
						break;
					case ItemID.NECKLACE_OF_FAITH:
						if (config.faith()) 
						{
							itemsToHighlight.put(ItemID.NECKLACE_OF_FAITH, Color.PINK);
						}
						break;
					case ItemID.BRACELET_OF_CLAY:
						if (config.clay()) 
						{
							itemsToHighlight.put(ItemID.BRACELET_OF_CLAY, Color.BLUE);
						}
						break;
					case ItemID.EFARITAYS_AID:
						if (config.efaritay()) 
						{
							itemsToHighlight.put(ItemID.EFARITAYS_AID, Color.YELLOW);
						}
						break;
					case ItemID.RING_OF_FORGING:
						if (config.forging()) 
						{
							itemsToHighlight.put(ItemID.RING_OF_FORGING, Color.RED);
						}
						break;
					case ItemID.INOCULATION_BRACELET:
						if (config.inoculation()) 
						{
							itemsToHighlight.put(ItemID.INOCULATION_BRACELET, Color.YELLOW);
						}
						break;
					case ItemID.RING_OF_PURSUIT:
						if (config.pursuit()) 
						{
							itemsToHighlight.put(ItemID.RING_OF_PURSUIT, Color.GREEN);
						}
						break;
					default:
						break;
				}
			}
		}
	}

	private boolean checkSlot(ItemContainer playerEquipment, int slotType) 
	{
		if (playerEquipment != null) 
		{
			Item item = playerEquipment.getItem(slotType);
			return item != null;
		}
		return false;
	}

	private void migrateConfig()
	{
		//Migrates profiles using the previous version of the plugin
		String oldConfigGroup = "pneckReminder";
		String oldKeyName = "neckOfFaith";
		String newConfigGroup = "equipAlert";
		String newKeyName = "faith";

		Boolean oldConfigValue = configManager.getConfiguration(oldConfigGroup, oldKeyName, Boolean.class);
		if (oldConfigValue != null)
		{
			configManager.setConfiguration(newConfigGroup, newKeyName, oldConfigValue);
			configManager.unsetConfiguration(oldConfigGroup, oldKeyName);
		}
	}

	
}
