package com.hakan.invapi.listeners;

import com.hakan.invapi.api.InventoryAPI;
import com.hakan.invapi.customevents.HInventoryClickEvent;
import com.hakan.invapi.interfaces.Click;
import com.hakan.invapi.inventory.invs.HInventory;
import com.hakan.invapi.inventory.item.ClickableItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            HInventory hInventory = InventoryAPI.getInventory(player);
            if (hInventory != null) {

                HInventoryClickEvent hInventoryClickEvent = new HInventoryClickEvent(player, hInventory, event);
                Bukkit.getPluginManager().callEvent(hInventoryClickEvent);
                if (hInventoryClickEvent.isCancelled()) {
                    event.setCancelled(true);
                    return;
                }

                Inventory inventory = event.getClickedInventory();

                if (inventory == null || event.getSlot() < 0) {
                    return;
                } else if (inventory.equals(player.getOpenInventory().getBottomInventory())) {
                    if (!hInventory.isClickable()) {
                        ItemStack currentItem = event.getCurrentItem();
                        if (currentItem != null && !currentItem.getType().equals(Material.AIR)) {
                            event.setCancelled(true);
                        }
                    }
                    return;
                }

                ClickableItem clickableItem = hInventory.getItem(event.getSlot());
                if (clickableItem == null) {
                    return;
                }

                event.setCancelled(true);

                Click click = clickableItem.getClick();
                if (click != null) {
                    click.click(event);
                }
            }
        }
    }
}