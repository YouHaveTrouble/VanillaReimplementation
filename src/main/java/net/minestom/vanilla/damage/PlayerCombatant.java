package net.minestom.vanilla.damage;

import java.time.Instant;

public class PlayerCombatant {

    long lastAttack;
    long cooldown;

    public PlayerCombatant() {
        refreshLastAttack();
        cooldown = lastAttack;
    }

    public long getLastAttack() {
        return lastAttack;
    }

    private void refreshLastAttack() {
        lastAttack = getNowNano();
    }

    public void setCooldown(double weaponAttackSpeed) {
        double timeInTicks = 20*weaponAttackSpeed;
        refreshLastAttack();
        long cooldownMilli = (long) (50000000L*timeInTicks);
        cooldown = lastAttack+cooldownMilli;
    }

    public long getCooldown() {
        return cooldown;
    }

    public boolean isOnCooldown() {
        return cooldown >= getNowNano();
    }

    private long getNowNano() {
        Instant inst = Instant.now();
        long time = inst.getEpochSecond();
        time *= 1000000000L;
        time += inst.getNano();
        return time;
    }

}
