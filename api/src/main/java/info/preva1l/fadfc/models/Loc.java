package info.preva1l.fadfc.models;

import info.preva1l.fadfc.models.claim.ClaimChunk;
import lombok.Getter;
import org.bukkit.Location;

@Getter
public class Loc extends LocRef {
    private final String world;

    public Loc(String world, int x, int y, int z) {
        super(x, y, z);
        this.world = world;
    }

    public static Loc fromBukkit(Location location) {
        return new Loc(location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public ClaimChunk getChunk() {
        return new ClaimChunk(getX() >> 4, getZ() >> 4, getWorld());
    }
}
