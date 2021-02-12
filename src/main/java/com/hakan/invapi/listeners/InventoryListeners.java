package com.hakan.invapi.listeners;

import com.hakan.invapi.InventoryPlugin;
import com.hakan.invapi.api.InventoryAPI;
import com.hakan.invapi.customevents.HInventoryClickEvent;
import com.hakan.invapi.customevents.HInventoryCloseEvent;
import com.hakan.invapi.customevents.HInventoryOpenEvent;
import com.hakan.invapi.interfaces.Click;
import com.hakan.invapi.inventory.invs.HInventory;
import com.hakan.invapi.inventory.item.ClickableItem;
import com.hakan.invapi.other.Variables;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class InventoryListeners implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (event.getClick().equals(ClickType.UNKNOWN)) {
                event.setCancelled(true);
                return;
            }
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
                }.runTaskLater(InventoryPlugin.getInstance(), 1);
            } else {
                HInventory.Close close = hInventory.closeChecker;
                if (close != null) {
                    close.close(event);
                }
                Bukkit.getPluginManager().callEvent(new HInventoryCloseEvent(player, hInventory, event));
                Variables.getInv.remove(player);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.updateInventory();
                    }
                }.runTaskLater(InventoryPlugin.getInstance(), 1);
            }
        }
    }

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