package info.preva1l.fadfc.models;

import info.preva1l.fadfc.models.claim.ClaimChunk;
import info.preva1l.fadfc.models.user.User;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Faction implements IFaction {
    private String tag;
    private User leader;
    private final List<ClaimChunk> claims = new ArrayList<>();
    private final List<User> members = new ArrayList<>();

    @Override
    public void addMember(@NotNull User member) {
        members.add(member);
    }

    @Override
    public void claimChunk(@NotNull ClaimChunk claimChunk) {
        claims.add(claimChunk);
    }

    @Override
    public Loc getSpawn() {
        return null;
    }
}
