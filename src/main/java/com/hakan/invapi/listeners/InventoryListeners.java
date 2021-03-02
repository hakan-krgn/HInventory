package com.hakan.invapi.listeners;

import com.hakan.invapi.InventoryAPI;
import com.hakan.invapi.customevents.HInventoryClickEvent;
import com.hakan.invapi.customevents.HInventoryCloseEvent;
import com.hakan.invapi.interfaces.Click;
import com.hakan.invapi.inventory.invs.HInventory;
import com.hakan.invapi.inventory.item.ClickableItem;
import com.hakan.invapi.utils.InventoryVariables;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

public class InventoryListeners implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClick().equals(ClickType.UNKNOWN)) {
            event.setCancelled(true);
            return;
        }

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
                } else if (!hInventory.isClickable() && !inventory.equals(hInventory.getInventory())) {
                    event.setCancelled(true);
                    return;
                }

                ClickableItem clickableItem = hInventory.getItem(event.getSlot());
                if (clickableItem != null) {
                    event.setCancelled(true);

                    Click click = clickableItem.getClick();
                    if (click != null) {
                        click.click(event);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player) {
            Player player = (Player) event.getPlayer();
            HInventory hInventory = InventoryAPI.getInventory(player);

            if (hInventory != null) {
                if (hInventory.isCloseable()) {
                    HInventory.Close closeChecker = hInventory.getCloseChecker();
                    if (closeChecker != null) {
                        closeChecker.close(event);
                    }

                    Bukkit.getPluginManager().callEvent(new HInventoryCloseEvent(player, hInventory, event));
                    InventoryVariables.playerInventory.remove(player);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.updateInventory();
                        }
                    }.runTaskLater(InventoryAPI.getInstance(), 1);
                } else {
                    new BukkitRunnable() {
                        public void run() {
                            hInventory.open(player);
                        }
                    }.runTaskLater(InventoryAPI.getInstance(), 1);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (event.getPlayer() instanceof Player) {
            Player player = (Player) event.getPlayer();

            HInventory hInventory = InventoryAPI.getInventory(player);
            if (hInventory != null) {
                InventoryVariables.playerInventory.remove(player);
            }
        }
    }
}