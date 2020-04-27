package ua.nure.andreiko.airline.db;

import org.apache.log4j.Logger;
import ua.nure.andreiko.airline.db.entity.*;
import ua.nure.andreiko.airline.exception.DBException;
import ua.nure.andreiko.airline.exception.Messages;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DB manager. Works with MySQL DB.  Only the required DAO methods are
 * defined!
 *
 * @author E.Andreiko
 */
public class DBManager {
    private static final Logger LOG = Logger.getLogger(DBManager.class);

    private static DBManager instance;

    public static synchronized DBManager getInstance() throws DBException {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() throws DBException {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            // airline - the name of data source
            dataSource = (DataSource) envContext.lookup("jdbc/airline");
            LOG.trace("Data source ==> " + dataSource);
        } catch (NamingException ex) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
        }
    }

    private DataSource dataSource;

    /**
     * Returns a DB connection from the Pool Connections. Before using this
     * method you must configure the Date Source and the Connections Pool in
     * your WEB_APP_ROOT/META-INF/context.xml file.
     *
     * @return DB connection.
     */

    public Connection getConnection() throws DBException {
        Connection con;
        try {
            con = dataSource.getConnection();
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
        }
        return con;
    }

    /**
     * Returns a user with the given login.
     *
     * @param login User login.
     * @return User entity.
     * @throws DBException
     */

    public User findUserByLogin(String login) throws DBException {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rS = null;
        Connection con = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(Const.SQL_FIND_USER_BY_LOGIN);
            pstmt.setString(1, login);
            rS = pstmt.executeQuery();
            if (rS.next()) {
                user = DBUtils.extractUser(rS);
            }
            con.commit();
        } catch (SQLException e) {
            DBUtils.rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, e);
        } finally {
            DBUtils.close(con, pstmt, rS);
        }
        return user;
    }

    /**
     * @return list flights
     * @throws DBException
     */

    public List<Flights> findFlights() throws DBException {
        List<Flights> flightsList = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(Const.SQL_FIND_FLIGHTS);
            while (resultSet.next()) {
                flightsList.add(DBUtils.extractFlights(resultSet));
            }
            connection.commit();
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_FLIGHTS, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_FLIGHTS, e);
        } finally {
            DBUtils.close(connection, statement, resultSet);
        }
        return flightsList;
    }

    /**
     * Returns a flight with the given identifier.
     *
     * @param id Flight identifier.
     * @return Flight entity.
     * @throws DBException
     */

    public Flights getFlightById(int id) throws DBException {
        Flights flight = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(Const.SQL_FIND_FLIGHTS_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                flight = DBUtils.extractFlights(resultSet);
            }
            connection.commit();
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_FLIGHTS_BY_ID, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_FLIGHTS_BY_ID, e);
        } finally {
            DBUtils.close(connection, preparedStatement, resultSet);
        }
        return flight;
    }

    /**
     * Create new flight.
     *
     * @param number Flight number.
     * @param from   Where is the flight from.
     * @param where  To where the flight.
     * @param date   Departure date flight.
     * @param name   Name flight.
     * @throws DBException
     */

    public void createFlight(String number, String from, String where, String date, String name)
            throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(Const.SQL_CREATE_FLIGHT);

            preparedStatement.setString(1, number);
            preparedStatement.setString(2, from);
            preparedStatement.setString(3, where);
            preparedStatement.setString(4, date);
            preparedStatement.setString(5, name);


            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_CREATE_FLIGHT, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_CREATE_FLIGHT, e);
        } finally {
            DBUtils.close(connection, preparedStatement);
        }
    }

    /**
     * Returns a flight with the given identifier and status identifier.
     *
     * @param number Flight number.
     * @return Flight entity.
     * @throws DBException
     */

    public Flights getFlightIdStatus(String number) throws DBException {
        Flights flight = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(Const.SQL_FIND_FLIGHTS_BY_NUMBER);
            preparedStatement.setString(1, number);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                flight = DBUtils.extractFlights(resultSet);
            }
            connection.commit();
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_FLIGHTS_BY_NUMBER, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_FLIGHTS_BY_NUMBER, e);
        } finally {
            DBUtils.close(connection, preparedStatement, resultSet);
        }
        return flight;
    }

    /**
     * Returns flights of the given number.
     *
     * @param number Flight entity.
     * @return List of flight entities.
     * @throws DBException
     */

    public List<Flights> findFlightsByNumber(String number) throws DBException {
        List<Flights> flights = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(Const.SQL_FIND_FLIGHTS_BY_NUMBER);
            preparedStatement.setString(1, number);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                flights.add(DBUtils.extractFlights(resultSet));
            }
            connection.commit();
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_FLIGHTS_BY_NUMBER, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_FLIGHTS_BY_NUMBER, e);
        } finally {
            DBUtils.close(connection, preparedStatement, resultSet);
        }
        return flights;
    }

    /**
     * Remove flight.
     *
     * @param id flight to remove.
     * @throws DBException
     */

    public void removeFlight(int id) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(Const.SQL_REMOVE_FLIGHT);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_REMOVE_FLIGHTS, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_REMOVE_FLIGHTS, e);
        }
    }

    /**
     * Remove worker.
     *
     * @param id worker to remove.
     * @throws DBException
     */
    public void removeWorker(int id) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(Const.SQL_REMOVE_WORKER);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_REMOVE_WORKER, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_REMOVE_WORKER, e);
        }
    }

    /**
     * Returns a worker with the given identifier.
     *
     * @param id Worker identifier.
     * @return Worker entity.
     * @throws DBException
     */

    public Workers getWorker(int id) throws DBException {
        Workers worker = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(Const.SQL_GET_WORKER);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                worker = DBUtils.extractWorkersIdRank(resultSet);
            }
            connection.commit();
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_FLIGHTS_BY_NUMBER, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_FLIGHTS_BY_NUMBER, e);
        } finally {
            DBUtils.close(connection, preparedStatement, resultSet);
        }
        return worker;
    }

    /**
     * Create new worker.
     *
     * @param firstName Employee first name.
     * @param lastName  Employee last name.
     * @param rankId    Employee rank identifier.
     * @throws DBException
     */

    public void createWorker(String firstName, String lastName, int rankId)
            throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(Const.SQL_CREATE_WORKER);

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, rankId);


            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_CREATE_WORKER, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_CREATE_WORKER, e);
        } finally {
            DBUtils.close(connection, preparedStatement);
        }
    }

    /**
     * Returns flights of the given where is the flight from.
     *
     * @param isFrom Flight entity
     * @return List of flight entities.
     * @throws DBException
     */

    public List<Flights> findFlightsIsFrom(String isFrom) throws DBException {
        List<Flights> flights = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(Const.SQL_FIND_FLIGHTS_FROM);
            preparedStatement.setString(1, isFrom);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                flights.add(DBUtils.extractFlights(resultSet));
            }
            connection.commit();
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_FLIGHTS_BY_FROM, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_FLIGHTS_BY_FROM, e);
        } finally {
            DBUtils.close(connection, preparedStatement, resultSet);
        }
        return flights;
    }

    /**
     * Update status flight.
     *
     * @param flight to update.
     * @throws DBException
     */

    public void updateStatusFlight(Flights flight) throws DBException {
        Connection connection = getConnection();
        try {
            DBUtils.updateFlightStatus(connection, flight);
            connection.commit();
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            LOG.error(Messages.ERR_CANNOT_UPDATE_WORKER, e);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_WORKER, e);
        } finally {
            DBUtils.close(connection);
        }
    }

    /**
     * Return all employees.
     *
     * @return List of worker entities.
     * @throws DBException
     */

    public List<Workers> findWorkers() throws DBException {
        List<Workers> workersList = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(Const.SQL_GET_WORKERS);
            while (resultSet.next()) {
                workersList.add(DBUtils.extractWorkers(resultSet));
            }
            connection.commit();
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_WORKERS, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_WORKERS, e);
        } finally {
            DBUtils.close(connection, statement, resultSet);
        }
        return workersList;
    }

    /**
     * Create new brigade.
     *
     * @param pilot      Worker rank.
     * @param navigator  Worker rank.
     * @param operator   Worker rank.
     * @param stewardess Worker rank.
     * @param flight     Number flight.
     * @throws DBException
     */

    public void addWorkersToBrigades(int pilot, int navigator, int operator, int stewardess, String flight)
            throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(Const.SQL_CREATE_BRIGADE);

            preparedStatement.setInt(1, pilot);
            preparedStatement.setInt(2, navigator);
            preparedStatement.setInt(3, operator);
            preparedStatement.setInt(4, stewardess);
            preparedStatement.setString(5, flight);

            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_CREATE_BRIGADES, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_CREATE_BRIGADES, e);
        } finally {
            DBUtils.close(connection, preparedStatement);
        }
    }

    /**
     * Update worker.
     *
     * @param worker worker to update.
     * @throws DBException
     */

    public void updateWorkers(Workers worker) throws DBException {
        Connection connection = getConnection();
        try {
            DBUtils.updateWorkerBrigade(connection, worker);
            connection.commit();
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            LOG.error(Messages.ERR_CANNOT_UPDATE_WORKER, e);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_WORKER, e);
        } finally {
            DBUtils.close(connection);
        }
    }

    /**
     * Update flight.
     *
     * @param flight flight to update.
     * @throws DBException
     */

    public void updateFlight(Flights flight) throws DBException {
        Connection connection = getConnection();
        try {
            DBUtils.updateFlightBrigade(connection, flight);
            connection.commit();
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            LOG.error(Messages.ERR_CANNOT_UPDATE_FLIGHT, e);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_FLIGHT, e);
        } finally {
            DBUtils.close(connection);
        }
    }

    /**
     * Returns a brigade with the given flight number.
     *
     * @param numberFlight Flight number
     * @return Brigades entity.
     * @throws DBException
     */

    public Brigades getIdBrigades(String numberFlight) throws DBException {
        Brigades brigade = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(Const.SQL_GET_BRIGADE);
            preparedStatement.setString(1, numberFlight);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                brigade = DBUtils.extractBrigades(resultSet);
            }
            connection.commit();
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_BRIGADES, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_BRIGADES, e);
        } finally {
            DBUtils.close(connection, preparedStatement, resultSet);
        }
        return brigade;
    }

    /**
     * Return all pilots.
     *
     * @return List of pilots.
     * @throws DBException
     */

    public List<Workers> getPilots() throws DBException {
        List<Workers> pilotsList = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(Const.SQL_GET_PILOTS);
            while (resultSet.next()) {
                pilotsList.add(DBUtils.extractWorkersIdRank(resultSet));
            }
            connection.commit();
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_FLIGHTS, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_FLIGHTS, e);
        } finally {
            DBUtils.close(connection, statement, resultSet);
        }
        return pilotsList;
    }

    /**
     * Return all navigator.
     *
     * @return List of navigator.
     * @throws DBException
     */

    public List<Workers> getNavigator() throws DBException {
        List<Workers> navigatorList = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(Const.SQL_GET_NAVIGATOR);
            while (resultSet.next()) {
                navigatorList.add(DBUtils.extractWorkersIdRank(resultSet));
            }
            connection.commit();
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_FLIGHTS, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_FLIGHTS, e);
        } finally {
            DBUtils.close(connection, statement, resultSet);
        }
        return navigatorList;
    }

    /**
     * Return all operator.
     *
     * @return List of operator.
     * @throws DBException
     */

    public List<Workers> getOperator() throws DBException {
        List<Workers> operatorList = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(Const.SQL_GET_OPERATOR);
            while (resultSet.next()) {
                operatorList.add(DBUtils.extractWorkersIdRank(resultSet));
            }
            connection.commit();
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_FLIGHTS, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_FLIGHTS, e);
        } finally {
            DBUtils.close(connection, statement, resultSet);
        }
        return operatorList;
    }

    /**
     * Return all stewardess.
     *
     * @return List of stewardess.
     * @throws DBException
     */

    public List<Workers> getStewardess() throws DBException {
        List<Workers> stewardessList = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(Const.SQL_GET_STEWARDESS);
            while (resultSet.next()) {
                stewardessList.add(DBUtils.extractWorkersIdRank(resultSet));
            }
            connection.commit();
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_FLIGHTS, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_FLIGHTS, e);
        } finally {
            DBUtils.close(connection, statement, resultSet);
        }
        return stewardessList;
    }

    /**
     * Create new application.
     *
     * @param subject Application subject.
     * @param message Application message.
     * @throws DBException
     */

    public void createApp(String subject, String message)
            throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(Const.SQL_CREATE_APPLICATION);

            preparedStatement.setString(1, subject);
            preparedStatement.setString(2, message);


            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_CREATE_APPLICATION, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_CREATE_APPLICATION, e);
        } finally {
            DBUtils.close(connection, preparedStatement);
        }
    }

    /**
     * Returns all application.
     *
     * @return List of application entities.
     * @throws DBException
     */

    public List<Application> findApp() throws DBException {
        List<Application> applicationList = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(Const.SQL_FIND_APPLICATION);
            while (resultSet.next()) {
                applicationList.add(DBUtils.extractApplication(resultSet));
            }
            connection.commit();
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_APPLICATION, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_APPLICATION, e);
        } finally {
            DBUtils.close(connection, statement, resultSet);
        }
        return applicationList;
    }

    /**
     * Returns a application with the given identifier.
     *
     * @param id Application identifier.
     * @return Application entity.
     * @throws DBException
     */
    public Application getAppIdStatus(int id) throws DBException {
        Application application = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(Const.SQL_GET_APPLICATION_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                application = DBUtils.extractApplication(resultSet);
            }
            connection.commit();
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_APPLICATION_BY_ID, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_APPLICATION_BY_ID, e);
        } finally {
            DBUtils.close(connection, preparedStatement, resultSet);
        }
        return application;
    }

    /**
     * Update application status.
     *
     * @param application Application to update.
     * @throws DBException
     */

    public void updateStatusApplication(Application application) throws DBException {
        Connection connection = getConnection();
        try {
            DBUtils.updateApplicationStatus(connection, application);
            connection.commit();
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            LOG.error(Messages.ERR_CANNOT_UPDATE_APPLICATION_STATUS, e);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_APPLICATION_STATUS, e);
        } finally {
            DBUtils.close(connection);
        }
    }

    /**
     * Update flight number.
     *
     * @param flight Flight to update.
     * @throws DBException
     */

    public void updateNumberFlight(Flights flight) throws DBException {
        Connection connection = getConnection();
        try {
            DBUtils.updateNumberFlight(connection, flight);
            connection.commit();
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            LOG.error(Messages.ERR_CANNOT_UPDATE_FLIGHT_NUMBER, e);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_FLIGHT_NUMBER, e);
        } finally {
            DBUtils.close(connection);
        }
    }

    /**
     * Update date flight.
     *
     * @param flight Flight to update.
     * @throws DBException
     */

    public void updateDateFlight(Flights flight) throws DBException {
        Connection connection = getConnection();
        try {
            DBUtils.updateDateFlight(connection, flight);
            connection.commit();
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            LOG.error(Messages.ERR_CANNOT_UPDATE_FLIGHT_DATE, e);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_FLIGHT_DATE, e);
        } finally {
            DBUtils.close(connection);
        }
    }

    /**
     * Update last name worker.
     *
     * @param worker Worker to update.
     * @throws DBException
     */

    public void updateLastNameWorker(Workers worker) throws DBException {
        Connection connection = getConnection();
        try {
            DBUtils.updateLastNameWorker(connection, worker);
            connection.commit();
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            LOG.error(Messages.ERR_CANNOT_UPDATE_WORKER_LAST_NAME, e);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_WORKER_LAST_NAME, e);
        } finally {
            DBUtils.close(connection);
        }
    }

    /**
     * Update worker rank.
     *
     * @param worker Worker to update.
     * @throws DBException
     */

    public void updateRankWorker(Workers worker) throws DBException {
        Connection connection = getConnection();
        try {
            DBUtils.updateRankWorker(connection, worker);
            connection.commit();
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            LOG.error(Messages.ERR_CANNOT_UPDATE_WORKER_RANK, e);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_WORKER_RANK, e);
        } finally {
            DBUtils.close(connection);
        }
    }
}
