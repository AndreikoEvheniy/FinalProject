package ua.nure.andreiko.airline.db;

/**
 * Holder for fields names of DB tables and beans.
 *
 * @author E.Andreiko
 */

public class Fields {

    public static final String ENTITY_ID = "id";

    public static final String USER_LOGIN = "login";
    public static final String USER_PASSWORD = "password";
    public static final String USER_ROLES = "role_id";

    public static final String FLIGHTS_NUMBER = "number";
    public static final String FLIGHTS_IS_FROM = "isFrom";
    public static final String FLIGHTS_WHERE_TO = "whereTo";
    public static final String FLIGHTS_DATE = "date";
    public static final String FLIGHTS_NAME = "name";
    public static final String FLIGHTS_BRIGADE = "brigade";
    public static final String FLIGHTS_STATUS = "status";

    public static final String APPLICATION_SUBJECT = "subject";
    public static final String APPLICATION_MESSAGE = "message";
    public static final String APPLICATION_STATUS = "status";


    public static final String BRIGADES_WORKERS_PILOT = "pilot";
    public static final String BRIGADES_WORKERS_NAVIGATOR = "navigator";
    public static final String BRIGADES_WORKERS_OPERATOR = "operator";
    public static final String BRIGADES_WORKERS_STEWARDESS = "stewardess";
    public static final String BRIGADES_WORKERS_FLIGHT_NUMBER = "flight_number";

    public static final String WORKERS_FIRST_NAME = "firstName";
    public static final String WORKERS_LAST_NAME = "lastName";
    public static final String WORKERS_RANK = "name";
    public static final String WORKERS_FLIGHT_NAME = "brigade_id";
    public static final String WORKERS_ID_RANK = "rank_id";
}
