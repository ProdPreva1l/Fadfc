package info.preva1l.fadfc.utils.config;

import info.preva1l.fadfc.utils.Logger;
import info.preva1l.fadfc.utils.Text;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LanguageConfig {
    private final ConfigurationSection superSection;

    public LanguageConfig(@NotNull ConfigurationSection superSection) {
        this.superSection = superSection;
    }

    public int getInt(String path, int def) {
        return superSection.getInt(path, def);
    }

    public int getInt(String path) {
        return superSection.getInt(path, 0);
    }

    public @NotNull Material getAsMaterial(String path) {
        return getAsMaterial(path, Material.APPLE);
    }

    public @NotNull Material getAsMaterial(String path, Material def) {
        Material material;
        String s = superSection.getString(path);
        if (s == null || s.isEmpty()) {
            throw new RuntimeException("No value at path %s".formatted(path));
        }
        try {
            material = Material.valueOf(s.toUpperCase());
        } catch (EnumConstantNotPresentException | IllegalArgumentException e) {
            material = def;
            Logger.severe("-----------------------------");
            Logger.severe("Config Incorrect!");
            Logger.severe("Material: " + s);
            Logger.severe("Does Not Exist!");
            Logger.severe("Defaulting to " + def.toString());
            Logger.severe("-----------------------------");
        }
        return material;
    }

    public @NotNull String getStringFormatted(String path)  {
        String f = superSection.getString(path);
        if (f == null || f.equals(path)) {
            throw new RuntimeException("No value at path %s".formatted(path));
        }
        return Text.legacyMessage(f);
    }

    public @NotNull String getString(String path, String def) {
        return superSection.getString(path, def);
    }

    public @NotNull String getStringFormatted(String path, String def) {
        return Text.legacyMessage(superSection.getString(path, def));
    }

    public @NotNull List<String> getLore(String path) {
        List<String> str = superSection.getStringList(path);
        List<String> newStr = new ArrayList<>();
        if (str.isEmpty() || str.get(0).equals(path) || str.get(0).equals("null")) {
            return Collections.emptyList();
        }
        for (String s : str) {
            newStr.add(Text.legacyMessage(s));
        }
        return newStr;
    }

    public @NotNull List<String> getLore(String path, List<String> def) {
        List<String> str = superSection.getStringList(path);
        List<String> newStr = new ArrayList<>();
        if (str.isEmpty() || str.get(0).equals(path) || str.get(0).equals("null")) {
            for (String s : str) {
                newStr.add(Text.legacyMessage(s));
            }
            return newStr;
        }
        for (String s : str) {
            newStr.add(Text.legacyMessage(s));
        }
        return newStr;
    }
}