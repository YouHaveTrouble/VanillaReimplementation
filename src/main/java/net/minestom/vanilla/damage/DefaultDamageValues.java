package net.minestom.vanilla.damage;

import net.minestom.server.item.Material;

import java.util.HashMap;

public class DefaultDamageValues {

    private final static HashMap<Material, WeaponStats> vanillaDamageValues = new HashMap<>();

    static {
        vanillaDamageValues.put(Material.WOODEN_SWORD, new WeaponStats(4F, 1.6F));
        vanillaDamageValues.put(Material.STONE_SWORD, new WeaponStats(5F, 1.6F));
        vanillaDamageValues.put(Material.GOLDEN_SWORD, new WeaponStats(4F, 1.6F));
        vanillaDamageValues.put(Material.IRON_SWORD, new WeaponStats(6F, 1.6F));
        vanillaDamageValues.put(Material.DIAMOND_SWORD, new WeaponStats(7F, 1.6F));
        vanillaDamageValues.put(Material.NETHERITE_SWORD, new WeaponStats(8F, 1.6F));

        vanillaDamageValues.put(Material.WOODEN_AXE, new WeaponStats(7F, 0.8F));
        vanillaDamageValues.put(Material.STONE_AXE, new WeaponStats(9F, 0.8F));
        vanillaDamageValues.put(Material.GOLDEN_AXE, new WeaponStats(7F, 1F));
        vanillaDamageValues.put(Material.IRON_AXE, new WeaponStats(9F, 0.9F));
        vanillaDamageValues.put(Material.DIAMOND_AXE, new WeaponStats(9F, 1F));
        vanillaDamageValues.put(Material.NETHERITE_AXE, new WeaponStats(10F, 1F));

        vanillaDamageValues.put(Material.WOODEN_PICKAXE, new WeaponStats(2F, 1.2F));
        vanillaDamageValues.put(Material.STONE_PICKAXE, new WeaponStats(3F, 1.2F));
        vanillaDamageValues.put(Material.GOLDEN_PICKAXE, new WeaponStats(2F, 1.2F));
        vanillaDamageValues.put(Material.IRON_PICKAXE, new WeaponStats(4F, 1.2F));
        vanillaDamageValues.put(Material.DIAMOND_PICKAXE, new WeaponStats(5F, 1.2F));
        vanillaDamageValues.put(Material.NETHERITE_PICKAXE, new WeaponStats(6F, 1.2F));

        vanillaDamageValues.put(Material.WOODEN_SHOVEL, new WeaponStats(2.5F, 1F));
        vanillaDamageValues.put(Material.STONE_SHOVEL, new WeaponStats(3.5F, 1F));
        vanillaDamageValues.put(Material.GOLDEN_SHOVEL, new WeaponStats(2.5F, 1F));
        vanillaDamageValues.put(Material.IRON_SHOVEL, new WeaponStats(4.5F, 1F));
        vanillaDamageValues.put(Material.DIAMOND_SHOVEL, new WeaponStats(5.5F, 1F));
        vanillaDamageValues.put(Material.NETHERITE_SHOVEL, new WeaponStats(6.5F, 1F));

        vanillaDamageValues.put(Material.WOODEN_HOE, new WeaponStats(1F, 1F));
        vanillaDamageValues.put(Material.STONE_HOE, new WeaponStats(1F, 2F));
        vanillaDamageValues.put(Material.GOLDEN_HOE, new WeaponStats(1F, 1F));
        vanillaDamageValues.put(Material.IRON_HOE, new WeaponStats(1F, 3F));
        vanillaDamageValues.put(Material.DIAMOND_HOE, new WeaponStats(1F, 4F));
        vanillaDamageValues.put(Material.NETHERITE_HOE, new WeaponStats(1F, 4F));

        vanillaDamageValues.put(Material.TRIDENT, new WeaponStats(9F, 1.1F));
    }

    public static HashMap<Material, WeaponStats> getVanillaDamageValues() {
        return vanillaDamageValues;
    }

}
