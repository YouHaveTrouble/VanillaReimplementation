package net.minestom.vanilla;

import net.minestom.server.MinecraftServer;
import net.minestom.server.attribute.Attribute;
import net.minestom.server.data.Data;
import net.minestom.server.data.SerializableDataImpl;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.ItemEntity;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.damage.DamageType;
import net.minestom.server.event.EventCallback;
import net.minestom.server.event.entity.EntityAttackEvent;
import net.minestom.server.event.instance.AddEntityToInstanceEvent;
import net.minestom.server.event.item.ItemDropEvent;
import net.minestom.server.event.item.PickupItemEvent;
import net.minestom.server.event.player.*;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.instance.ExplosionSupplier;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.network.ConnectionManager;
import net.minestom.server.storage.StorageManager;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.Vector;
import net.minestom.server.utils.time.TimeUnit;
import net.minestom.server.world.DimensionType;
import net.minestom.vanilla.anvil.AnvilChunkLoader;
import net.minestom.vanilla.blocks.NetherPortalBlock;
import net.minestom.vanilla.blocks.VanillaBlocks;
import net.minestom.vanilla.damage.CombatUtils;
import net.minestom.vanilla.damage.DefaultDamageValues;
import net.minestom.vanilla.damage.PlayerCombatants;
import net.minestom.vanilla.damage.WeaponStats;
import net.minestom.vanilla.dimensions.VanillaDimensionTypes;
import net.minestom.vanilla.generation.VanillaTestGenerator;
import net.minestom.vanilla.instance.VanillaExplosion;
import net.minestom.vanilla.system.ServerProperties;
import java.util.UUID;

public class PlayerInit {

    private static volatile InstanceContainer overworld;
    private static volatile InstanceContainer nether;
    private static volatile InstanceContainer end;

    public static void init(ServerProperties properties) {
        String worldName = properties.get("level-name");

        ExplosionSupplier explosionGenerator = (centerX, centerY, centerZ, strength, additionalData) -> {
            boolean isTNT = additionalData != null ? additionalData.getOrDefault(VanillaExplosion.DROP_EVERYTHING_KEY, false) : false;
            boolean noBlockDamage = additionalData != null ? additionalData.getOrDefault(VanillaExplosion.DONT_DESTROY_BLOCKS_KEY, false) : false;
            return new VanillaExplosion(centerX, centerY, centerZ, strength, false, isTNT, !noBlockDamage);
        };
        StorageManager storageManager = MinecraftServer.getStorageManager();
        VanillaTestGenerator noiseTestGenerator = new VanillaTestGenerator();
        overworld = MinecraftServer.getInstanceManager().createInstanceContainer(DimensionType.OVERWORLD, storageManager.getLocation(worldName + "/data")); // TODO: configurable
        overworld.enableAutoChunkLoad(true);
        overworld.setChunkGenerator(noiseTestGenerator);
        overworld.setData(new SerializableDataImpl());
        overworld.setExplosionSupplier(explosionGenerator);
        overworld.setChunkLoader(new AnvilChunkLoader(storageManager.getLocation(worldName + "/region")));

        nether = MinecraftServer.getInstanceManager().createInstanceContainer(VanillaDimensionTypes.NETHER, MinecraftServer.getStorageManager().getLocation(worldName + "/DIM-1/data"));
        nether.enableAutoChunkLoad(true);
        nether.setChunkGenerator(noiseTestGenerator);
        nether.setData(new SerializableDataImpl());
        nether.setExplosionSupplier(explosionGenerator);
        nether.setChunkLoader(new AnvilChunkLoader(storageManager.getLocation(worldName + "/DIM-1/region")));

        end = MinecraftServer.getInstanceManager().createInstanceContainer(VanillaDimensionTypes.END, MinecraftServer.getStorageManager().getLocation(worldName + "/DIM1/data"));
        end.enableAutoChunkLoad(true);
        end.setChunkGenerator(noiseTestGenerator);
        end.setData(new SerializableDataImpl());
        end.setExplosionSupplier(explosionGenerator);
        end.setChunkLoader(new AnvilChunkLoader(storageManager.getLocation(worldName + "/DIM1/region")));

        PlayerCombatants combatants = new PlayerCombatants();

        // Load some chunks beforehand
        int loopStart = -2;
        int loopEnd = 2;
        for (int x = loopStart; x < loopEnd; x++)
            for (int z = loopStart; z < loopEnd; z++) {
                overworld.loadChunk(x, z);
                nether.loadChunk(x, z);
                end.loadChunk(x, z);
            }

        EventCallback<AddEntityToInstanceEvent> callback = event -> {
            event.getEntity().setData(new SerializableDataImpl());
            Data data = event.getEntity().getData();
            if (event.getEntity() instanceof Player) {
                data.set(NetherPortalBlock.PORTAL_COOLDOWN_TIME_KEY, 5 * 20L, Long.class);
            }
        };
        overworld.addEventCallback(AddEntityToInstanceEvent.class, callback);
        nether.addEventCallback(AddEntityToInstanceEvent.class, callback);
        end.addEventCallback(AddEntityToInstanceEvent.class, callback);

        MinecraftServer.getSchedulerManager().buildShutdownTask(() -> {
            try {
                overworld.saveInstance(() -> System.out.println("Overworld saved"));
                nether.saveInstance(() -> System.out.println("Nether saved"));
                end.saveInstance(() -> System.out.println("End saved"));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        });

        if (Boolean.parseBoolean(properties.get("online-mode"))) {
            MojangAuth.init();
        }

        ConnectionManager connectionManager = MinecraftServer.getConnectionManager();

        connectionManager.addPlayerInitialization(player -> {
            player.addEventCallback(PlayerLoginEvent.class, event -> {
                event.setSpawningInstance(overworld);
                combatants.addCombatant(player);
            });

            // anticheat method
            // but also prevents client and server fighting for player position after a teleport due to a Nether portal
            player.addEventCallback(PlayerMoveEvent.class, moveEvent -> {
                float currentX = player.getPosition().getX();
                float currentY = player.getPosition().getY();
                float currentZ = player.getPosition().getZ();
                float velocityX = player.getVelocity().getX();
                float velocityY = player.getVelocity().getY();
                float velocityZ = player.getVelocity().getZ();

                float dx = moveEvent.getNewPosition().getX() - currentX;
                float dy = moveEvent.getNewPosition().getY() - currentY;
                float dz = moveEvent.getNewPosition().getZ() - currentZ;

                float actualDisplacement = dx * dx + dy * dy + dz * dz;
                float expectedDisplacement = velocityX * velocityX + velocityY * velocityY + velocityZ * velocityZ;

                float upperLimit = 100; // TODO: 300 if elytra deployed

                if (actualDisplacement - expectedDisplacement >= upperLimit) {
                    moveEvent.setCancelled(true);
                    player.teleport(player.getPosition()); // force teleport to previous position
                    System.out.println(player.getUsername() + " moved too fast! " + dx + " " + dy + " " + dz);
                }
            });

            player.addEventCallback(PlayerBlockBreakEvent.class, event -> {
                if (player.getGameMode() != GameMode.CREATIVE)
                    VanillaBlocks.dropOnBreak(player.getInstance(), event.getBlockPosition());
            });

            player.addEventCallback(PlayerSpawnEvent.class, event -> {
                if (event.isFirstSpawn()) {

                    player.setGameMode(GameMode.SURVIVAL);
                    player.teleport(new Position(176, 72, 236));
                    ItemStack axe = new ItemStack(Material.WOODEN_AXE, (byte) 1);
                    UUID uuid = UUID.nameUUIDFromBytes("base".getBytes());
                    player.getInventory().addItemStack(axe);
                    player.getInventory().addItemStack(new ItemStack(Material.WOODEN_SWORD, (byte) 1));

                }
            });
            player.addEventCallback(PlayerDisconnectEvent.class, event -> {
                combatants.removeCombatant(player);
            });

            player.addEventCallback(PickupItemEvent.class, event -> {
                boolean couldAdd = player.getInventory().addItemStack(event.getItemStack());
                event.setCancelled(!couldAdd); // Cancel event if player does not have enough inventory space
            });

            player.addEventCallback(ItemDropEvent.class, event -> {
                ItemStack droppedItem = event.getItemStack();
                ItemEntity itemEntity = new ItemEntity(droppedItem, player.getPosition().clone().add(0, 1.5f, 0));
                itemEntity.setPickupDelay(500, TimeUnit.MILLISECOND);
                itemEntity.setInstance(player.getInstance());
                Vector velocity = player.getPosition().clone().getDirection().multiply(6);
                itemEntity.setVelocity(velocity);
            });

            // Basic combat
            player.addEventCallback(EntityAttackEvent.class, event -> {
                if (!(event.getSource() instanceof LivingEntity && event.getTarget() instanceof LivingEntity))
                    return;

                LivingEntity victim = (LivingEntity) event.getTarget();
                LivingEntity attacker = (LivingEntity) event.getSource();

                if (victim.isInvulnerable())
                    return;
                if (victim instanceof Player && ((Player) victim).getGameMode().equals(GameMode.CREATIVE))
                    return;

                ItemStack murderWeapon = attacker.getItemInMainHand();
                float damage = 1F;
                float attackSpeed = 4F;

                try { // Try to get default weapon stats
                    WeaponStats stats = DefaultDamageValues.getVanillaDamageValues().get(murderWeapon.getMaterial());
                    damage = stats.getAttackDamage();
                    attackSpeed = stats.getAttackSpeed();
                } catch (Exception ignored) {}

                // damage reduction from attack speed
                double ticksSince = combatants.getCombatant(player.getUuid()).getTicksFromLastAction();
                float attackStrength = CombatUtils.getAttackStrengthScale(0.5F, (float) ticksSince, attackSpeed);
                damage *= 0.2F + attackStrength * attackStrength * 0.8F;

                // TODO damage reduction from victim armor

                // temp logging for testing
                System.out.println( "ticks: " + ticksSince + " damage:" + damage);

                // TODO rethink how attack speed is applied
                attacker.setAttribute(Attribute.ATTACK_SPEED, attackSpeed);

                // damage the victim, apply cooldowns and other funny stuff
                victim.damage(DamageType.fromEntity(event.getSource()), damage);
                // TODO make knockback velocity vector closer to vanilla
                victim.setVelocity(CombatUtils.getKnockback(attacker));
                CombatUtils.grantImmunity(victim);
                combatants.getCombatant(attacker.getUuid()).refreshLastAttack();
            });
        });
    }
}
