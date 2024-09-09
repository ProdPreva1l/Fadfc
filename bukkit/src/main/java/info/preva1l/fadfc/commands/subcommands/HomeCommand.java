package info.preva1l.fadfc.commands.subcommands;

import info.preva1l.fadfc.commands.lib.BasicSubCommand;
import info.preva1l.fadfc.commands.lib.Command;
import info.preva1l.fadfc.models.user.CommandUser;
import info.preva1l.fadfc.models.user.OnlineUser;

@Command(
        name = "home",
        permission = "fadfc.home"
)
public class HomeCommand extends BasicSubCommand {
    @Override
    public void execute(CommandUser sender, String[] args) {
        OnlineUser user = (OnlineUser) sender;
        user.getFaction().ifPresent(faction -> faction.getSpawn().teleport(user));
    }
}
