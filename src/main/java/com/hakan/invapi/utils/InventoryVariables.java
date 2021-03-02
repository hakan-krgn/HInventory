package com.hakan.invapi.utils;

import com.hakan.invapi.inventory.invs.HInventory;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class InventoryVariables {

    public static Map<Player, HInventory> playerInventory = new HashMap<>();

}