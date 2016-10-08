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
        List<String> signStrs = new ArrayList<>();

        Arrays.asList(sign.getLines()).forEach(signStrs::add);

        new JumpTask(player, signStrs).runTaskLater(pl, 1L);
    }

}
