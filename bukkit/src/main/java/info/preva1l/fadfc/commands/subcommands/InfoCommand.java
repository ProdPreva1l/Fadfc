package info.preva1l.fadfc.commands.subcommands;

import info.preva1l.fadfc.commands.lib.BasicSubCommand;
import info.preva1l.fadfc.commands.lib.Command;
import info.preva1l.fadfc.models.user.CommandUser;

@Command(
        name = "info",
        permission = "fadfc.info"
)
public class InfoCommand extends BasicSubCommand {
    @Override
    public void execute(CommandUser sender, String[] args) {

    }
}
