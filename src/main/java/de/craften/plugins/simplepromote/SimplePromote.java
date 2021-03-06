package de.craften.plugins.simplepromote;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A very simple promote plugin.
 */
public class SimplePromote extends JavaPlugin {
    private static final String METADATA_PROMOTING = "simplepromote.promoting";
    private List<Promotion> promotions;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        promotions = new ArrayList<>();

        for (Map promotionConfig : getConfig().getMapList("ranks")) {
            promotions.add(new Promotion(
                    promotionConfig.get("name").toString(),
                    Double.parseDouble(promotionConfig.get("cost").toString()),
                    promotionConfig.get("group").toString(),
                    promotionConfig.containsKey("requires") ? promotionConfig.get("requires").toString() : null
            ));
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("promote") && sender instanceof Player) {
            Promotion nextPromotion = getNextPromotion((Player) sender);
            if (((Player) sender).hasMetadata(METADATA_PROMOTING) && args.length == 1 && args[0].equalsIgnoreCase("confirm")) {
                if (nextPromotion != null) {
                    if (nextPromotion.promote((Player) sender)) {
                        sender.sendMessage("Congratulations, you were promoted to " + nextPromotion.getName() + "!");
                        if (getConfig().getBoolean("broadcastPromotedPlayers", true)) {
                            getServer().broadcastMessage(((Player) sender).getDisplayName() + " was just promoted to " + nextPromotion.getName() + "!");
                        }
                    } else {
                        sender.sendMessage("Sorry, you could not be promoted.");
                    }
                } else {
                    sender.sendMessage("Sorry, you can't be promoted.");
                }
                ((Player) sender).removeMetadata(METADATA_PROMOTING, this);
            } else {
                Economy economy = Bukkit.getServicesManager().getRegistration(Economy.class).getProvider();
                if (nextPromotion != null) {
                    if (economy.has((OfflinePlayer) sender, nextPromotion.getCost())) {
                        sender.sendMessage("You can be promoted to " + nextPromotion.getName() + " for " + nextPromotion.getCostString() + ". Use '/" + label + " confirm' to continue.");
                    } else {
                        sender.sendMessage("The next rank is " + nextPromotion.getName() + ", but you don't have enough money to be promoted. You need " + nextPromotion.getCostString() + ".");
                    }
                } else {
                    sender.sendMessage("Sorry, you can't be promoted.");
                }
                ((Player) sender).setMetadata(METADATA_PROMOTING, new FixedMetadataValue(this, true));
            }
            return true;
        }
        return false;
    }

    private Promotion getNextPromotion(Player player) {
        for (Promotion promotion : promotions) {
            if (promotion.canPromote(player)) {
                return promotion;
            }
        }
        return null;
    }
}
