package net.minestom.vanilla.items;

import net.minestom.server.data.Data;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.entity.damage.DamageType;
import net.minestom.server.event.entity.EntityAttackEvent;
import net.minestom.server.event.player.PlayerBlockInteractEvent;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.event.player.PlayerUseItemOnBlockEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.CustomBlock;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.network.ConnectionManager;
import net.minestom.server.utils.BlockPosition;
import net.minestom.vanilla.blocks.VanillaBlocks;

import java.util.function.Supplier;

/**
 * All items with special behaviour available in the vanilla reimplementation
 */
public enum VanillaItems {

    WHITE_BED(() -> new BedItem(Material.WHITE_BED, VanillaBlocks.WHITE_BED)),
    BLACK_BED(() -> new BedItem(Material.BLACK_BED, VanillaBlocks.BLACK_BED)),
    LIGHT_BLUE_BED(() -> new BedItem(Material.LIGHT_BLUE_BED, VanillaBlocks.LIGHT_BLUE_BED)),
    BLUE_BED(() -> new BedItem(Material.BLUE_BED, VanillaBlocks.BLUE_BED)),
    RED_BED(() -> new BedItem(Material.RED_BED, VanillaBlocks.RED_BED)),
    GREEN_BED(() -> new BedItem(Material.GREEN_BED, VanillaBlocks.GREEN_BED)),
    YELLOW_BED(() -> new BedItem(Material.YELLOW_BED, VanillaBlocks.YELLOW_BED)),
    PURPLE_BED(() -> new BedItem(Material.PURPLE_BED, VanillaBlocks.PURPLE_BED)),
    MAGENTA_BED(() -> new BedItem(Material.MAGENTA_BED, VanillaBlocks.MAGENTA_BED)),
    CYAN_BED(() -> new BedItem(Material.CYAN_BED, VanillaBlocks.CYAN_BED)),
    PINK_BED(() -> new BedItem(Material.PINK_BED, VanillaBlocks.PINK_BED)),
    GRAY_BED(() -> new BedItem(Material.GRAY_BED, VanillaBlocks.GRAY_BED)),
    LIGHT_GRAY_BED(() -> new BedItem(Material.LIGHT_GRAY_BED, VanillaBlocks.LIGHT_GRAY_BED)),
    ORANGE_GRAY_BED(() -> new BedItem(Material.ORANGE_BED, VanillaBlocks.ORANGE_BED)),
    BROWN_BED(() -> new BedItem(Material.BROWN_BED, VanillaBlocks.BROWN_BED)),
    LIME_BED(() -> new BedItem(Material.LIME_BED, VanillaBlocks.LIME_BED)),

    FLINT_AND_STEEL(FlintAndSteel::new),

    WOODEN_SWORD(() -> new WeaponItem(Material.WOODEN_SWORD, 5F, 1.6F)),
    STONE_SWORD(() -> new WeaponItem(Material.STONE_SWORD, 6F, 1.6F)),
    GOLDEN_SWORD(() -> new WeaponItem(Material.GOLDEN_SWORD, 5F, 1.6F)),
    IRON_SWORD(() -> new WeaponItem(Material.IRON_SWORD, 7F, 1.6F)),
    DIAMOND_SWORD(() -> new WeaponItem(Material.DIAMOND_SWORD, 8F, 1.6F)),
    NETHERITE_SWORD(() -> new WeaponItem(Material.NETHERITE_SWORD, 9F, 1.6F)),

    WOODEN_SHOVEL(() -> new WeaponItem(Material.WOODEN_SHOVEL, 3.5F, 1F)),
    STONE_SHOVEL(() -> new WeaponItem(Material.STONE_SHOVEL, 4.5F, 1F)),
    GOLDEN_SHOVEL(() -> new WeaponItem(Material.GOLDEN_SHOVEL, 3.5F, 1F)),
    IRON_SHOVEL(() -> new WeaponItem(Material.IRON_SHOVEL, 5.5F, 1F)),
    DIAMOND_SHOVEL(() -> new WeaponItem(Material.DIAMOND_SHOVEL, 6.5F, 1F)),
    NETHERITE_SHOVEL(() -> new WeaponItem(Material.NETHERITE_SHOVEL, 7.5F, 1F)),

    WOODEN_PICKAXE(() -> new WeaponItem(Material.WOODEN_PICKAXE, 3F, 1.2F)),
    STONE_PICKAXE(() -> new WeaponItem(Material.STONE_PICKAXE, 4F, 1.2F)),
    GOLDEN_PICKAXE(() -> new WeaponItem(Material.GOLDEN_PICKAXE, 3F, 1.2F)),
    IRON_PICKAXE(() -> new WeaponItem(Material.IRON_PICKAXE, 5F, 1.2F)),
    DIAMOND_PICKAXE(() -> new WeaponItem(Material.DIAMOND_PICKAXE, 6F, 1.2F)),
    NETHERITE_PICKAXE(() -> new WeaponItem(Material.NETHERITE_PICKAXE, 7F, 1.2F)),

    WOODEN_AXE(() -> new WeaponItem(Material.WOODEN_AXE, 8F, 0.8F)),
    STONE_AXE(() -> new WeaponItem(Material.STONE_AXE, 10F, 1F)),
    GOLDEN_AXE(() -> new WeaponItem(Material.GOLDEN_AXE, 8F, 1F)),
    IRON_AXE(() -> new WeaponItem(Material.IRON_AXE, 10F, 0.9F)),
    DIAMOND_AXE(() -> new WeaponItem(Material.DIAMOND_AXE, 10F, 1F)),
    NETHERITE_AXE(() -> new WeaponItem(Material.NETHERITE_AXE, 11F, 1F)),

    WOODEN_HOE(() -> new WeaponItem(Material.WOODEN_HOE, 2F, 1F)),
    STONE_HOE(() -> new WeaponItem(Material.STONE_HOE, 2F, 2F)),
    GOLDEN_HOE(() -> new WeaponItem(Material.GOLDEN_HOE, 2F, 1F)),
    IRON_HOE(() -> new WeaponItem(Material.IRON_HOE, 2F, 3F)),
    DIAMOND_HOE(() -> new WeaponItem(Material.DIAMOND_HOE, 2F, 4F)),
    NETHERITE_HOE(() -> new WeaponItem(Material.NETHERITE_HOE, 2F, 4F)),

    TRIDENT(() -> new WeaponItem(Material.TRIDENT, 10F, 1.1F));

    private final Supplier<VanillaItem> itemCreator;

    private VanillaItems(Supplier<VanillaItem> itemCreator) {
        this.itemCreator = itemCreator;
    }

    /**
     * Register all vanilla items into the given manager
     * @param connectionManager used to add events to new players
     */
    public static void registerAll(ConnectionManager connectionManager) {
        connectionManager.addPlayerInitialization(player -> {
            for (VanillaItems itemDescription : values()) {
                VanillaItem item = itemDescription.itemCreator.get();
                player.addEventCallback(PlayerUseItemEvent.class, event -> {
                    if(event.getItemStack().getMaterial() == item.getMaterial()) {
                        item.onUseInAir(player, event.getItemStack(), event.getHand());
                    }
                });

                player.addEventCallback(PlayerBlockInteractEvent.class, event -> {
                    Instance instance = player.getInstance();
                    BlockPosition blockPosition = event.getBlockPosition();

                    // logic from Minestom core, allows containers to be opened even if the item has a use
                    CustomBlock customBlock = instance.getCustomBlock(blockPosition);
                    if (customBlock != null) {
                        Data data = instance.getBlockData(blockPosition);
                        boolean blocksItem = customBlock.onInteract(player, event.getHand(), blockPosition, data);
                        if(blocksItem) {
                            event.setBlockingItemUse(true);
                            event.setCancelled(true);
                        }
                    }

                    if(!event.isCancelled()) {
                        ItemStack itemStack = player.getItemInHand(event.getHand());
                        if(itemStack.getMaterial() == item.getMaterial()) {
                            if(item.onUseOnBlock(player, itemStack, event.getHand(), event.getBlockPosition(), event.getBlockFace().toDirection())) {
                                // prevent block placement
                                event.setBlockingItemUse(true);
                                event.setCancelled(true);
                            }
                        }
                    }
                });

                player.addEventCallback(PlayerUseItemOnBlockEvent.class, event -> {
                    if(event.getItemStack().getMaterial() == item.getMaterial()) {
                        item.onUseOnBlock(player, event.getItemStack(), event.getHand(), event.getPosition(), event.getBlockFace());
                    }
                });

            }
            // Basic combat
            player.addEventCallback(EntityAttackEvent.class, event -> {
                if (event.getSource() instanceof LivingEntity) {

                    float damage = 1F;

                    //TODO check for weapon damage here

                    if (event.getTarget() instanceof LivingEntity) {
                        LivingEntity victim = (LivingEntity) event.getTarget();
                        System.out.println(victim.getHealth());
                        victim.damage(DamageType.fromEntity(event.getSource()), damage);
                        System.out.println(victim.getHealth());
                    }
                }
            });
        });
    }
}
