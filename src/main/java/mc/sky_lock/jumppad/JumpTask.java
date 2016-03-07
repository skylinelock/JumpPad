package mc.sky_lock.jumppad;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

/**
 * Created by sky_lock on 2016/03/07.
 */
public class JumpTask extends BukkitRunnable {

    private final Player player;
    private final List<String> strs;

    public JumpTask(Player player, List<String> strs) {
        this.player = player;
        this.strs = strs;
    }

    @Override
    public void run() {
        if(strs.size() < 3) {
            return;
        }
        if(!(strs.get(0).equalsIgnoreCase("[jumppad]"))) {
            return;
        }

        Double XZAxis = isDouble(strs.get(1)) ? Double.valueOf(strs.get(1)) : 3.0F;
        Double YAxis = isDouble(strs.get(2)) ? Double.valueOf(strs.get(2)) : 3.0F;
        Location loc = player.getLocation();

        player.setVelocity(loc.getDirection().multiply(XZAxis).setY(YAxis));
        player.playSound(loc, Sound.GHAST_FIREBALL, 1, 1);
    }

    private boolean isDouble(String str) {
        try {
            Double.valueOf(str);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

}
