package net.minestom.vanilla.damage;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.entity.Player;
import net.minestom.server.timer.SchedulerManager;
import net.minestom.server.utils.MathUtils;
import net.minestom.server.utils.Vector;
import net.minestom.server.utils.time.TimeUnit;
import java.time.Instant;

public class CombatUtils {

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

    public static Vector getKnockback(LivingEntity entity) {
        float multiplier;
        Vector baseVelocity = entity.getPosition().clone().getDirection();
        if (entity instanceof Player && ((Player) entity).isSprinting()) {
            multiplier = 6F;
            if (baseVelocity.getY() < 1.2F) {
                baseVelocity.setY(1.2F);
            }
        } else {
            multiplier = 4F;
            if (baseVelocity.getY() < 1.5F) {
                baseVelocity.setY(1.5F);
            }
        }
        return baseVelocity.multiply(multiplier);
    }

    public static long getNowNano() {
        Instant inst = Instant.now();
        long time = inst.getEpochSecond();
        time *= 1000000000L;
        time += inst.getNano();
        return time;
    }

    public static float getCurrentItemAttackStrengthDelay(double attackSpeed) {
        return (float) ((1.0D / attackSpeed) * 20.0D);
    }

    public static float getAttackStrengthScale(float x, float ticksSince, double attackSpeed) {
        return MathUtils.clampFloat((ticksSince + x) / getCurrentItemAttackStrengthDelay(attackSpeed), 0.0F, 1.0F);
    }

}
