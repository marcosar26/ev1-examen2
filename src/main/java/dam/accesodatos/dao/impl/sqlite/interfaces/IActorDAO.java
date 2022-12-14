package dam.accesodatos.dao.impl.sqlite.interfaces;

import dam.accesodatos.model.Actor;

import java.util.Collection;

public interface IActorDAO {
    public Collection<Actor> getActorsWithFirstNameStartingWith(String letra);

    public Collection<Actor> getActorsWithMostFilms();
}
