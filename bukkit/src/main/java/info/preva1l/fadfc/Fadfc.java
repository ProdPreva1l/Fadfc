package info.preva1l.fadfc;

import info.preva1l.fadfc.api.FactionsAPI;
import info.preva1l.fadfc.api.ImplFactionsAPI;
import info.preva1l.fadfc.jobs.SaveJobs;
import info.preva1l.fadfc.managers.CommandManager;
import info.preva1l.fadfc.managers.FactionManager;
import info.preva1l.fadfc.managers.PersistenceManager;
import info.preva1l.fadfc.managers.UserManager;
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
        PersistenceManager.getInstance();
        FactionManager.getInstance();
        UserManager.getInstance();
        CommandManager.getInstance();

        // Init Jobs
        SaveJobs.startAll();

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
