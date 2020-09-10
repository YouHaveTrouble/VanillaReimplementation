package net.minestom.vanilla.damage;

public class WeaponStats {

    private final float attackDamage;
    private final float attackSpeed;

    public WeaponStats(float damage, float attackSpeed) {
        this.attackDamage  = damage;
        this.attackSpeed = attackSpeed;
    }

    public float getAttackDamage() {
        return attackDamage;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }
}
