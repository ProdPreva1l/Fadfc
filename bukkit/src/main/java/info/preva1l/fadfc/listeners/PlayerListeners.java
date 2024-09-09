package info.preva1l.fadfc.listeners;

import info.preva1l.fadfc.managers.PersistenceManager;
import info.preva1l.fadfc.managers.UserManager;
import info.preva1l.fadfc.models.user.OnlineUser;
import info.preva1l.fadfc.models.user.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListeners implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        PersistenceManager.getInstance().get(User.class, e.getPlayer().getUniqueId()).thenAccept(user -> {
            OnlineUser newUser;
            if (user.isEmpty()) {
                newUser = new OnlineUser(e.getPlayer().getName(), e.getPlayer().getUniqueId(), e.getPlayer(), null);
                PersistenceManager.getInstance().save(User.class, newUser);
                return;
            }
            UserManager.getInstance().cacheUser(OnlineUser.fromDb(user.get(), e.getPlayer()));
        });
    }

    @EventHandler
    public void onLeave(PlayerJoinEvent e) {
        UserManager.getInstance().invalidate(e.getPlayer().getUniqueId());
        UserManager.getInstance().invalidate(e.getPlayer().getName());
    }
}
