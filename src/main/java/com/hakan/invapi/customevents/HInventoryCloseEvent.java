package com.hakan.invapi.customevents;

import com.hakan.invapi.inventory.invs.HInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class HInventoryCloseEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final HInventory hInventory;
    private final InventoryCloseEvent event;

    public HInventoryCloseEvent(Player player, HInventory hInventory, InventoryCloseEvent event) {
        this.player = player;
        this.hInventory = hInventory;
        this.event = event;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }

    public HInventory getInventory() {
        return hInventory;
    }

    public InventoryCloseEvent getCloseEvent() {
        return event;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}