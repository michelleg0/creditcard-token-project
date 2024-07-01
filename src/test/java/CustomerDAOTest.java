package test.java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import main.java.dao.CustomerDAO;
import main.java.dto.CustomerDTO;
import main.java.entity.Customer;
import main.java.utility.DatabaseConnection;
import main.java.utility.DbConstants;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerDAOTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private Statement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    @InjectMocks
    private CustomerDAO customerDAO;

    private MockedStatic<DatabaseConnection> mockedDatabaseConnection;

    @BeforeEach
    public void setUp() {
        mockedDatabaseConnection = mockStatic(DatabaseConnection.class);
        mockedDatabaseConnection.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
    }

    @AfterEach
    public void tearDown() {
        mockedDatabaseConnection.close();
    }

    @Test
    void insertCustomer_InputCustomerDetails_ReturnsNewCustomerId() throws SQLException {
        CustomerDTO customerDTO = new CustomerDTO("John", "Doe", "john.doe@example.com");

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(1);

        int newId = customerDAO.insertCustomer(customerDTO);

        assertEquals(1, newId);
        verify(mockPreparedStatement).setString(1, "John");
        verify(mockPreparedStatement).setString(2, "Doe");
        verify(mockPreparedStatement).setString(3, "john.doe@example.com");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void insertCustomer_DatabaseRelatedIssue_ThrowsSQLException() throws SQLException {
        CustomerDTO customerDTO = new CustomerDTO("John", "Doe", "john.doe@example.com");

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("Database error"));

        assertThrows(SQLException.class, () -> customerDAO.insertCustomer(customerDTO));
    }

    @Test
    void getAllCustomers_ReturnsAllCustomers() throws SQLException {
        List<Customer> expectedCustomers = new ArrayList<>();
        expectedCustomers.add(new Customer(1, "John", "Doe", "john.doe@example.com"));
        expectedCustomers.add(new Customer(2, "Jane", "Smith", "jane.smith@example.com"));

        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt(DbConstants.ID)).thenReturn(1, 2);
        when(mockResultSet.getString(DbConstants.FIRST_NAME)).thenReturn("John", "Jane");
        when(mockResultSet.getString(DbConstants.LAST_NAME)).thenReturn("Doe", "Smith");
        when(mockResultSet.getString(DbConstants.EMAIL)).thenReturn("john.doe@example.com", "jane.smith@example.com");

        List<Customer> actualCustomers = customerDAO.getAllCustomers();

        assertEquals(expectedCustomers, actualCustomers);
    }

    @Test
    void getCustomer_GivenCustomerId_ReturnsCustomer() throws SQLException {
        Customer expectedCustomer = new Customer(1, "John", "Doe", "john.doe@example.com");

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(DbConstants.ID)).thenReturn(1);
        when(mockResultSet.getString(DbConstants.FIRST_NAME)).thenReturn("John");
        when(mockResultSet.getString(DbConstants.LAST_NAME)).thenReturn("Doe");
        when(mockResultSet.getString(DbConstants.EMAIL)).thenReturn("john.doe@example.com");

        Customer actualCustomer = customerDAO.getCustomer(1);

        assertEquals(expectedCustomer, actualCustomer);
        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).executeQuery();
    }
}