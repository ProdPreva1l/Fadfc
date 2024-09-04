package info.preva1l.fadfc.api;

import info.preva1l.fadfc.managers.FactionManager;
import info.preva1l.fadfc.managers.IFactionManager;

public class ImplFactionsAPI extends FactionsAPI {
    @Override
    public IFactionManager getFactionManager() {
        return FactionManager.getInstance();
    }
}
