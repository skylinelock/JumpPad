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
import java.util.List;

/**
 * Created by sky_lock on 2016/03/07.
 */
public class PlayerInteractListener implements Listener {

    private final JumpPad main;

    public PlayerInteractListener(JumpPad main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e) {
        if(!(e.getAction().equals(Action.PHYSICAL))) {
            return;
        }
        if(!(e.getPlayer() instanceof Player)) {
            return;
        }
        Player player = e.getPlayer();

        if(!(player.hasPermission("jumppad.fly"))) {
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

        for(String str: sign.getLines()) {
            signStrs.add(str);
        }

        new JumpTask(player, signStrs).runTaskLater(main, 1);
    }

}
