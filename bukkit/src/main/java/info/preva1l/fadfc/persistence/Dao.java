package info.preva1l.fadfc.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface representing a Data Access Object, a design pattern that provides an abstract interface to the database,
 * allowing for the separation of the database logic from the rest of the application.
 * @param <T> the type of the object that the DAO will be handling.
 */
public interface Dao<T extends DatabaseObject> {
    /**
     * Get an object from the database by its id.
     * @param id the id of the object to get.
     * @return an optional containing the object if it exists, or an empty optional if it does not.
     */
    Optional<T> get(UUID id);

    /**
     * Search for an object by a name or other string relation.
     * @param search the query to search for.
     * @return an optional containing the object if it exists, or an empty optional if it does not.
     */
    default Optional<T> get(String search) {
        throw new UnsupportedOperationException();
    }

    /**
     * Get all objects of type T from the database.
     * @return a list of all objects of type T in the database.
     */
    List<T> getAll();

    /**
     * Save an object of type T to the database.
     * @param t the object to save.
     */
    void save(T t);

    /**
     * Update an object of type T in the database.
     * @param t the object to update.
     * @param params the parameters to update the object with.
     */
    void update(T t, String[] params);

    /**
     * Delete an object of type T from the database.
     * @param t the object to delete.
     */
    void delete(T t);

    /**
     * Delete o from t if t is a collection
     * @param t the collection to delete from.
     * @param o the object to delete
     */
    default void deleteSpecific(T t, Object o) {
        throw new UnsupportedOperationException();
    }
}