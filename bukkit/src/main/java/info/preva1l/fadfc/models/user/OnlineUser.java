package info.preva1l.fadfc.models.user;

import info.preva1l.fadfc.Fadfc;
import lombok.Getter;
import net.kyori.adventure.audience.Audience;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Getter
public class OnlineUser implements User, CommandUser {
    private final String name;
    private final UUID uniqueId;
    private final Player player;

    public OnlineUser(Player player) {
        this.player = player;
        this.uniqueId = player.getUniqueId();
        this.name = player.getName();
    }

    @Override
    public @NotNull Audience getAudience() {
        return Fadfc.i().getAudiences().player(player);
    }

    @Override
    public boolean hasPermission(@NotNull String permission) {
        return player.hasPermission(permission);
    }
}
