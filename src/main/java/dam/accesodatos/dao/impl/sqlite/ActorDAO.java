package dam.accesodatos.dao.impl.sqlite;

import dam.accesodatos.dao.impl.sqlite.interfaces.IActorDAO;
import dam.accesodatos.model.Actor;

import java.util.*;

public class ActorDAO extends AbstractDAO implements IActorDAO {
    @Override
    public List<Actor> getActorsWithFirstNameStartingWith(String letra) {
        String sql = "SELECT * FROM actor WHERE first_name LIKE ?";
        Collection<Actor> temp = conseguirActores(sql, (letra + "%"));

        List<Actor> actores = new ArrayList<>(temp);

        actores.sort(Comparator.comparing(Actor::getFirst_name));

        return actores;
    }

    @Override
    public Set<Actor> getActorsWithMostFilms() {
        String sql = "SELECT * FROM actor WHERE actor_id IN (SELECT actor_id FROM film_actor GROUP BY actor_id HAVING COUNT(actor_id) = (SELECT MAX(num) FROM (SELECT COUNT(actor_id) AS num FROM film_actor GROUP BY actor_id)))";
        Collection<Actor> temp = conseguirActores(sql);

        return Set.copyOf(temp);
    }
}
