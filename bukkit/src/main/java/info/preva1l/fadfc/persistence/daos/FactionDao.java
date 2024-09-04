package info.preva1l.fadfc.persistence.daos;

import info.preva1l.fadfc.models.IFaction;
import info.preva1l.fadfc.persistence.Dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FactionDao implements Dao<IFaction> {
    /**
     * Get an object from the database by its id.
     *
     * @param id the id of the object to get.
     * @return an optional containing the object if it exists, or an empty optional if it does not.
     */
    @Override
    public Optional<IFaction> get(UUID id) {
        return Optional.empty();
    }

    /**
     * Get all objects of type T from the database.
     *
     * @return a list of all objects of type T in the database.
     */
    @Override
    public List<IFaction> getAll() {
        return List.of();
    }

    /**
     * Save an object of type T to the database.
     *
     * @param iFaction the object to save.
     */
    @Override
    public void save(IFaction iFaction) {

    }

    /**
     * Update an object of type T in the database.
     *
     * @param iFaction the object to update.
     * @param params   the parameters to update the object with.
     */
    @Override
    public void update(IFaction iFaction, String[] params) {

    }

    /**
     * Delete an object of type T from the database.
     *
     * @param iFaction the object to delete.
     */
    @Override
    public void delete(IFaction iFaction) {

    }
}
