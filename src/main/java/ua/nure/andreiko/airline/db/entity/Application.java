package ua.nure.andreiko.airline.db.entity;

/**
 * Application entity.
 *
 * @author E.Andreiko
 */

public class Application extends Entity {
    private String subject;
    private String message;
    private String status_id;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    @Override
    public String toString() {
        return "Application[" +
                "subject'" + subject + '\'' +
                "message'" + message + '\'' +
                ", status='" + status_id + '\'' +
                ", getId()='" + getId() + '\'' +
                ']';
    }
}
