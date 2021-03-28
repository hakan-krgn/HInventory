package com.hakan.invapi.inventory.invs;

import com.hakan.invapi.InventoryAPI;
import com.hakan.invapi.customevents.HInventoryOpenEvent;
import com.hakan.invapi.interfaces.Update;
import com.hakan.invapi.inventory.item.ClickableItem;
import com.hakan.invapi.utils.InventoryVariables;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class HInventory implements InventoryHolder {

    private final Inventory bukkitInventory;
    private final String title;
    private final InventoryType inventoryType;
    private final HashMap<Integer, ClickableItem> clickableItems = new HashMap<>();
    private Close closeChecker;
    private String id;
    private boolean closeable;
    private boolean clickable;

    public HInventory(String title, InventoryType inventoryType, int size, String id, boolean closeable, boolean clickable) {
        this.id = id;
        this.closeable = closeable;
        this.clickable = clickable;
        this.title = title;
        this.inventoryType = inventoryType;
        if (inventoryType.equals(InventoryType.CHEST)) {
            this.bukkitInventory = Bukkit.createInventory(this, size * 9, title);
        } else {
            this.bukkitInventory = Bukkit.createInventory(this, inventoryType, title);
        }
    }

    public HInventory() {
        this("", InventoryType.CHEST, 1, "default_inventory", true, false);
    }

    public void open(Player player) {
        if (player == null) return;

        HInventory hInventory = this;
        Listener openListener = new Listener() {
            @EventHandler
            public void onInventoryOpen(InventoryOpenEvent event) {
                Bukkit.getPluginManager().callEvent(new HInventoryOpenEvent(player, hInventory, event));
            }
        };
        Bukkit.getPluginManager().registerEvents(openListener, InventoryAPI.getInstance());

        player.openInventory(this.bukkitInventory);
        InventoryVariables.playerInventory.put(player, this);

        InventoryOpenEvent.getHandlerList().unregister(openListener);
    }

    public void close(Player player) {
        if (player == null) return;
        this.closeable = true;
        player.closeInventory();
    }

    public void update(Player player, int runLater, int period, Update update) {
        final BukkitTask[] bukkitTask = {null};
        if (InventoryAPI.getInstance() != null) {
            bukkitTask[0] = new BukkitRunnable() {
                public void run() {
                    if (InventoryAPI.getInventory(player) == null || InventoryAPI.getInstance() == null) {
                        cancel();
                        return;
                    }
                    update.update(bukkitTask[0]);
                }
            }.runTaskTimer(InventoryAPI.getInstance(), runLater, period);
        }
    }

    public void guiFill(ClickableItem clickableItem) {
        for (int slot = 0; slot < this.getSize() * 9; slot++) {
            if (getItem(slot) != null) continue;
            this.setItem(slot, clickableItem);
        }
    }

    public void guiFill(ItemStack itemStack) {
        guiFill(ClickableItem.empty(itemStack));
    }

    public void guiAir() {
        guiFill(new ItemStack(Material.AIR));
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSize() {
        return this.bukkitInventory.getSize() / 9;
    }

    public String getTitle() {
        return this.title;
    }

    public InventoryType getInventoryType() {
        return this.inventoryType;
    }

    public boolean isCloseable() {
        return this.closeable;
    }

    public void setCloseable(boolean closeable) {
        this.closeable = closeable;
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    @Override
    public Inventory getInventory() {
        return this.bukkitInventory;
    }

    public void setItem(int slot, ClickableItem clickableItem) {
        if (clickableItem.getItem() == null || clickableItem.getItem().getType().equals(Material.AIR)) {
            return;
        }
        this.clickableItems.put(slot, clickableItem);
        this.bukkitInventory.setItem(slot, clickableItem.getItem());
    }

    public Close getCloseChecker() {
        return this.closeChecker;
    }

    public ClickableItem getItem(int slot) {
        return this.clickableItems.getOrDefault(slot, null);
    }

    public HInventory whenClosed(Close close) {
        this.closeChecker = close;
        return this;
    }

    public HInventory clone() {
        return new HInventory(getTitle(), getInventoryType(), getSize(), getId() + "_clone", isCloseable(), isClickable());
    }

    public Pagination getPagination() {
        return new Pagination(this);
    }

    public interface Close {
        void close(InventoryCloseEvent event);
    }
}