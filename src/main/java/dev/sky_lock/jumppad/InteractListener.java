package dev.sky_lock.jumppad;

import com.google.common.primitives.Doubles;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author sky_lock
 */
final class InteractListener implements Listener {

    private final JumpPadPlugin plugin;

    InteractListener(JumpPadPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.PHYSICAL) {
            return;
        }
        Player player = event.getPlayer();

        if (!(player.hasPermission("jumppad.fly"))) {
            return;
        }
        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }
        if (!this.isPressurePlate(block.getType())) {
            return;
        }
        Block underBlock = block.getLocation().subtract(0.0D, 2.0D, 0.0).clone().getBlock();

        if (underBlock.getType() != Material.SIGN && underBlock.getType() != Material.WALL_SIGN) {
            return;
        }
        Sign sign = (Sign) underBlock.getState();
        String[] lines = sign.getLines();

        new BukkitRunnable() {

            @Override
            public void run() {
                if (!(lines[0].equalsIgnoreCase("[jumppad]"))) {
                    return;
                }
                Double scale = Doubles.tryParse(lines[1]);
                Double y = Doubles.tryParse(lines[2]);

                Location loc = player.getLocation();

                player.setVelocity(loc.getDirection().multiply(scale == null ? 3.0F : scale).setY(y == null ? 1.0F : y));
                player.playSound(loc, Sound.ENTITY_BLAZE_SHOOT, 1.0F, 1.0F);
            }
        }.runTaskLater(plugin, 1L);
    }

    private boolean isPressurePlate(Material type) {
        switch (type) {
            case ACACIA_PRESSURE_PLATE:
            case BIRCH_PRESSURE_PLATE:
            case DARK_OAK_PRESSURE_PLATE:
            case HEAVY_WEIGHTED_PRESSURE_PLATE:
            case JUNGLE_PRESSURE_PLATE:
            case LIGHT_WEIGHTED_PRESSURE_PLATE:
            case OAK_PRESSURE_PLATE:
            case SPRUCE_PRESSURE_PLATE:
            case STONE_PRESSURE_PLATE:
                return true;
            default:
                return false;
        }
    }
}

