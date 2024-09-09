package info.preva1l.fadfc.models.user;

import info.preva1l.fadfc.Fadfc;
import info.preva1l.fadfc.managers.FactionManager;
import info.preva1l.fadfc.models.IFaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.audience.Audience;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class OnlineUser implements User, CommandUser {
    private final String name;
    private final UUID uniqueId;
    private final Player player;
    @Nullable
    private final String factionTag;

    public static OnlineUser fromDb(User user, Player player) {
        return new OnlineUser(user.getName(), user.getUniqueId(), player, user.getFactionTag());
    }

    @Override
    public @NotNull Audience getAudience() {
        return Fadfc.i().getAudiences().player(player);
    }

    @Override
    public boolean hasPermission(@NotNull String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public Optional<IFaction> getFaction() {
        return FactionManager.getInstance().getFactionByTag(factionTag);
    }
}
