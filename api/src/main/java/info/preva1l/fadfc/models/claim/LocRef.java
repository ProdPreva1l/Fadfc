package info.preva1l.fadfc.models.claim;

import lombok.Getter;

@Getter
public class LocRef {
    private final int x;
    private final int y;
    private final int z;

    public LocRef(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
