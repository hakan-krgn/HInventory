package com.hakan.invapi.customevents;

import com.hakan.invapi.inventory.invs.HInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;

public class HInventoryClickEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final HInventory hInventory;
    private final InventoryClickEvent event;

    public HInventoryClickEvent(Player player, HInventory hInventory, InventoryClickEvent event) {
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

    public InventoryClickEvent getClickEvent() {
        return event;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}