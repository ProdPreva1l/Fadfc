package info.preva1l.fadfc.jobs;

import info.preva1l.fadfc.config.Config;
import info.preva1l.fadfc.managers.FactionManager;
import info.preva1l.fadfc.managers.PersistenceManager;
import info.preva1l.fadfc.managers.UserManager;
import info.preva1l.fadfc.models.IFaction;
import info.preva1l.fadfc.models.user.User;

import java.time.Duration;

public class SaveJobs {
    public static void startAll() {
        new FactionSaveJob().start();
        new UsersSaveJob().start();
    }

    public static class FactionSaveJob extends Job {
        public FactionSaveJob() {
            super("Factions Save", Duration.ofMinutes(Config.getInstance().getJobs().getFactionsSaveInterval()));
        }

        @Override
        protected void execute() {
            FactionManager.getInstance().getAllFactions().forEach(f ->
                    PersistenceManager.getInstance().save(IFaction.class, f));
        }
    }

    public static class UsersSaveJob extends Job {
        public UsersSaveJob() {
            super("Users Save", Duration.ofMinutes(Config.getInstance().getJobs().getUsersSaveInterval()));
        }

        @Override
        protected void execute() {
            UserManager.getInstance().getAllUsers().forEach(f ->
                    PersistenceManager.getInstance().save(User.class, f));
        }
    }
}
