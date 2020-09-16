package net.minestom.vanilla.damage;

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

    public void refreshLastAttack() {
        lastAttack = CombatUtils.getNowNano();
    }

    public long getCooldown() {
        return cooldown;
    }

    public boolean isOnCooldown() {
        return cooldown >= CombatUtils.getNowNano();
    }

    public double getTicksFromLastAction() {
        long now = CombatUtils.getNowNano();
        long nanosSince = now - cooldown;
        long msSince = nanosSince / 10000000;
        return Math.round(msSince) / 5F;
    }



}
