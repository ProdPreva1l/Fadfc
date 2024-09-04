package info.preva1l.fadfc.models.claim;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClaimChunk {
    private final int chunkX;
    private final int chunkZ;
    private final String world;
}
