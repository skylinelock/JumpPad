package mc.sky_lock.jumppad;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author sky_lock
 */
public class JumpPad extends JavaPlugin {

    @Override
    public void onEnable() {
        super.onEnable();
        getServer().getPluginManager().registerEvents(new InteractListener(this), this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
