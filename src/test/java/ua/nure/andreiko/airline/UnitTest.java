package ua.nure.andreiko.airline;

import org.junit.Test;
import org.mockito.Mockito;
import ua.nure.andreiko.airline.db.DBManager;
import ua.nure.andreiko.airline.db.entity.*;
import ua.nure.andreiko.airline.exception.AppException;
import ua.nure.andreiko.airline.exception.DBException;

import java.sql.Connection;
import java.util.List;

public class UnitTest {
    DBManager dbManager = Mockito.mock(DBManager.class);
    List<Flights> flightsList;
    List<Workers> workersList;
    List<Application> applicationList;
    User user;
    Connection connection;
    Flights flights;
    Workers workers;
    Brigades brigades;
    Application application;

    @Test
    public void connectionTest() throws AppException {
        Mockito.when(dbManager.getConnection()).thenReturn(connection);
    }

    @Test
    public void findUserByLoginTest() throws DBException {
        Mockito.when(dbManager.findUserByLogin("user")).thenReturn(user);
    }

    @Test
    public void findFlightsTest() throws DBException {
        Mockito.when(dbManager.findFlights()).thenReturn(flightsList);
    }

    @Test
    public void getFlightByIdTest() throws DBException {
        Mockito.when(dbManager.getFlightById(1)).thenReturn(flights);
    }

    @Test
    public void getFlightStatusByIdTest() throws DBException {
        Mockito.when(dbManager.getFlightIdStatus("1488")).thenReturn(flights);
    }

    @Test
    public void findFlightsByNumberTest() throws DBException {
        Mockito.when(dbManager.findFlightsByNumber("1488")).thenReturn(flightsList);
    }

    @Test
    public void getWorkerTest() throws DBException {
        Mockito.when(dbManager.getWorker(1)).thenReturn(workers);
    }

    @Test
    public void findFlightsIsFromTest() throws DBException {
        Mockito.when(dbManager.findFlightsIsFrom("Kharkov")).thenReturn(flightsList);
    }

    @Test
    public void findWorkersTest() throws DBException {
        Mockito.when(dbManager.findWorkers()).thenReturn(workersList);
    }

    @Test
    public void getIdBrigadesTest() throws DBException {
        Mockito.when(dbManager.getIdBrigades("1488")).thenReturn(brigades);
    }

    @Test
    public void getPilotsTest() throws DBException {
        Mockito.when(dbManager.getPilots()).thenReturn(workersList);
    }

    @Test
    public void getNavigatorTest() throws DBException{
        Mockito.when(dbManager.getNavigator()).thenReturn(workersList);
    }

    @Test
    public void getOperatorTest() throws DBException{
        Mockito.when(dbManager.getOperator()).thenReturn(workersList);
    }

    @Test
    public void getStewardessTest() throws DBException{
        Mockito.when(dbManager.getStewardess()).thenReturn(workersList);
    }

    @Test
    public void findApplicationTest() throws DBException{
        Mockito.when(dbManager.findApp()).thenReturn(applicationList);
    }

    @Test
    public void getAppIdStatusTest() throws DBException{
        Mockito.when(dbManager.getAppIdStatus(1)).thenReturn(application);
    }
}
