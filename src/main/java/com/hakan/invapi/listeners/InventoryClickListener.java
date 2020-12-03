package com.hakan.invapi.listeners;

import com.hakan.invapi.api.InventoryAPI;
import com.hakan.invapi.customevents.HInventoryClickEvent;
import com.hakan.invapi.interfaces.Click;
import com.hakan.invapi.inventory.invs.HInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            HInventory hInventory = InventoryAPI.getInventory(player);
            if (hInventory != null) {

                Inventory inventory = event.getClickedInventory();
                if (inventory == null) return;
                else if (inventory.equals(player.getOpenInventory().getBottomInventory())) return;
                else if (event.getSlot() < 0) return;

                event.setCancelled(true);

                Click click = hInventory.clickEvents.get(event.getSlot());
                if (click == null) return;
                click.click(event);
                Bukkit.getPluginManager().callEvent(new HInventoryClickEvent(player, hInventory, event));
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (InventoryAPI.getInventory(player) != null) event.setCancelled(true);
        }
    }
}