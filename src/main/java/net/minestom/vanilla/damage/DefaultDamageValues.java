package net.minestom.vanilla.damage;

import net.minestom.server.item.Material;

import java.util.HashMap;

public class DefaultDamageValues {

    private final static HashMap<Material, WeaponStats> damageValues = new HashMap<>();

    static {
        damageValues.put(Material.WOODEN_SWORD, new WeaponStats(5F, 1.6F));
        damageValues.put(Material.STONE_SWORD, new WeaponStats(6F, 1.6F));
        damageValues.put(Material.GOLDEN_SWORD, new WeaponStats(5F, 1.6F));
        damageValues.put(Material.IRON_SWORD, new WeaponStats(7F, 1.6F));
        damageValues.put(Material.DIAMOND_SWORD, new WeaponStats(8F, 1.6F));
        damageValues.put(Material.NETHERITE_SWORD, new WeaponStats(9F, 1.6F));

        damageValues.put(Material.WOODEN_AXE, new WeaponStats(8F, 0.8F));
        damageValues.put(Material.STONE_AXE, new WeaponStats(10F, 0.8F));
        damageValues.put(Material.GOLDEN_AXE, new WeaponStats(8F, 1F));
        damageValues.put(Material.IRON_AXE, new WeaponStats(10F, 0.9F));
        damageValues.put(Material.DIAMOND_AXE, new WeaponStats(10F, 1F));
        damageValues.put(Material.NETHERITE_AXE, new WeaponStats(11F, 1F));

        damageValues.put(Material.WOODEN_PICKAXE, new WeaponStats(3F, 1.2F));
        damageValues.put(Material.STONE_PICKAXE, new WeaponStats(4F, 1.2F));
        damageValues.put(Material.GOLDEN_PICKAXE, new WeaponStats(3F, 1.2F));
        damageValues.put(Material.IRON_PICKAXE, new WeaponStats(5F, 1.2F));
        damageValues.put(Material.DIAMOND_PICKAXE, new WeaponStats(6F, 1.2F));
        damageValues.put(Material.NETHERITE_PICKAXE, new WeaponStats(7F, 1.2F));

        damageValues.put(Material.WOODEN_SHOVEL, new WeaponStats(3.5F, 1F));
        damageValues.put(Material.STONE_SHOVEL, new WeaponStats(4.5F, 1F));
        damageValues.put(Material.GOLDEN_SHOVEL, new WeaponStats(3.5F, 1F));
        damageValues.put(Material.IRON_SHOVEL, new WeaponStats(5.5F, 1F));
        damageValues.put(Material.DIAMOND_SHOVEL, new WeaponStats(6.5F, 1F));
        damageValues.put(Material.NETHERITE_SHOVEL, new WeaponStats(7.5F, 1F));

        damageValues.put(Material.WOODEN_HOE, new WeaponStats(2F, 1F));
        damageValues.put(Material.STONE_HOE, new WeaponStats(2F, 2F));
        damageValues.put(Material.GOLDEN_HOE, new WeaponStats(2F, 1F));
        damageValues.put(Material.IRON_HOE, new WeaponStats(2F, 3F));
        damageValues.put(Material.DIAMOND_HOE, new WeaponStats(2F, 4F));
        damageValues.put(Material.NETHERITE_HOE, new WeaponStats(2F, 4F));

        damageValues.put(Material.TRIDENT, new WeaponStats(10F, 1.1F));
    }

    public static HashMap<Material, WeaponStats> getDamageValues() {
        return damageValues;
    }

}
