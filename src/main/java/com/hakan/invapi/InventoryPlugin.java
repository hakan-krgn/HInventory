package com.hakan.invapi;

import com.hakan.invapi.api.InventoryAPI;
import com.hakan.invapi.inventory.invs.HInventory;
import com.hakan.invapi.inventory.item.ClickableItem;
import com.hakan.invapi.listeners.DisablePluginListener;
import com.hakan.invapi.listeners.InventoryListeners;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class InventoryPlugin extends JavaPlugin {

    private static Plugin instance;

    public static Plugin getInstance() {
        return instance;
    }

    public static void setInstance(Plugin plugin) {
        instance = plugin;
    }

    public static void setup(Plugin plugin) {
        if (instance != null) {
            Bukkit.getLogger().warning("InventoryAPI already registered !");
            return;
        }
        setInstance(plugin);
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new DisablePluginListener(), plugin);
        pm.registerEvents(new InventoryListeners(), plugin);
    }

    @Override
    public void onEnable() {
        setup(this);

        HInventory hInventory = InventoryAPI.getInventoryManager().setSize(2).setTitle("sa").setInventoryType(InventoryType.CHEST).setId("sa").setCloseable(true).setClickable(true).create();

        hInventory.setItem(0, ClickableItem.of(new ItemStack(Material.DIAMOND), event -> {
            Bukkit.broadcastMessage(event.getClick() + "");
        }));

        hInventory.open(Bukkit.getPlayerExact("blueybighats"));
    }
}