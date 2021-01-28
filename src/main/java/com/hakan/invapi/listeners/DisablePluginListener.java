package com.hakan.invapi.listeners;

import com.hakan.invapi.InventoryPlugin;
import com.hakan.invapi.api.InventoryAPI;
import com.hakan.invapi.inventory.invs.HInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;

public class DisablePluginListener implements Listener {

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        if (event.getPlugin().equals(InventoryPlugin.getInstance())) {
            InventoryPlugin.setInstance(null);
            for (Player player : Bukkit.getOnlinePlayers()) {
                HInventory hInventory = InventoryAPI.getInventory(player);
                if (hInventory != null) hInventory.close(player);
            }
        }
    }
}