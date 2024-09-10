package info.preva1l.fadfc.managers;

import info.preva1l.fadfc.models.IFaction;
import info.preva1l.fadfc.models.Loc;
import info.preva1l.fadfc.models.claim.ClaimChunk;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class FactionManager implements IFactionManager {
    private static FactionManager instance;
    private final Map<String, IFaction> factionsCache = new ConcurrentHashMap<>();

    public static FactionManager getInstance() {
        if (instance == null) {
            instance = new FactionManager();
        }
        return instance;
    }

    public void updateFaction(IFaction faction) {
        factionsCache.put(faction.getTag(), faction);
    }

    @Override
    public Optional<IFaction> getFactionAt(ClaimChunk claimChunk) {
        return factionsCache.values().stream().filter(faction -> faction.getClaims().contains(claimChunk)).findFirst();
    }

    @Override
    public Optional<IFaction> getFactionAt(Loc loc) {
        return factionsCache.values().stream().filter(faction -> faction.getClaims().contains(loc.getChunk())).findFirst();
    }

    @Override
    public Optional<IFaction> getFactionByTag(String tag) {
        if (tag == null) return Optional.empty();
        return Optional.ofNullable(factionsCache.get(tag));
    }

    @Override
    public List<IFaction> getAllFactions() {
        return new ArrayList<>(factionsCache.values());
    }
}
