package info.preva1l.fadfc.api;

import info.preva1l.fadfc.managers.IFactionManager;
import lombok.Getter;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public abstract class FactionsAPI {
    /**
     * -- GETTER --
     *  Get the instance of the Factions API.
     */
    @Getter private static FactionsAPI instance;

    /**
     * Get the factions manager to interact with factions.
     * @return the factions manager instance.
     */
    public abstract IFactionManager getFactionManager();

    @ApiStatus.Internal
    public static void setInstance(@NotNull FactionsAPI newInstance) {
        if (instance != null) {
            throw new IllegalStateException("FactionsAPI instance already set!");
        }
        instance = newInstance;
    }
}
