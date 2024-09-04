package info.preva1l.fadfc.config;

import de.exlll.configlib.Configuration;
import de.exlll.configlib.NameFormatters;
import de.exlll.configlib.YamlConfigurationProperties;
import de.exlll.configlib.YamlConfigurations;
import info.preva1l.fadfc.Fadfc;
import info.preva1l.fadfc.utils.Logger;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;
import java.nio.charset.StandardCharsets;

@Getter
@Configuration
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("FieldMayBeFinal")
public class Lang {
    private static Lang instance;

    private static final String CONFIG_HEADER = """
            ##########################################
            #                  Fadfc                 #
            #     Language/Message Configuration     #
            ##########################################
            """;

    private static final YamlConfigurationProperties PROPERTIES = YamlConfigurationProperties.newBuilder()
            .charset(StandardCharsets.UTF_8)
            .setNameFormatter(NameFormatters.LOWER_KEBAB_CASE)
            .header(CONFIG_HEADER).build();


    private String prefix = "&#9555ff&lFACTIONS &8&l»&r";

    private Command command = new Command();

    @Getter
    @Configuration
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Command {
        private String noPermission = "&c&l(!)&r &fInsufficient permission";
        private String unknownArgs = "&c&l(!)&r &fUnknown arguments.";
        private String mustBePlayer = "&c&l(!)&r &fYou must be a player to run this command.";
    }

    private Errors errors = new Errors();

    @Getter
    @Configuration
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Errors {

    }

    public static void reload() {
        instance = YamlConfigurations.load(new File(Fadfc.i().getDataFolder(), "lang.yml").toPath(), Lang.class, PROPERTIES);
        Logger.info("Configuration automatically reloaded from disk.");
    }

    public static Lang getInstance() {
        if (instance == null) {
            instance = YamlConfigurations.update(new File(Fadfc.i().getDataFolder(), "lang.yml").toPath(), Lang.class, PROPERTIES);
            AutoReload.watch(Fadfc.i().getDataFolder().toPath(), "lang.yml", Lang::reload);
        }

        return instance;
    }
}