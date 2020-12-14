package com.hakan.invapi.listeners;

import com.hakan.invapi.api.InventoryAPI;
import com.hakan.invapi.customevents.HInventoryOpenEvent;
import com.hakan.invapi.inventory.invs.HInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class InventoryOpenListener implements Listener {

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (event.getPlayer() instanceof Player) {
            Player player = (Player) event.getPlayer();
            HInventory hInventory = InventoryAPI.getInventory(player);
            if (hInventory == null) return;
            Bukkit.getPluginManager().callEvent(new HInventoryOpenEvent(player, hInventory, event));
        }
    }
}