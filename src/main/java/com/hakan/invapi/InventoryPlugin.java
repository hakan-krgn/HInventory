package com.hakan.invapi;

import com.hakan.invapi.listeners.DisablePluginListener;
import com.hakan.invapi.listeners.InventoryClickListener;
import com.hakan.invapi.listeners.InventoryCloseListener;
import com.hakan.invapi.listeners.InventoryOpenListener;
import org.bukkit.Bukkit;
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
        pm.registerEvents(new InventoryClickListener(), plugin);
        pm.registerEvents(new InventoryCloseListener(), plugin);
        pm.registerEvents(new InventoryOpenListener(), plugin);
    }

    @Override
    public void onEnable() {
        setup(this);
    }
}