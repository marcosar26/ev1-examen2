package dam.accesodatos.dao.impl.sqlite;

import dam.accesodatos.bd.DatabaseManager;
import dam.accesodatos.dao.interfaces.IActorDAO;
import dam.accesodatos.model.Actor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ActorDAO implements IActorDAO {
    @Override
    public Collection<Actor> getActorsWithFirstNameStartingWith(String letra) {
        String sql = "SELECT * FROM actor WHERE first_name LIKE ? ORDER BY first_name";

        return conseguirActores(sql, (letra + "%"));
    }

    @Override
    public Collection<Actor> getActorsWithMostFilms() {
        String sql = "SELECT * FROM actor WHERE actor_id IN (SELECT actor_id FROM film_actor GROUP BY actor_id HAVING COUNT(actor_id) = (SELECT MAX(num) FROM (SELECT COUNT(actor_id) AS num FROM film_actor GROUP BY actor_id)))";

        return conseguirActores(sql);
    }

    private Collection<Actor> conseguirActores(String sql, Object... objects) {
        try (PreparedStatement statement = DatabaseManager.getInstance().getCon().prepareStatement(sql)) {

            for (int i = 0; i < objects.length; i++) {
                statement.setObject((i + 1), objects[i]);
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
