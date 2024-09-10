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
import org.bukkit.Material;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Getter
@Configuration
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("FieldMayBeFinal")
public class Menus {
    private static Menus instance;

    private static final String CONFIG_HEADER = """
            ##########################################
            #                  Fadfc                 #
            #    Miscellaneous Menu Configuration    #
            ##########################################
            """;

    private static final YamlConfigurationProperties PROPERTIES = YamlConfigurationProperties.newBuilder()
            .charset(StandardCharsets.UTF_8)
            .setNameFormatter(NameFormatters.LOWER_KEBAB_CASE)
            .header(CONFIG_HEADER).build();

    private NoItems noItems = new NoItems();

    @Getter
    @Configuration
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class NoItems implements ConfigurableItem {
        private Material material = Material.BARRIER;
        private int modelData = 0;
        private String name = "&cNo Items Found!";
        private List<String> lore = List.of("");
    }

    public static void reload() {
        instance = YamlConfigurations.load(new File(Fadfc.i().getDataFolder(), "menus/misc.yml").toPath(), Menus.class, PROPERTIES);
        Logger.info("Configuration '%s' automatically reloaded from disk.".formatted("menus/misc.yml"));
    }

    public static Menus getInstance() {
        if (instance == null) {
            instance = YamlConfigurations.update(new File(Fadfc.i().getDataFolder(), "menus/misc.yml").toPath(), Menus.class, PROPERTIES);
            AutoReload.watch(Fadfc.i().getDataFolder().toPath(), "menus/misc.yml", Menus::reload);
        }

        return instance;
    }
}