package com.hakan.invapi.inventory.invs;

import com.hakan.invapi.Main;
import com.hakan.invapi.api.InventoryAPI;
import com.hakan.invapi.customevents.HInventoryOpenEvent;
import com.hakan.invapi.interfaces.Click;
import com.hakan.invapi.interfaces.Update;
import com.hakan.invapi.inventory.item.ClickableItem;
import com.hakan.invapi.other.Variables;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class HInventory {

    private final Inventory bukkitInventory;
    private final String title;
    private final InventoryType inventoryType;
    public Close closeChecker;
    public HashMap<Integer, Click> clickEvents = new HashMap<>();
    private String id;
    private boolean closeable;

    public HInventory(String title, InventoryType inventoryType, int size, String id, boolean closeable) {
        setId(id);
        setCloseable(closeable);
        this.title = title;
        this.inventoryType = inventoryType;
        if (inventoryType.equals(InventoryType.CHEST)) {
            this.bukkitInventory = Bukkit.createInventory(null, size * 9, title);
        } else {
            this.bukkitInventory = Bukkit.createInventory(null, inventoryType, title);
        }
    }

    public void open(Player player) {
        if (player == null) return;
        player.openInventory(this.bukkitInventory);
        Variables.getInv.put(player, this);
        Bukkit.getPluginManager().callEvent(new HInventoryOpenEvent(player, this));
    }

    public void close(Player player) {
        if (player == null) return;
        this.closeable = true;
        player.closeInventory();
    }

    public void update(Player player, int runLater, int period, Update update) {
        final BukkitTask[] bukkitTask = {null};
        bukkitTask[0] = new BukkitRunnable() {
            public void run() {
                if (InventoryAPI.getInventory(player) == null) {
                    cancel();
                    return;
                }
                update.update(bukkitTask[0]);
            }
        }.runTaskTimer(Main.getInstance(), runLater, period);
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

    public org.bukkit.inventory.Inventory toInventory() {
        return this.bukkitInventory;
    }

    public void setItem(int slot, ClickableItem clickableItem) {
        ItemStack itemStack = clickableItem.getItem();
        this.clickEvents.put(slot, clickableItem.getClick());
        this.bukkitInventory.setItem(slot, itemStack);
    }

    public HInventory whenClosed(Close close) {
        this.closeChecker = close;
        return this;
    }

    public HInventory clone() {
        return new HInventory(getTitle(), getInventoryType(), getSize(), getId() + "_clone", isCloseable());
    }

    public Pagination getPagination() {
        return new Pagination(this);
    }

    public interface Close {
        void close(InventoryCloseEvent event);
    }
}