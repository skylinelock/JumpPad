package mc.sky_lock.jumppad;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author sky_lock
 */
class InteractListener implements Listener {

    private final JumpPad pl;

    InteractListener(JumpPad pl) {
        this.pl = pl;
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (event.getAction() != Action.PHYSICAL) {
            return;
        }
        Player player = event.getPlayer();

        if (!(player.hasPermission("jumppad.allow.fly"))) {
            return;
        }
        Block block = event.getClickedBlock();

        if (block.getType() != Material.WOOD_PLATE
                && block.getType() != Material.STONE_PLATE
                && block.getType() != Material.GOLD_PLATE
                && block.getType() != Material.IRON_PLATE) {
            return;
        }
        Block underBlock = player.getWorld().getBlockAt(block.getLocation().subtract(0, 2, 0));

        if (underBlock.getType() != Material.SIGN_POST && underBlock.getType() != Material.WALL_SIGN) {
            return;
        }
        Sign sign = (Sign) underBlock.getState();
        List<String> strs = new ArrayList<>();

        Arrays.asList(sign.getLines()).forEach(strs::add);
        new BukkitRunnable() {

            @Override
            public void run() {
                if (strs.size() < 3) {
                    return;
                }
                if (!(strs.get(0).equalsIgnoreCase("[jumppad]"))) {
                    return;
                }
                double scalar = isDouble(strs.get(1)) ? Double.parseDouble(strs.get(1)) : 3.0;
                double YAxis = isDouble(strs.get(2)) ? Double.parseDouble(strs.get(2)) : 3.0;

                Location loc = player.getLocation();

                player.setVelocity(loc.getDirection().multiply(scalar).setY(YAxis));
                player.playSound(loc, Sound.ENTITY_BLAZE_SHOOT, 1F, 1F);
            }
        }.runTaskLater(pl, 1L);
    }

    private boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }
}

