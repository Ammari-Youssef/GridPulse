package com.youssef.GridPulse.domain.liquibase;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.sql.*;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
public class LiquibaseMigrationTest {

    static {
        // Configure Docker client explicitly
        TestcontainersConfiguration.getInstance()
                .updateUserConfig("docker.client.strategy", "org.testcontainers.dockerclient.UnixSocketClientProviderStrategy");
    }

    @Container
    public static PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:16-alpine")
                    .asCompatibleSubstituteFor("postgres")
    )
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", postgresqlContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", postgresqlContainer::getPassword);
        dynamicPropertyRegistry.add("spring.datasource.driver-class-name", postgresqlContainer::getDriverClassName);
    }

    @Test
    void connectionEstablished() {
        assertThat(postgresqlContainer.isCreated()).isTrue();
        assertThat(postgresqlContainer.isRunning()).isTrue();
    }

    @Test
    void allMigrationsApplySuccessfully() throws SQLException {
        // Manually test migrations without Spring autowiring
        testMigrationsWithManualConnection();
    }

    private void testMigrationsWithManualConnection() throws SQLException {
        // Wait for container to load
        postgresqlContainer.waitingFor(Wait.forListeningPort());

        String url = postgresqlContainer.getJdbcUrl();
        String username = postgresqlContainer.getUsername();
        String password = postgresqlContainer.getPassword();

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            // Clean up any existing state before verification
            cleanupDatabase(conn);

            // 1. First verify database is empty
            assertThat(tableExists(conn, "databasechangelog")).isFalse();

            assertThat(tableExists(conn, "_user")).isFalse();
            assertThat(tableExists(conn, "token")).isFalse();
            assertThat(tableExists(conn, "inverter")).isFalse();
            assertThat(tableExists(conn, "user_history")).isFalse();
            assertThat(tableExists(conn, "inverter_history")).isFalse();
            assertThat(tableExists(conn, "security_keys")).isFalse();


            // 2. Manually run Liquibase migrations
            runLiquibaseMigrations(conn);

            // 3. Verify migrations were applied
            assertThat(tableExists(conn, "databasechangelog")).isTrue();
            assertThat(tableExists(conn, "_user")).isTrue();
            assertThat(tableExists(conn, "token")).isTrue();
            assertThat(tableExists(conn, "inverter")).isTrue();
            assertThat(tableExists(conn, "user_history")).isTrue();
            assertThat(tableExists(conn, "inverter_history")).isTrue();
            assertThat(tableExists(conn, "security_keys")).isTrue();

            // 4. Check migration history
            verifyMigrationHistory(conn);
        } catch (LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }

    private void runLiquibaseMigrations(Connection conn) throws LiquibaseException {
        // Create Liquibase instance manually
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(
                new JdbcConnection(conn)
        );

        Liquibase liquibase = new Liquibase(
                "db/changelog/db.changelog-master.xml",
                new ClassLoaderResourceAccessor(),
                database
        );

        // Run all migrations
        liquibase.update(new Contexts());
    }

    private void verifyMigrationHistory(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT COUNT(*) as count, MAX(ID) as last_migration FROM databasechangelog"
             )) {

            rs.next();
            int migrationCount = rs.getInt("count");
            String lastMigration = rs.getString("last_migration");

            assertThat(migrationCount).isGreaterThan(0);
            assertThat(lastMigration).isNotBlank();
            System.out.println("✅ Applied " + migrationCount + " migrations, last: " + lastMigration);
        }
    }

    private void cleanupDatabase(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            // Disable foreign key constraints temporarily
            stmt.execute("SET CONSTRAINTS ALL DEFERRED");

            // Drop tables in reverse order of dependencies
            stmt.execute("DROP TABLE IF EXISTS token CASCADE");
            stmt.execute("DROP TABLE IF EXISTS user_history CASCADE");
            stmt.execute("DROP TABLE IF EXISTS inverter_history CASCADE");
            stmt.execute("DROP TABLE IF EXISTS security_keys CASCADE");
            stmt.execute("DROP TABLE IF EXISTS inverter CASCADE");
            stmt.execute("DROP TABLE IF EXISTS _user CASCADE");
            stmt.execute("DROP TABLE IF EXISTS databasechangelog CASCADE");
            stmt.execute("DROP TABLE IF EXISTS databasechangeloglock CASCADE");
        }
    }

    // Helper methods
    private boolean tableExists(Connection conn, String tableName) throws SQLException {
        ResultSet rs = conn.getMetaData().getTables(null, null, tableName, new String[]{"TABLE"});
        return rs.next();
    }
}
