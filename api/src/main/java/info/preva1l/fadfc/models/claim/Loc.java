package info.preva1l.fadfc.models.claim;

import lombok.Getter;

@Getter
public class Loc extends LocRef {
    private final String world;

    public Loc(String world, int x, int y, int z) {
        super(x, y, z);
        this.world = world;
    }

    public ClaimChunk getChunk() {
        return new ClaimChunk(getX() >> 4, getZ() >> 4, getWorld());
    }
}
