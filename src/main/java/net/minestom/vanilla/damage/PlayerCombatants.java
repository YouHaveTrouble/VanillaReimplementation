package net.minestom.vanilla.damage;

import net.minestom.server.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerCombatants {

    private HashMap<UUID,PlayerCombatant> playerCombatantList = new HashMap<>();

    public void addCombatant(Player player) {
        playerCombatantList.put(player.getUuid(), new PlayerCombatant());
    }
    public void removeCombatant(Player player) {
        playerCombatantList.remove(player.getUuid());
    }
    public PlayerCombatant getCombatant(UUID uuid) {
        return playerCombatantList.get(uuid);
    }

}
