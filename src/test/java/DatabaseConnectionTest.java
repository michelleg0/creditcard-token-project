package test.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import main.java.utility.Config;
import main.java.utility.DatabaseConnection;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.mockito.Mockito.mockStatic;

class DatabaseConnectionTest {
    private MockedStatic<DriverManager> mockedDriverManager;
    private MockedStatic<Config> mockedConfig;

    @BeforeEach
    void setUp() {
        mockedDriverManager = mockStatic(DriverManager.class);
        mockedConfig = mockStatic(Config.class);
    }

    @Test
    void getConnection_validConnectionDetails_SuccessfulConnection() throws SQLException {
        mockedConfig.when(() -> Config.get("db.url")).thenReturn("jdbc:mysql://localhost:3306/testdb");
        mockedConfig.when(() -> Config.get("db.user")).thenReturn("testuser");
        mockedConfig.when(() -> Config.get("db.password")).thenReturn("testpass");

        Connection mockConnection = Mockito.mock(Connection.class);
        mockedDriverManager.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                .thenReturn(mockConnection);

        Connection connection = DatabaseConnection.getConnection();

        assertNotNull(connection);

        mockedDriverManager.verify(() -> DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/testdb", "testuser", "testpass"));

        mockedDriverManager.close();
        mockedConfig.close();
    }
}