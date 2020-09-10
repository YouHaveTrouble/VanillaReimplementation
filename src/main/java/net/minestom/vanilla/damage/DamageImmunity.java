package net.minestom.vanilla.damage;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.timer.SchedulerManager;
import net.minestom.server.utils.time.TimeUnit;

public class DamageImmunity {

    private static final SchedulerManager schedulerManager = MinecraftServer.getSchedulerManager();

    public static void grantImmunity(LivingEntity entity) {
        entity.setInvulnerable(true);
        schedulerManager.buildTask(() -> {
            entity.setInvulnerable(false);
        }).delay(10, TimeUnit.TICK).schedule();
    }

    public static void grantImmunity(LivingEntity entity, int timeInTicks) {
        entity.setInvulnerable(true);
        schedulerManager.buildTask(() -> {
            entity.setInvulnerable(false);
        }).delay(timeInTicks, TimeUnit.TICK).schedule();
    }


}
