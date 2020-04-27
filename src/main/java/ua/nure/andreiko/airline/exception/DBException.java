package ua.nure.andreiko.airline.exception;

/**
 * An exception that provides information on a database access error.
 *
 * @author E.Andreiko
 *
 */

public class DBException extends AppException {
    public DBException() {
        super();
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }
}
