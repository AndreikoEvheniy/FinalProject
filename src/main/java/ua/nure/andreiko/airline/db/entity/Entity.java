package ua.nure.andreiko.airline.db.entity;

import java.io.Serializable;

/**
 * Root of all entities which have identifier field.
 *
 * @author E.Andreiko
 */

public abstract class Entity implements Serializable {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
