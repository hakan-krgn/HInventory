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

                Inventory inventory = event.getClickedInventory();
                if (inventory == null) {
                    return;
                } else if (inventory.equals(player.getOpenInventory().getBottomInventory())) {
                    ItemStack currentItem = event.getCurrentItem();
                    if (currentItem != null && !currentItem.getType().equals(Material.AIR)) {
                        event.setCancelled(true);
                    }
                    return;
                } else if (event.getSlot() < 0) {
                    return;
                }

                Bukkit.getPluginManager().callEvent(new HInventoryClickEvent(player, hInventory, event));

                ClickableItem clickableItem = hInventory.getItem(event.getSlot());
                if (clickableItem == null) return;
                event.setCancelled(true);
                Click click = clickableItem.getClick();
                if (click == null) return;
                click.click(event);
            }
        }
    }
}