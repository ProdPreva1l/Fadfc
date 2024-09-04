package info.preva1l.fadfc.persistence.handlers;

import com.zaxxer.hikari.HikariDataSource;
import info.preva1l.fadfc.Fadfc;
import info.preva1l.fadfc.config.Config;
import info.preva1l.fadfc.models.IFaction;
import info.preva1l.fadfc.persistence.Dao;
import info.preva1l.fadfc.persistence.DatabaseHandler;
import info.preva1l.fadfc.persistence.DatabaseObject;
import info.preva1l.fadfc.persistence.daos.FactionDao;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class MySQLHandler implements DatabaseHandler {
    private final Map<Class<?>, Dao<?>> daos = new HashMap<>();
    private final String driverClass;
    @Getter private boolean connected = false;
    private HikariDataSource dataSource;

    public MySQLHandler() {
        this.driverClass = Config.getInstance().getStorage().getType().getDriverClass();
    }

    @SuppressWarnings("SameParameterValue")
    @NotNull
    private String[] getSchemaStatements(@NotNull String schemaFileName) throws IOException {
        return new String(Objects.requireNonNull(Fadfc.i().getResource(schemaFileName))
                .readAllBytes(), StandardCharsets.UTF_8).split(";");
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void connect() {
        Config.Storage sql = Config.getInstance().getStorage();

        dataSource = new HikariDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setJdbcUrl(String.format("jdbc:%s://%s:%d/%s", Config.getInstance().getStorage().getType().getId(), sql.getHost(), sql.getPort(), sql.getDatabase()));
        dataSource.setUsername(sql.getUsername());
        dataSource.setPassword(sql.getPassword());

        dataSource.setMaximumPoolSize(10);
        dataSource.setMinimumIdle(10);
        dataSource.setMaxLifetime(1800000);
        dataSource.setKeepaliveTime(0);
        dataSource.setConnectionTimeout(5000);
        dataSource.setPoolName("PrisonCoreHikariPool");

        final Properties properties = new Properties();
        properties.putAll(
                Map.of("cachePrepStmts", "true",
                        "prepStmtCacheSize", "250",
                        "prepStmtCacheSqlLimit", "2048",
                        "useServerPrepStmts", "true",
                        "useLocalSessionState", "true",
                        "useLocalTransactionState", "true"
                ));
        properties.putAll(
                Map.of(
                        "rewriteBatchedStatements", "true",
                        "cacheResultSetMetadata", "true",
                        "cacheServerConfiguration", "true",
                        "elideSetAutoCommits", "true",
                        "maintainTimeStats", "false")
        );
        dataSource.setDataSourceProperties(properties);

        try (Connection connection = dataSource.getConnection()) {
            final String[] databaseSchema = getSchemaStatements(String.format("database/%s_schema.sql", Config.getInstance().getStorage().getType().getId()));
            try (Statement statement = connection.createStatement()) {
                for (String tableCreationStatement : databaseSchema) {
                    statement.execute(tableCreationStatement);
                }
                connected = true;
            } catch (SQLException e) {
                destroy();
                throw new IllegalStateException("Failed to create database tables. Please ensure you are running MySQL v8.0+ " +
                        "and that your connecting user account has privileges to create tables.", e);
            }
        } catch (SQLException | IOException e) {
            destroy();
            throw new IllegalStateException("Failed to establish a connection to the MySQL database. " +
                    "Please check the supplied database credentials in the config file", e);
        }
        registerDaos();
    }

    public void destroy() {
        if (dataSource != null) dataSource.close();
    }

    @Override
    public void wipeDatabase() {
        // do nun
    }

    public void registerDaos() {
        registerDao(IFaction.class, new FactionDao());
    }

    @Override
    public <T extends DatabaseObject> List<T> getAll(Class<T> clazz) {
        return (List<T>) getDao(clazz).getAll();
    }

    @Override
    public <T extends DatabaseObject> Optional<T> get(Class<T> clazz, UUID id) {
        return (Optional<T>) getDao(clazz).get(id);
    }

    @Override
    public <T extends DatabaseObject> Optional<T> search(Class<T> clazz, String search) {
        return (Optional<T>) getDao(clazz).get(search);
    }

    @Override
    public <T extends DatabaseObject> void save(Class<T> clazz, T t) {
        getDao(clazz).save(t);
    }

    @Override
    public <T extends DatabaseObject> void update(Class<T> clazz, T t, String[] params) {
        getDao(clazz).update(t, params);
    }

    @Override
    public <T extends DatabaseObject> void delete(Class<T> clazz, T t) {
        getDao(clazz).delete(t);
    }

    @Override
    public <T extends DatabaseObject> void deleteSpecific(Class<T> clazz, T t, Object o) {
        getDao(clazz).deleteSpecific(t, o);
    }

    @Override
    public void registerDao(Class<?> aClass, Dao<? extends DatabaseObject> dao) {
        daos.put(aClass, dao);
    }

    /**
     * Gets the DAO for a specific class.
     *
     * @param clazz The class to get the DAO for.
     * @param <T>   The type of the class.
     * @return The DAO for the specified class.
     */
    private <T extends DatabaseObject> Dao<T> getDao(Class<?> clazz) {
        if (!daos.containsKey(clazz))
            throw new IllegalArgumentException("No DAO registered for class " + clazz.getName());
        return (Dao<T>) daos.get(clazz);
    }
}