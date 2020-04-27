package ua.nure.andreiko.airline.exception;

/**
 * An exception that provides information on an application error.
 *
 * @author E.Andreiko
 */

public class AppException extends Exception {
    public AppException() {
        super();
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}
