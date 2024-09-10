package info.preva1l.fadfc.managers;

import info.preva1l.fadfc.Fadfc;
import info.preva1l.fadfc.menus.lib.FastInv;
import info.preva1l.fadfc.menus.lib.GuiLayout;
import info.preva1l.fadfc.utils.Logger;
import info.preva1l.fadfc.utils.Text;
import info.preva1l.fadfc.utils.config.BasicConfig;
import info.preva1l.fadfc.utils.config.LanguageConfig;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LayoutManager {
    private static LayoutManager instance;
    private final List<GuiLayout> guiLayouts = new ArrayList<>();

    public static LayoutManager getInstance() {
        if (instance == null) {
            instance = new LayoutManager();
        }
        return instance;
    }
    
    public void loadLayout(BasicConfig config) {
        final MenuType menuType = getMenuType(config.getFileName());

        final String guiTitle = Text.legacyMessage(config.getString("title"));

        final List<Integer> fillerSlots = new ArrayList<>();
        final List<Integer> paginationSlots = new ArrayList<>();
        final List<Integer> scrollbarSlots = new ArrayList<>();
        final List<Integer> noItems = new ArrayList<>();
        final HashMap<ButtonType, Integer> buttonSlots = new HashMap<>();

        final ConfigurationSection superSection = config.getConfiguration().getConfigurationSection("lang");
        if (superSection == null) {
            Logger.severe("Gui Layout for the GUI %s is invalid! Missing the lang config section.".formatted(menuType.toString()));
            return;
        }
        final LanguageConfig languageConfig = new LanguageConfig(superSection);

        final ConfigurationSection layoutSection = config.getConfiguration().getConfigurationSection("layout");
        if (layoutSection == null) {
            Logger.severe("Gui Layout for the GUI %s is invalid! Missing the layout config section.".formatted(menuType.toString()));
            return;
        }

        final int guiSize = config.getInt("size") * 9;

        for (String key : layoutSection.getKeys(false)) {
            int slotNumber = Integer.parseInt(key);
            if (slotNumber > guiSize - 1) {
                Logger.severe("Gui Layout for the GUI %s is invalid! Slot: %s is out of bounds for gui size %s (%s)".formatted(menuType.toString(), slotNumber, guiSize, guiSize/9));
                return;
            }

            ButtonType buttonType;
            String temp2 = layoutSection.getString(key);
            if (temp2 == null || temp2.isBlank()) {
                Logger.severe("Gui Layout for the GUI %s is invalid! Slot: %s is an empty string?".formatted(menuType.toString(), slotNumber));
                return;
            }

            try {
                buttonType = ButtonType.valueOf(temp2);
            } catch (IllegalArgumentException e) {
                Logger.severe("Gui Layout for the GUI %s is invalid! Slot: %s Button Type %s does not exist!".formatted(menuType.toString(), slotNumber, temp2));
                return;
            }

            if (buttonType.equals(ButtonType.FILLER)) {
                fillerSlots.add(slotNumber);
                continue;
            }

            if (buttonType.equals(ButtonType.PAGINATION_ITEM)) {
                paginationSlots.add(slotNumber);
                continue;
            }

            if (buttonType.equals(ButtonType.SCROLLBAR_ITEM)) {
                scrollbarSlots.add(slotNumber);
                continue;
            }

            if (buttonType.equals(ButtonType.NO_ITEMS)) {
                paginationSlots.add(slotNumber);
                noItems.add(slotNumber);
                continue;
            }

            buttonSlots.put(buttonType, slotNumber);
        }

        guiLayouts.add(new GuiLayout(menuType, fillerSlots, paginationSlots, scrollbarSlots, noItems, buttonSlots, guiTitle, guiSize, languageConfig, config));
    }

    private MenuType getMenuType(String fileName) {
        String[] temp = fileName.split("/");
        return switch (temp[temp.length - 1]) {
            case "claim.yml": yield MenuType.CLAIM;
            default: throw new IllegalStateException("The config file %s is not related to a GuiLayout".formatted(fileName));
        };
    }

    public void reloadLayout(MenuType menuType) {
        final String temp = menuType.getLayout().extraConfig().getFileName();
        guiLayouts.removeIf(mT -> mT.menuType().equals(menuType));
        loadLayout(new BasicConfig(Fadfc.i(), temp));
    }

    public @NotNull GuiLayout getLayout(MenuType menuType) {
        for (GuiLayout layout : guiLayouts) {
            if (menuType == layout.menuType()) return layout;
        }
        throw new IllegalStateException("No GuiLayout found for inventory type %s".formatted(menuType));
    }

    public @NotNull GuiLayout getLayout(FastInv inventory) {
        for (GuiLayout layout : guiLayouts) {
            if (inventory.getMenuType() == layout.menuType()) return layout;
        }
        throw new IllegalStateException("No GuiLayout found for inventory type %s".formatted(inventory.getMenuType()));
    }

    public enum MenuType {
        CLAIM,
        ;

        public GuiLayout getLayout() {
            return LayoutManager.getInstance().getLayout(this);
        }

        public String getTitle() {
            return getLayout().guiTitle();
        }

        public int getSize() {
            return getLayout().guiSize();
        }
    }

    public enum ButtonType {
        /**
         * Pagination Buttons
         */
        PAGINATION_CONTROL_ONE,
        PAGINATION_CONTROL_TWO,
        PAGINATION_ITEM,
        /**
         * Scrollbar buttons
         */
        SCROLLBAR_CONTROL_ONE,
        SCROLLBAR_CONTROL_TWO,
        SCROLLBAR_ITEM,
        /**
         * Claim Buttons
         */
        CHUNK,
        CURRENT_CHUNK,
        MANAGE_GROUPS,
        MANAGE_CLAIMS,
        /**
         * Misc Items
         */
        NO_ITEMS,
        FILLER,
        BACK,
        CLOSE,
    }
}