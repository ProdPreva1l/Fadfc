package info.preva1l.fadfc.models.user;

import info.preva1l.fadfc.models.IFaction;
import info.preva1l.fadfc.persistence.DatabaseObject;

import java.util.Optional;
import java.util.UUID;

public interface User extends DatabaseObject {
    String getName();
    UUID getUniqueId();
    String getFactionTag();

    Optional<IFaction> getFaction();
}
