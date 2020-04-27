package ua.nure.andreiko.airline.db.entity;

/**
 * Brigades entity.
 *
 * @author E.Andreiko
 */
public class Brigades extends Entity {
    private int pilot;
    private int navigator;
    private int operator;
    private int stewardess;
    private int flightNumber;

    public int getPilot() {
        return pilot;
    }

    public void setPilot(int pilot) {
        this.pilot = pilot;
    }

    public int getNavigator() {
        return navigator;
    }

    public void setNavigator(int navigator) {
        this.navigator = navigator;
    }

    public int getOperator() {
        return operator;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }

    public int getStewardess() {
        return stewardess;
    }

    public void setStewardess(int stewardess) {
        this.stewardess = stewardess;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    @Override
    public String toString() {
        return "Brigades[" +
                "pilot='" + pilot + '\'' +
                ", navigator=" + navigator + '\'' +
                ", operator=" + operator + '\'' +
                ", stewardess=" + stewardess + '\'' +
                ", flightNumber=" + flightNumber + '\'' +
                ", getId()='" + getId() + '\'' +
                ']';
    }
}
