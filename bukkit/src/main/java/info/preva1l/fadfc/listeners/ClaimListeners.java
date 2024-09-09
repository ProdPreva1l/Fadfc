package info.preva1l.fadfc.listeners;

import info.preva1l.fadfc.managers.FactionManager;
import info.preva1l.fadfc.managers.UserManager;
import info.preva1l.fadfc.models.IFaction;
import info.preva1l.fadfc.models.Loc;
import info.preva1l.fadfc.models.user.OnlineUser;
import info.preva1l.fadfc.models.user.User;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Optional;

@AllArgsConstructor
public class ClaimListeners implements Listener {
    private final FactionManager factionManager;

    /**
     * Check if the player can perform an action at the provided location.
     *
     * @param user the player to check.
     * @param location the location of the action.
     * @return true if the action is allowed false if not
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isActionAllowed(User user, Loc location) {
        Optional<IFaction> factionAtLocationOptional = factionManager.getFactionAt(location);
        Optional<IFaction> playerFactionOptional = user.getFaction();

        if (factionAtLocationOptional.isEmpty() || playerFactionOptional.isEmpty()) {
            return true;
        }

        IFaction factionAtLocation = factionAtLocationOptional.get();
        IFaction playerFaction = playerFactionOptional.get();

        return playerFaction.equals(factionAtLocation);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Optional<OnlineUser> user = UserManager.getInstance().getUser(event.getPlayer().getUniqueId());
        if (user.isEmpty()) {
            throw new RuntimeException("User never connected?");
        }
        if (!isActionAllowed(user.get(), Loc.fromBukkit(event.getBlock().getLocation()))) {
            return;
        }
        event.setCancelled(true);
        // todo: send message
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Optional<OnlineUser> user = UserManager.getInstance().getUser(event.getPlayer().getUniqueId());
        if (user.isEmpty()) {
            throw new RuntimeException("User never connected?");
        }
        if (!isActionAllowed(user.get(), Loc.fromBukkit(event.getBlock().getLocation()))) {
            return;
        }
        event.setCancelled(true);
        // todo: send message
    }
}
