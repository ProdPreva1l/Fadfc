package info.preva1l.fadfc.models;

import info.preva1l.fadfc.models.claim.ClaimChunk;
import info.preva1l.fadfc.models.user.User;
import info.preva1l.fadfc.persistence.DatabaseObject;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IFaction extends DatabaseObject {
    @NotNull String getTag();
    void setTag(@NotNull String tag);

    User getLeader();
    void setLeader(User leader);

    List<User> getMembers();
    void addMember(@NotNull User member);

    List<ClaimChunk> getClaims();
    void claimChunk(@NotNull ClaimChunk claimChunk);
}
