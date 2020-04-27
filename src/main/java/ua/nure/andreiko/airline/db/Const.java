package ua.nure.andreiko.airline.db;

/**
 * SQL queries.
 *
 * @author E.Andreiko
 */

public class Const {
    private Const() {
    }

    public static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";

    public static final String SQL_FIND_FLIGHTS = "SELECT flights.id, flights.number, flights.isFrom, flights.whereTo, flights.date," +
            "flights.name, flights.brigade, flight_statuses.status FROM flights, flight_statuses WHERE flight_statuses.id = flights.status_id";
    public static final String SQL_FIND_FLIGHTS_BY_NUMBER = "SELECT flights.id, flights.number, flights.isFrom, flights.whereTo, flights.date," +
            "flights.name, flights.brigade, flight_statuses.status FROM flights " +
            "INNER JOIN flight_statuses ON flight_statuses.id = flights.status_id WHERE flights.number=?";
    public static final String SQL_FIND_FLIGHTS_BY_ID = "SELECT flights.id, flights.number, flights.isFrom, flights.whereTo, flights.date," +
            "flights.name, flights.brigade, flight_statuses.status FROM flights " +
            "INNER JOIN flight_statuses ON flight_statuses.id = flights.status_id WHERE flights.id=?";
    public static final String SQL_FIND_FLIGHTS_FROM = "SELECT flights.id, flights.number, flights.isFrom, flights.whereTo, flights.date," +
            "flights.name, flights.brigade, flight_statuses.status FROM flights " +
            "INNER JOIN flight_statuses ON flight_statuses.id = flights.status_id WHERE flights.isFrom=?";
    public static final String SQL_UPDATE_FLIGHT_BRIGADE = "UPDATE flights SET brigade=? WHERE number=?";
    public static final String SQL_UPDATE_FLIGHT_STATUS = "UPDATE flights SET status_id=? WHERE number=?";
    public static final String SQL_CREATE_FLIGHT = "INSERT INTO flights VALUES (DEFAULT, ?, ?, ?, ?, ?, NULL, 1)";
    public static final String SQL_REMOVE_FLIGHT = "DELETE FROM flights WHERE id=?";
    public static final String SQL_UPDATE_NUMBER_FLIGHT = "UPDATE flights SET number=? WHERE id=?";
    public static final String SQL_UPDATE_DATE_FLIGHT = "UPDATE flights SET date=? WHERE id=?";

    public static final String SQL_GET_WORKERS = "SELECT workers.id, workers.firstName, workers.lastName, workers.rank_id, workers_rank.name," +
            "workers.brigade_id FROM workers, workers_rank  WHERE workers_rank.id = workers.rank_id";
    public static final String SQL_GET_WORKER = "SELECT * FROM workers WHERE id=?";
    public static final String SQL_GET_PILOTS = "SELECT * FROM workers WHERE rank_id=1";
    public static final String SQL_GET_NAVIGATOR = "SELECT * FROM workers WHERE rank_id=2";
    public static final String SQL_GET_OPERATOR = "SELECT * FROM workers WHERE rank_id=3";
    public static final String SQL_GET_STEWARDESS = "SELECT * FROM workers WHERE rank_id=4";
    public static final String SQL_UPDATE_BRIGADE_IN_WORKER = "UPDATE workers SET brigade_id=? WHERE id=?";
    public static final String SQL_CREATE_WORKER = "INSERT INTO workers VALUES (DEFAULT, ?, ?, ?, NULL)";
    public static final String SQL_REMOVE_WORKER = "DELETE FROM workers WHERE id=?";
    public static final String SQL_UPDATE_LAST_NUMBER_WORKER = "UPDATE workers SET lastName=? WHERE id=?";
    public static final String SQL_UPDATE_RANK_ID_WORKER = "UPDATE workers SET rank_id=? WHERE id=?";

    public static final String SQL_CREATE_BRIGADE =
            "INSERT INTO brigades VALUES (DEFAULT, ?,?,?,?,?)";
    public static final String SQL_GET_BRIGADE = "SELECT * FROM brigades WHERE flight_number=?";

    public static final String SQL_CREATE_APPLICATION = "INSERT INTO applications VALUES (DEFAULT, ?, ?, 1)";
    public static final String SQL_FIND_APPLICATION = "SELECT applications.id, applications.subject, applications.message," +
            " application_statuses.status FROM applications, application_statuses WHERE application_statuses.id = applications.status_id";
    public static final String SQL_GET_APPLICATION_BY_ID = "SELECT applications.id, applications.subject, applications.message," +
            " application_statuses.status FROM applications " +
            "INNER JOIN application_statuses ON application_statuses.id = applications.status_id WHERE applications.id=?";
    public static final String SQL_UPDATE_APPLICATION_STATUS = "UPDATE applications SET status_id=? WHERE id=?";
}