package dam.accesodatos;

import dam.accesodatos.dao.impl.sqlite.ActorDAO;
import dam.accesodatos.dao.impl.sqlite.FilmDAO;
import dam.accesodatos.dao.impl.sqlite.interfaces.IActorDAO;
import dam.accesodatos.dao.impl.sqlite.interfaces.IFilmDAO;

import java.util.Collection;

public class Main {

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        IActorDAO actorDAO = new ActorDAO();
        IFilmDAO filmDAO = new FilmDAO();

        String letra = "A";
        System.out.println("Actores cuyo nombre empieza por " + letra + " ordenados por nombre (1,5 puntos)");
        System.out.println("==========================================================");
        printList(actorDAO.getActorsWithFirstNameStartingWith(letra));

        System.out.println("\nActores que han salido en más películas (2 puntos)");
        System.out.println("==========================================================");
        printList(actorDAO.getActorsWithMostFilms());

        System.out.println("\nDuración media del préstamo de las películas más cortas (1,5 puntos)");
        System.out.println("==========================================================");
        System.out.println(filmDAO.getRentalDurationAvg());

        System.out.println("\nLista de características especiales (1,5 puntos)");
        System.out.println("==========================================================");
        System.out.println(filmDAO.getDistinctSpecialFeatures());

        String lastname = "KILMER";
        System.out.println("\nExportar a fichero películas de actores con apellidos " + lastname + " (2 puntos)");
        System.out.println("==========================================================");
        filmDAO.exportFilmsForActorsWithLastname(lastname);

    }


    private void printList(Collection<?> list) {
        for (Object o : list) {
            System.out.println(o);
        }
    }

}