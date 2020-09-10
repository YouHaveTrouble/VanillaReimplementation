package net.minestom.vanilla.items;

import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.Direction;

public class WeaponItem extends VanillaItem {

    private float attackDamage;
    private float attackSpeed;

    public WeaponItem(Material vanillaItem, float damage, float attackSpeed) {
        super(vanillaItem);
        this.attackDamage  = damage;
        this.attackSpeed = attackSpeed;
    }

    @Override
    public void onUseInAir(Player player, ItemStack itemStack, Player.Hand hand) {

    }

    @Override
    public boolean onUseOnBlock(Player player, ItemStack itemStack, Player.Hand hand, BlockPosition position, Direction blockFace) {
        return false;
    }

    public float getAttackDamage() {
        return attackDamage;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }
}
