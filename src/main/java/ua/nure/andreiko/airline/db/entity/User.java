package ua.nure.andreiko.airline.db.entity;

/**
 * User entity.
 *
 * @author E.Andreiko
 */

public class User extends Entity {
    private String login;
    private String password;
    private int roleId;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "User[" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", roleId=" + roleId +
                ']';
    }
}
