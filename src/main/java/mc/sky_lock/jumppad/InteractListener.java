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

    private final JumpPad main;

    InteractListener(JumpPad main) {
        this.main = main;
    }

    @SuppressWarnings("unused")
    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e) {
        if(!(e.getAction().equals(Action.PHYSICAL))) {
            return;
        }
        Player player = e.getPlayer();

        if(!(player.hasPermission("jumppad.allow.fly"))) {
            return;
        }
        Block block = e.getClickedBlock();

        if(!(block.getType().equals(Material.WOOD_PLATE)) &&
                !(block.getType().equals(Material.STONE_PLATE)) &&
                !(block.getType().equals(Material.GOLD_PLATE)) &&
                !(block.getType().equals(Material.IRON_PLATE))) {
            return;
        }
        Block underBlock = player.getWorld().getBlockAt(block.getLocation().add(0,-2,0));

        if(!(underBlock.getType().equals(Material.SIGN_POST)) && !(underBlock.getType().equals(Material.WALL_SIGN))) {
            return;
        }
        Sign sign = (Sign)underBlock.getState();
        List<String> signStrs = new ArrayList<>();

        Arrays.asList(sign.getLines()).forEach(signStrs::add);

        new JumpTask(player, signStrs).runTaskLater(main, 1);
    }

}
