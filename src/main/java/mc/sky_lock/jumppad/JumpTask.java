package mc.sky_lock.jumppad;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

/**
 * @author sky_lock
 */
class JumpTask extends BukkitRunnable {

    private final Player player;
    private final List<String> strs;

    JumpTask(Player player, List<String> strs) {
        this.player = player;
        this.strs = strs;
    }

    @Override
    public void run() {
        if (strs.size() < 3) {
            return;
        }
        if (!(strs.get(0).equalsIgnoreCase("[jumppad]"))) {
            return;
        }
        Double scalar = isDouble(strs.get(1)) ? Double.parseDouble(strs.get(1)) : 3.0F;
        Double YAxis = isDouble(strs.get(2)) ? Double.parseDouble(strs.get(2)) : 3.0F;
        
        Location loc = player.getLocation();

        player.setVelocity(loc.getDirection().multiply(scalar).setY(YAxis));
        player.playSound(loc, Sound.ENTITY_BLAZE_SHOOT, 1, 1F);
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
