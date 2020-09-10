package net.minestom.vanilla.system;

import net.minestom.server.data.Data;
import net.minestom.server.data.SerializableData;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;

public class EnderChestSystem {

    private static EnderChestSystem INSTANCE;

    private SerializableData inventories = new SerializableData();

    private EnderChestSystem() {}

    public Inventory get(Player player) {
        // TODO: use UUID in future?
        Inventory inv = inventories.get(player.getUuid().toString());
        if(inv == null) {
            inv = new Inventory(InventoryType.CHEST_3_ROW, "EnderChest of "+player.getUsername());
            inventories.set(player.getUsername(), inv, Inventory.class);
        }
        return inv;
    }

    public static EnderChestSystem getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new EnderChestSystem();
        }
        return INSTANCE;
    }
}
