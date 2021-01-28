package com.hakan.invapi.inventory.invs;

import com.hakan.invapi.inventory.item.ClickableItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Pagination {

    private final HInventory hInventory;
    private List<Integer> itemSlots;
    private List<ClickableItem> clickableItems;
    private int page;

    Pagination(HInventory hInventory) {
        this.page = 0;
        this.hInventory = hInventory;
        this.clickableItems = new ArrayList<>();
        this.itemSlots = new ArrayList<>();
    }

    public void setItems(List<ClickableItem> clickableItems) {
        this.clickableItems = clickableItems;
        updateInventory();
    }

    public void nextPage() {
        setPage(getPage() + 1);
    }

    public void previousPage() {
        setPage(getPage() - 1);
    }

    public void firstPage() {
        setPage(0);
    }

    public void lastPage() {
        setPage(getLastPage());
    }

    private boolean updateInventory() {
        List<ClickableItem> clickableItems = getClickableItems();
        List<Integer> itemSlots = getItemSlots();

        int clickableItemSize = clickableItems.size();
        int itemSlotSize = itemSlots.size();
        int page = getPage();

        int first = page * itemSlotSize;
        int last = (page + 1) * itemSlotSize;
        if (clickableItemSize <= first) return false;
        if (first < 0) return false;

        HInventory hInventory = getInventory();

        int m = 0;
        for (; first < last; first++) {
            ClickableItem clickableItem;
            if (clickableItemSize > first) clickableItem = clickableItems.get(first);
            else clickableItem = ClickableItem.empty(new ItemStack(Material.AIR));
            hInventory.setItem(itemSlots.get(m), clickableItem);
            m++;
        }
        return true;
    }

    public HInventory getInventory() {
        return hInventory;
    }

    public List<Integer> getItemSlots() {
        return itemSlots;
    }

    public void setItemSlots(List<Integer> ints) {
        this.itemSlots = ints;
        updateInventory();
    }

    public void setItemSlots(int min, int max) {
        List<Integer> itemSlots = new ArrayList<>();
        for (; min <= max; min++) {
            itemSlots.add(min);
        }
        setItemSlots(itemSlots);
    }

    public List<ClickableItem> getClickableItems() {
        return clickableItems;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        if (getClickableItems().size() == 0) return;
        else if (getInventory() == null) return;
        else if (getItemSlots().size() == 0) return;

        int oldPage = this.page;
        this.page = page;
        boolean isPassed = updateInventory();
        if (!isPassed) {
            this.page = oldPage;
        }
    }

    public int getLastPage() {
        int m = (int) Math.ceil((double) getClickableItems().size() / getItemSlots().size()) - 1;
        return m != -1 ? m : 0;
    }

    public int getFirstPage() {
        return 0;
    }

    public boolean isLastPage() {
        return getPage() == getLastPage();
    }

    public boolean isFirstPage() {
        return getPage() == 0;
    }
}