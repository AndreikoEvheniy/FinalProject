package ua.nure.andreiko.airline.db;

import ua.nure.andreiko.airline.db.entity.User;

/**
 * User roles.
 *
 * @author E.Andreiko
 */

public enum Role {
    ADMIN, DISPATCHER, USER;

    public static Role getRole(User user) {
        int roleId = user.getRoleId();
        return Role.values()[roleId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
