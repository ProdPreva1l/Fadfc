package info.preva1l.fadfc.commands.lib;

import info.preva1l.fadfc.Fadfc;
import info.preva1l.fadfc.models.user.CommandUser;
import info.preva1l.fadfc.models.user.ConsoleUser;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BasicCommand {
    private final Command info;
    @Getter private final List<BasicSubCommand> subCommands;

    public BasicCommand() {
        this.info = getClass().getAnnotation(Command.class);
        this.subCommands = new ArrayList<>();

        if (info == null) {
            throw new RuntimeException("BasicCommand must be annotated with @Command");
        }
    }

    public abstract void execute(CommandUser sender, String[] args);

    public List<String> tabComplete(CommandUser sender, String[] args) {
        return new ArrayList<>();
    }

    /**
     * Handles subcommand execution easily.
     * @param sender command sender
     * @param args command args
     * @return true if the subcommand was executed,
     * false if the sender does not have permission or if the sender was console on a player only command
     */
    public boolean subCommandExecutor(CommandUser sender, String[] args) {
        for (BasicSubCommand subCommand : getSubCommands()) {
            if (args[0].equalsIgnoreCase(subCommand.getName())
                    || Arrays.stream(subCommand.getAliases()).collect(Collectors.toList()).contains(args[0])) {

                if (subCommand.isInGameOnly() && sender instanceof ConsoleUser) {
                    sender.sendMessage("MUST BE PLAYER LOCALE");
                    return false;
                }

                if (!subCommand.getPermission().isEmpty() && !sender.hasPermission(subCommand.getPermission())) {
                    sender.sendMessage("NO PERMISSION LOCALE");
                    return false;
                }

                if (subCommand.isAsync()) {
                    Bukkit.getServer().getScheduler().runTaskAsynchronously(Fadfc.getPlugin(Fadfc.class),
                            () -> subCommand.execute(sender, removeFirstElement(args)));
                } else {
                    Bukkit.getServer().getScheduler().runTask(Fadfc.getPlugin(Fadfc.class),
                            () -> subCommand.execute(sender, removeFirstElement(args)));
                }
                return true;
            }
        }
        return false;
    }

    protected String[] removeFirstElement(String[] array) {
        if (array == null || array.length == 0) {
            return new String[]{};
        }

        String[] newArray = new String[array.length - 1];
        System.arraycopy(array, 1, newArray, 0, array.length - 1);

        return newArray;
    }

    public String getName() {
        return info.name();
    }

    public String getPermission() {
        return info.permission();
    }

    public String[] getAliases() {
        return info.aliases();
    }

    public boolean isInGameOnly() {
        return info.inGameOnly();
    }

    public boolean isAsync() {
        return info.async();
    }
}