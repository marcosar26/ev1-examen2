package dam.accesodatos.dao.impl.sqlite;

import dam.accesodatos.bd.DatabaseManager;
import dam.accesodatos.model.Actor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractDAO {
    public Collection<Actor> conseguirActores(String sql, Object... objects) {
        try (PreparedStatement statement = DatabaseManager.getInstance().getCon().prepareStatement(sql)) {

            if (objects.length > 0) {
                for (int i = 0; i < objects.length; i++) {
                    statement.setObject((i + 1), objects[i]);
                }
            }

            Set<Actor> actores = new HashSet<>();

            try (ResultSet set = statement.executeQuery()) {
                while (set.next()) {
                    int id = set.getInt("actor_id");
                    String first_name = set.getString("first_name");
                    String last_name = set.getString("last_name");
                    Timestamp timestamp = set.getTimestamp("last_update");

                    Actor actor = new Actor(id, first_name, last_name, timestamp);
                    actores.add(actor);
                }
            }

            return actores;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
