package ua.nure.andreiko.airline.db.entity;

/**
 * Workers entity.
 *
 * @author E.Andreiko
 */

public class Workers extends Entity {
    private String firstName;
    private String lastName;
    private String workersRank;
    private int brigade_id;

    public int getBrigade_id() {
        return brigade_id;
    }

    public void setBrigade_id(int brigade_id) {
        this.brigade_id = brigade_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getWorkersRank() {
        return workersRank;
    }

    public void setWorkersRank(String workersRank) {
        this.workersRank = workersRank;
    }

    @Override
    public String toString() {
        return "Workers[" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", workersRank=" + workersRank +
                ", brigade_id='" + brigade_id + '\'' +
                ", getId()='" + getId() + '\'' +
                ']';
    }
}
