package dev.sky_lock.jumppad;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author sky_lock
 */
public final class JumpPadPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        super.onEnable();
        getServer().getPluginManager().registerEvents(new InteractListener(this), this);
    }

}
