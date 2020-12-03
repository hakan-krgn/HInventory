package com.hakan.invapi.inventory.item;

import com.hakan.invapi.interfaces.Click;
import org.bukkit.inventory.ItemStack;

public class ClickableItem {

    private ItemStack item;
    private Click click;

    private ClickableItem(ItemStack item, Click click) {
        this.item = item;
        this.click = click;
    }

    public ClickableItem() {
    }

    public static ClickableItem of(ItemStack item, Click click) {
        return new ClickableItem(item, click);
    }

    public static ClickableItem empty(ItemStack item) {
        return new ClickableItem(item, (event) -> {
        });
    }

    public ItemStack getItem() {
        return item;
    }

    public Click getClick() {
        return click;
    }
}