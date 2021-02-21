# InventoryAPI

**Create Inventory**

```java
Player player = Bukkit.getPlayer("playerName");

HInventory hInventory = InventoryAPI.getInventoryManager().setTitle("aa").setCloseable(false).setSize(3).setId("bb").create();

hInventory.setItem(11, ClickableItem.of(new ItemStack(Material.APPLE), (event) -> {
    player.playSound(player.getLocation(), Sound.VILLAGER_IDLE, 1, 1);
    player.sendMessage("§enormal item !");
}));

hInventory.setItem(13, ClickableItem.of(new ItemStack(Material.DIAMOND), (event) -> {
    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
    player.sendMessage("§agood item !");
    hInventory.close(player);
}));

hInventory.setItem(15, ClickableItem.of(new ItemStack(Material.MAP), (event) -> {
    player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1, 1);
    player.sendMessage("§cbad item !");
}));

hInventory.open(player);
```

**Page System**

```java
Player player = Bukkit.getPlayer("playerName");

HInventory hInventory = InventoryAPI.getInventoryManager().setTitle("a").setCloseable(false).setSize(5).setId("b").create();

Pagination pagination = hInventory.getPagination();

pagination.setItemSlots(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35));

List<ClickableItem> clickableItemList = new ArrayList<>();
for (int x = 1; x < 903; x++) {
    int finalX = x;
    clickableItemList.add(ClickableItem.of(new ItemStack(Material.DIAMOND), (event) -> {
        Bukkit.broadcastMessage(finalX + "");
    }));
}
pagination.setItems(clickableItemList);

hInventory.setItem(38, ClickableItem.of(new ItemStack(Material.ARROW), (event) -> {
    player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
    pagination.previousPage();
}));

hInventory.setItem(40, ClickableItem.of(new ItemStack(Material.BARRIER), (event) -> {
    player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
    hInventory.close(player);
}));

hInventory.setItem(42, ClickableItem.of(new ItemStack(Material.ARROW), (event) -> {
    player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
    pagination.nextPage();
}));

hInventory.open(player);
```

~~-----------------------------------------------------------------------~~

*If you want to use it without putting it into plugins folder, add*

```java
InventoryAPI.setupInvs(this);
```

*to onEnable of your plugin.*

**Maven**

[![](https://jitpack.io/v/hakan-krgn/HInventory.svg)](https://jitpack.io/#hakan-krgn/HInventory)

```xml
<dependency>
    <groupId>com.github.hakan-krgn</groupId>
    <artifactId>HInventory</artifactId>
    <version>10.0.0</version>
</dependency>

<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```
