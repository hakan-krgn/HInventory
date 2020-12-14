package com.hakan.invapi.customevents;

import com.hakan.invapi.inventory.invs.HInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class HInventoryOpenEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final HInventory hInventory;
    private final InventoryOpenEvent event;

    public HInventoryOpenEvent(Player player, HInventory hInventory, InventoryOpenEvent event) {
        this.player = player;
        this.hInventory = hInventory;
        this.event = event;
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

    public InventoryOpenEvent getOpenEvent() {
        return event;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}