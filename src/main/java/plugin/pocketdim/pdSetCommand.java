package plugin.pocketdim;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class pdSetCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            if (command.getName().equalsIgnoreCase("pdset")) {
                if (args.length == 2) {
                    String playerName = sender.getName().toString();
                    String snowballName = args[0];
                    String dimensionName = args[1];
                    Plugin plugin = Bukkit.getPluginManager().getPlugin("PocketDim");

                    plugin.getConfig().set(playerName + ".SnowballName", snowballName);
                    plugin.getConfig().set(playerName + ".DimensionName", dimensionName);
                    plugin.saveConfig();


                } else {
                    sender.sendMessage(ChatColor.RED + "Error");
                    sender.sendMessage("Please, try /pdSet <" + ChatColor.DARK_AQUA + "SnowballName" + ChatColor.WHITE + "> <" + ChatColor.DARK_AQUA + "DimensionName>" + ChatColor.WHITE + "");
                }
            }
        }
        return false;
    }
}
