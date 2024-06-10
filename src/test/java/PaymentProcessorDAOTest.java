package test.java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import main.java.dao.PaymetProcessorDAO;
import main.java.dto.PaymentProcessorDTO;
import main.java.entity.PaymentProcessor;
import main.java.utility.DatabaseConnection;
import main.java.utility.DbConstants;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
public class PaymentProcessorDAOTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private Statement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    @InjectMocks
    private PaymetProcessorDAO paymetProcessorDAO;

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
    void insertPaymentProcessor_InputPaymentProcessorDetails_ReturnsNewPaymentProcessorId() throws SQLException {
        PaymentProcessorDTO paymentProcessorDTO = new PaymentProcessorDTO("Stripe");

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(1);

        int newId = paymetProcessorDAO.insertPaymentProcessor(paymentProcessorDTO);

        assertEquals(1, newId);
        verify(mockPreparedStatement).setString(1, "Stripe");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void getAllPaymentProcessors_ReturnsAllPaymentProcessors() throws SQLException {
        List<PaymentProcessor> expectedPaymentProcessors = new ArrayList<>();
        expectedPaymentProcessors.add(new PaymentProcessor(1, "Stripe"));
        expectedPaymentProcessors.add(new PaymentProcessor(2, "Keap Pay"));

        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt(DbConstants.ID)).thenReturn(1, 2);
        when(mockResultSet.getString(DbConstants.NAME)).thenReturn("Stripe", "Keap Pay");

        List<PaymentProcessor> actualPaymentProcessors = paymetProcessorDAO.getAllPaymentProcessors();

        assertEquals(expectedPaymentProcessors, actualPaymentProcessors);
    }

    @Test
    void getPaymentProcessor_GivenPaymentProcessorId_ReturnsPaymentProcessor() throws SQLException {
        PaymentProcessor expectedPaymentProcessor = new PaymentProcessor(1, "Stripe");

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(DbConstants.ID)).thenReturn(1);
        when(mockResultSet.getString(DbConstants.NAME)).thenReturn("Stripe");

        PaymentProcessor actualPaymentProcessor = paymetProcessorDAO.getPaymentProcessor(1);

        assertEquals(expectedPaymentProcessor, actualPaymentProcessor);
        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).executeQuery();
    }
}
