package info.preva1l.fadfc.models.user;

import info.preva1l.fadfc.models.IFaction;

import java.util.Optional;
import java.util.UUID;

public interface User {
    String getName();
    UUID getUniqueId();

    Optional<IFaction> getFaction();
}
