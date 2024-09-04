package info.preva1l.fadfc.managers;

import info.preva1l.fadfc.models.IFaction;
import info.preva1l.fadfc.models.claim.ClaimChunk;
import info.preva1l.fadfc.models.claim.Loc;

import java.util.List;
import java.util.Optional;

public interface IFactionManager {
    Optional<IFaction> getFactionAt(ClaimChunk claimChunk);
    Optional<IFaction> getFactionAt(Loc loc);
    Optional<IFaction> getFactionByTag(String tag);

    List<IFaction> getAllFactions();
}
