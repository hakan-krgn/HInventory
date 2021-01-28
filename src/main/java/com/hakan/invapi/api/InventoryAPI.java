package com.hakan.invapi.api;

import com.hakan.invapi.InventoryPlugin;
import com.hakan.invapi.inventory.invs.HInventory;
import com.hakan.invapi.other.Variables;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;

public class InventoryAPI {

    public InventoryAPI() {
    }

    public static void setupInvs(Plugin plugin) {
        InventoryPlugin.setup(plugin);
    }

    public static HInventory getInventory(Player player) {
        return Variables.getInv.get(player);
    }

    public static String getId(Player player) {
        HInventory hInventory = Variables.getInv.get(player);
        return hInventory != null ? hInventory.getId() : "__there_is_no_inventory__";
    }

    public static InventoryManager getInventoryManager() {
        return new InventoryManager();
    }

    public static class InventoryManager {

        private String title = "";
        private InventoryType inventoryType = InventoryType.CHEST;
        private int size = 6;
        private boolean closeable = true;
        private String id = "";
        private boolean clickable = false;

        public InventoryManager() {
        }

        public InventoryManager setTitle(String title) {
            this.title = title;
            return this;
        }

        public InventoryManager setSize(int size) {
            this.size = size;
            return this;
        }

        public InventoryManager setId(String id) {
            this.id = id;
            return this;
        }

        public InventoryManager setCloseable(boolean closeable) {
            this.closeable = closeable;
            return this;
        }

        public InventoryManager setInventoryType(InventoryType inventoryType) {
            this.inventoryType = inventoryType;
            return this;
        }

        public InventoryManager setClickable(boolean clickable) {
            this.clickable = clickable;
            return this;
        }

        public HInventory create() {
            return new HInventory(ChatColor.translateAlternateColorCodes('&', this.title), this.inventoryType, this.size, this.id, this.closeable, this.clickable);
        }
    }
}