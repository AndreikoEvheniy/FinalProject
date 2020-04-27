package ua.nure.andreiko.airline.db;

import org.apache.log4j.Logger;
import ua.nure.andreiko.airline.db.entity.*;
import ua.nure.andreiko.airline.exception.Messages;

import java.sql.*;

/**
 * Other methods and DB util for DBManager.
 *
 * @author E.Andreiko
 */

public class DBUtils {
    private static final Logger LOG = Logger.getLogger(DBUtils.class);

    /**
     * Extracts a user entity from the result set.
     *
     * @param rs Result set from which a user entity will be extracted.
     * @return User entity.
     * @throws SQLException
     */

    public static User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt(Fields.ENTITY_ID));
        user.setLogin(rs.getString(Fields.USER_LOGIN));
        user.setPassword(rs.getString(Fields.USER_PASSWORD));
        user.setRoleId(rs.getInt(Fields.USER_ROLES));
        return user;
    }

    /**
     * Extracts a flights entity from the result set.
     *
     * @param resultSet Result set from which a flights entity will be extracted.
     * @return Flight entity.
     * @throws SQLException
     */

    public static Flights extractFlights(ResultSet resultSet) throws SQLException {
        Flights flights = new Flights();
        flights.setId(resultSet.getInt(Fields.ENTITY_ID));
        flights.setNumber(resultSet.getInt(Fields.FLIGHTS_NUMBER));
        flights.setIsFrom(resultSet.getString(Fields.FLIGHTS_IS_FROM));
        flights.setWhereTo(resultSet.getString(Fields.FLIGHTS_WHERE_TO));
        flights.setDate(resultSet.getString(Fields.FLIGHTS_DATE));
        flights.setName(resultSet.getString(Fields.FLIGHTS_NAME));
        flights.setBrigade(resultSet.getInt(Fields.FLIGHTS_BRIGADE));
        flights.setStatus_id(resultSet.getString(Fields.FLIGHTS_STATUS));
        return flights;
    }

    /**
     * Extracts a applications entity from the result set.
     *
     * @param resultSet Result set from which an applications entity will be extracted.
     * @return Application entity.
     * @throws SQLException
     */

    public static Application extractApplication(ResultSet resultSet) throws SQLException {
        Application application = new Application();
        application.setId(resultSet.getInt(Fields.ENTITY_ID));
        application.setSubject(resultSet.getString(Fields.APPLICATION_SUBJECT));
        application.setMessage(resultSet.getString(Fields.APPLICATION_MESSAGE));
        application.setStatus_id(resultSet.getString(Fields.APPLICATION_STATUS));
        return application;
    }

    /**
     * Extracts a workers entity from the result set.
     *
     * @param resultSet Result set from which a workers entity will be extracted.
     * @return Worker entity.
     * @throws SQLException
     */

    public static Workers extractWorkers(ResultSet resultSet) throws SQLException {
        Workers workers = new Workers();
        workers.setId(resultSet.getInt(Fields.ENTITY_ID));
        workers.setFirstName(resultSet.getString(Fields.WORKERS_FIRST_NAME));
        workers.setLastName(resultSet.getString(Fields.WORKERS_LAST_NAME));
        workers.setWorkersRank(resultSet.getString(Fields.WORKERS_RANK));
        workers.setBrigade_id(resultSet.getInt(Fields.WORKERS_FLIGHT_NAME));
        return workers;
    }

    /**
     * Extracts a workers with identifier rank entity from the result set.
     *
     * @param resultSet Result set from which a workers entity will be extracted.
     * @return Worker entity.
     * @throws SQLException
     */

    public static Workers extractWorkersIdRank(ResultSet resultSet) throws SQLException {
        Workers workers = new Workers();
        workers.setId(resultSet.getInt(Fields.ENTITY_ID));
        workers.setFirstName(resultSet.getString(Fields.WORKERS_FIRST_NAME));
        workers.setLastName(resultSet.getString(Fields.WORKERS_LAST_NAME));
        workers.setWorkersRank(resultSet.getString(Fields.WORKERS_ID_RANK));
        workers.setBrigade_id(resultSet.getInt(Fields.WORKERS_FLIGHT_NAME));
        return workers;
    }

    /**
     * Extracts a brigade entity from the result set.
     *
     * @param resultSet Result set from which a brigades entity will be extracted.
     * @return Brigade entity.
     * @throws SQLException
     */

    public static Brigades extractBrigades(ResultSet resultSet) throws SQLException {
        Brigades brigades = new Brigades();
        brigades.setId(resultSet.getInt(Fields.ENTITY_ID));
        brigades.setPilot(resultSet.getInt(Fields.BRIGADES_WORKERS_PILOT));
        brigades.setNavigator(resultSet.getInt(Fields.BRIGADES_WORKERS_NAVIGATOR));
        brigades.setOperator(resultSet.getInt(Fields.BRIGADES_WORKERS_OPERATOR));
        brigades.setStewardess(resultSet.getInt(Fields.BRIGADES_WORKERS_STEWARDESS));
        brigades.setFlightNumber(resultSet.getInt(Fields.BRIGADES_WORKERS_FLIGHT_NUMBER));
        return brigades;
    }

    /**
     * Update flight.
     *
     * @param connection connection from DBManager.
     * @param flight     flight to update.
     */

    public static void updateFlightBrigade(Connection connection, Flights flight) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(Const.SQL_UPDATE_FLIGHT_BRIGADE);
            preparedStatement.setInt(1, flight.getBrigade());
            preparedStatement.setInt(2, flight.getNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Cannot update flight brigade ", e);
        } finally {
            close(preparedStatement);
        }
    }

    /**
     * Adding a brigade to a worker.
     *
     * @param connection connection from DBManager.
     * @param worker     worker to update.
     */

    public static void updateWorkerBrigade(Connection connection, Workers worker) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(Const.SQL_UPDATE_BRIGADE_IN_WORKER);
            preparedStatement.setInt(1, worker.getBrigade_id());
            preparedStatement.setInt(2, worker.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Cannot update worker brigade ", e);
        } finally {
            close(preparedStatement);
        }
    }

    /**
     * Update flight status.
     *
     * @param connection connection from DBManager.
     * @param flight     flight to update.
     */

    public static void updateFlightStatus(Connection connection, Flights flight) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(Const.SQL_UPDATE_FLIGHT_STATUS);
            preparedStatement.setString(1, flight.getStatus_id());
            preparedStatement.setInt(2, flight.getNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Cannot update flight status ", e);
        } finally {
            close(preparedStatement);
        }
    }

    /**
     * Update application status.
     *
     * @param connection  connection from DBManager.
     * @param application application to update.
     */

    public static void updateApplicationStatus(Connection connection, Application application) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(Const.SQL_UPDATE_APPLICATION_STATUS);
            preparedStatement.setString(1, application.getStatus_id());
            preparedStatement.setInt(2, application.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Cannot update application status ", e);
        } finally {
            close(preparedStatement);
        }
    }


    /**
     * Update number flight.
     *
     * @param connection connection from DBManager.
     * @param flight     flight to update.
     */

    public static void updateNumberFlight(Connection connection, Flights flight) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(Const.SQL_UPDATE_NUMBER_FLIGHT);
            preparedStatement.setInt(1, flight.getNumber());
            preparedStatement.setInt(2, flight.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Cannot update number flight ", e);
        } finally {
            close(preparedStatement);
        }
    }


    /**
     * Flight date update.
     *
     * @param connection connection from DBManager.
     * @param flight     flight to update.
     */

    public static void updateDateFlight(Connection connection, Flights flight) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(Const.SQL_UPDATE_DATE_FLIGHT);
            preparedStatement.setString(1, flight.getDate());
            preparedStatement.setInt(2, flight.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Cannot update flight date ", e);
        } finally {
            close(preparedStatement);
        }
    }

    /**
     * Update last name worker.
     *
     * @param connection connection from DBManager.
     * @param worker     worker to update.
     */

    public static void updateLastNameWorker(Connection connection, Workers worker) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(Const.SQL_UPDATE_LAST_NUMBER_WORKER);
            preparedStatement.setString(1, worker.getLastName());
            preparedStatement.setInt(2, worker.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Cannot update last name worker ", e);
        } finally {
            close(preparedStatement);
        }
    }

    /**
     * Update rank worker.
     *
     * @param connection connection from DBManager.
     * @param worker     worker to update.
     */

    public static void updateRankWorker(Connection connection, Workers worker) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(Const.SQL_UPDATE_RANK_ID_WORKER);
            preparedStatement.setString(1, worker.getWorkersRank());
            preparedStatement.setInt(2, worker.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Cannot update rank worker ", e);
        } finally {
            close(preparedStatement);
        }
    }

    /**
     * Rollbacks a connection.
     *
     * @param con Connection to be roll backed.
     */

    public static void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                LOG.error("Cannot rollback transaction", ex);
            }
        }
    }

    /**
     * Closes resources.
     *
     * @param con  Connection to be closed.
     * @param stmt Statement to be closed.
     * @param rs   Result set to be closed.
     */

    public static void close(Connection con, Statement stmt, ResultSet rs) {
        close(rs);
        close(stmt);
        close(con);
    }

    /**
     * Closes a connection.
     *
     * @param con Connection to be closed.
     */

    public static void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, ex);
            }
        }
    }

    /**
     * Closes a statement object.
     *
     * @param stmt Statement to be closed.
     */
    public static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_STATEMENT, ex);
            }
        }
    }

    /**
     * Closes a result set object.
     *
     * @param rs Result set to be closed.
     */

    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_RESULT_SET, ex);
            }
        }
    }

    /**
     * Closes resources.
     *
     * @param connection        Connection to be closed.
     * @param preparedStatement Prepared statement to be closed.
     */

    public static void close(Connection connection, PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_CONNECION_AND_STATEMENT, e);
            }
        }
    }
}
