package plugin.pocketdim;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PocketDim extends JavaPlugin {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        pdSetCommand command = new pdSetCommand();
        getConfig().options().copyDefaults(true);
        getCommand("pdset").setExecutor(command);
        getLogger().info("Loaded.");
        Bukkit.getPluginManager().registerEvents(new SnowBallHitListener(), this);
    }

    @Override
    public void onDisable() {
        saveConfig();
    }
}