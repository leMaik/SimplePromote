package de.craften.plugins.simplepromote;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * A promotion.
 */
public class Promotion {
    private final String name;
    private final double cost;
    private final String group;
    private final String requiredGroup;

    public Promotion(String name, double cost, String group, String requiredGroup) {
        this.name = name;
        this.cost = cost;
        this.group = group;
        this.requiredGroup = requiredGroup;
    }

    public boolean promote(Player player) {
        Economy economy = Bukkit.getServicesManager().getRegistration(Economy.class).getProvider();
        Permission permissions = Bukkit.getServicesManager().getRegistration(Permission.class).getProvider();
        if (!permissions.playerInGroup(player, group) && (requiredGroup == null || permissions.playerInGroup(player, requiredGroup))) {
            EconomyResponse response = economy.withdrawPlayer(player, cost);
            if (response.transactionSuccess()) {
                permissions.playerAddGroup(player, group);
                permissions.playerRemoveGroup(player, requiredGroup);
                return true;
            } else {
                player.sendMessage(response.errorMessage);
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public String getCostString() {
        Economy economy = Bukkit.getServicesManager().getRegistration(Economy.class).getProvider();
        return economy.format(cost);
    }

    public boolean isPromoted(Player player) {
        Permission permissions = Bukkit.getServicesManager().getRegistration(Permission.class).getProvider();
        return permissions.playerInGroup(player, group);
    }

    public boolean canPromote(Player player) {
        Permission permissions = Bukkit.getServicesManager().getRegistration(Permission.class).getProvider();
        return permissions.playerInGroup(player, requiredGroup) && !permissions.playerInGroup(player, group);
    }
}
