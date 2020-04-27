package ua.nure.andreiko.airline.db.entity;

/**
 * Flights entity.
 *
 * @author E.Andreiko
 */

public class Flights extends Entity {
    private int number;
    private String isFrom;
    private String whereTo;
    private String date;
    private String name;
    private int brigade;
    private String status_id;

    public Flights(String name) {
        this.name = name;
    }

    public Flights() {
    }

    public int getBrigade() {
        return brigade;
    }

    public void setBrigade(int brigade) {
        this.brigade = brigade;
    }

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getIsFrom() {
        return isFrom;
    }

    public void setIsFrom(String isFrom) {
        this.isFrom = isFrom;
    }

    public String getWhereTo() {
        return whereTo;
    }

    public void setWhereTo(String whereTo) {
        this.whereTo = whereTo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Flights[" +
                "number'" + number + '\'' +
                "isFrom='" + isFrom + '\'' +
                ", whereTo='" + whereTo + '\'' +
                ", date=" + date +
                ", name='" + name + '\'' +
                ", brigade='" + brigade + '\'' +
                ", status='" + status_id + '\'' +
                ", getId()='" + getId() + '\'' +
                ']';
    }
}
