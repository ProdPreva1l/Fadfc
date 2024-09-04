package info.preva1l.fadfc;

import info.preva1l.fadfc.api.FactionsAPI;
import info.preva1l.fadfc.api.ImplFactionsAPI;
import info.preva1l.fadfc.managers.FactionManager;
import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Fadfc extends JavaPlugin {
    private static Fadfc instance;
    private BukkitAudiences audiences;

    @Override
    public void onEnable() {
        audiences = BukkitAudiences.create(this);
        instance = this;

        // Init the managers
        FactionManager.getInstance();

        FactionsAPI.setInstance(new ImplFactionsAPI());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Fadfc i() {
        return instance;
    }
}
