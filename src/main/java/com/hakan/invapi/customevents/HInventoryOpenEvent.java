package com.hakan.invapi.customevents;

import com.hakan.invapi.inventory.invs.HInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class HInventoryOpenEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final HInventory hInventory;

    public HInventoryOpenEvent(Player player, HInventory hInventory) {
        this.player = player;
        this.hInventory = hInventory;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HInventory getInventory() {
        return hInventory;
    }

    public Player getPlayer() {
        return player;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}