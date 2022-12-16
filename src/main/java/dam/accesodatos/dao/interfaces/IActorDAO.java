package dam.accesodatos.dao.interfaces;

import dam.accesodatos.model.Actor;

import java.util.Collection;

public interface IActorDAO {
    Collection<Actor> getActorsWithFirstNameStartingWith(String letra);

    Collection<Actor> getActorsWithMostFilms();
}
