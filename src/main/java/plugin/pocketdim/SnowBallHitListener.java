package plugin.pocketdim;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.Effect.Type.SOUND;

public class SnowBallHitListener implements Listener {
    Plugin plugin = null;
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
    }
    @EventHandler
    public void onSnowballHit(ProjectileHitEvent event) {

        if (event.getEntity() instanceof Snowball){
            if (event.getHitEntity() instanceof Player) {
                Player player = (Player) event.getHitEntity();
                Snowball snowball = (Snowball) event.getEntity();

                String playerName = player.getName();
                //CustomName is equals to "tp" if player launch snowball with correct ItemStack Name
                String snowballName = snowball.getCustomName();
                if (snowball.getCustomName().equals("tp")){
                    //Get shooter nickname to obtain dimension where to send
                    String shooter = (String) snowball.getMetadata("Shooter").get(0).value();
                    this.plugin = Bukkit.getPluginManager().getPlugin("PocketDim");
                    String DimensionName = plugin.getConfig().getString(shooter+".DimensionName");
                    // Execute PlaySound with small delay, because we need to teleport player first
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT,1,1);
                            player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER,1,1);
                        }
                    }.runTaskLater(plugin, 1L);
                    //Execute multiverse plugin command
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv tp "+playerName+" "+ DimensionName);
                }


            }
        }
    }
    @EventHandler
    public void projectileLaunchEvent(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player) {
            Player player = (Player) event.getEntity().getShooter();

            if (event.getEntity() instanceof Snowball) {
                this.plugin = Bukkit.getPluginManager().getPlugin("PocketDim");
                String playerName = player.getName();
                String snowballName = plugin.getConfig().getString(playerName+".SnowballName");
                if (player.getItemInHand().getItemMeta().getDisplayName().equals(snowballName)) {
                    player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT,1,1);
                    player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER,1,1);

                    event.getEntity().setMetadata("Shooter", new FixedMetadataValue(plugin, playerName.toString()));
                    event.getEntity().setCustomName("tp");
                }
            }
        }
    }


}
