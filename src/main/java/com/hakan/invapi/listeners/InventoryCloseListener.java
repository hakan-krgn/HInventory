package com.hakan.invapi.listeners;

import com.hakan.invapi.Main;
import com.hakan.invapi.api.InventoryAPI;
import com.hakan.invapi.customevents.HInventoryCloseEvent;
import com.hakan.invapi.inventory.invs.HInventory;
import com.hakan.invapi.other.Variables;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class InventoryCloseListener implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player) {
            Player player = (Player) event.getPlayer();
            HInventory hInventory = InventoryAPI.getInventory(player);
            if (hInventory == null) return;
            if (!hInventory.isCloseable()) {
                new BukkitRunnable() {
                    public void run() {
                        hInventory.open(player);
                    }
                }.runTaskLater(Main.getInstance(), 1);
            } else {
                if (hInventory.closeChecker != null) {
                    hInventory.closeChecker.close(event);
                }
                Bukkit.getPluginManager().callEvent(new HInventoryCloseEvent(player, hInventory, event));
                Variables.getInv.remove(player);
            }
        }
    }
}