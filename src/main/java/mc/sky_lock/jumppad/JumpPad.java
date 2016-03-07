package mc.sky_lock.jumppad;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by sky_lock on 2016/03/07.
 */
public class JumpPad extends JavaPlugin {

    @Override
    public void onEnable() {
        super.onEnable();
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
    }

    @Override
    public void onDisable() {
    }
}
