package dam.accesodatos.model;

import java.sql.Timestamp;
import java.util.Objects;

public class Actor {
    private int id;
    private String first_name;
    private String last_name;
    private Timestamp last_update;

    public Actor(int id, String first_name, String last_name, Timestamp last_update) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.last_update = last_update;
    }

    @Override
    public String toString() {
        return "Actor{" + "id=" + id + ", first_name='" + first_name + '\'' + ", last_name='" + last_name + '\'' + ", last_update=" + last_update + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Actor actor)) return false;
        return id == actor.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Timestamp getLast_update() {
        return last_update;
    }

    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }
}
