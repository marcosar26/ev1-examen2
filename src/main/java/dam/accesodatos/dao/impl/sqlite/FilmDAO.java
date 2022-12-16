package dam.accesodatos.dao.impl.sqlite;

import dam.accesodatos.bd.DatabaseManager;
import dam.accesodatos.dao.impl.sqlite.interfaces.IFilmDAO;
import dam.accesodatos.model.Actor;
import dam.accesodatos.model.Film;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class FilmDAO implements IFilmDAO {

    @Override
    public double getRentalDurationAvg() {
        String sql = "SELECT AVG(rental_duration) FROM film WHERE length = (SELECT MIN(length) FROM film)";

        try (Statement statement = DatabaseManager.getInstance().getCon().createStatement()) {

            try (ResultSet set = statement.executeQuery(sql)) {
                return set.getDouble(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<String> getDistinctSpecialFeatures() {
        String sql = "SELECT DISTINCT special_features FROM film";
        Set<String> featuresSet = new TreeSet<>();

        try (Statement statement = DatabaseManager.getInstance().getCon().createStatement()) {

            try (ResultSet set = statement.executeQuery(sql)) {
                while (set.next()) {
                    String feature = set.getString(1);
                    if (feature.contains(",")) {
                        String[] features = feature.split(",");
                        for (String f : features) {
                            featuresSet.add(f.trim());
                        }
                    } else {
                        featuresSet.add(feature);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return featuresSet;
    }

    @Override
    public void exportFilmsForActorsWithLastname(String lastname) {
        String sql = "SELECT * FROM film INNER JOIN film_actor ON film.film_id = film_actor.film_id INNER JOIN actor ON film_actor.actor_id = actor.actor_id WHERE actor.last_name = ?";

        Map<Actor, Set<Film>> map = new TreeMap<>(Comparator.comparing(Actor::getId));

        try (PreparedStatement statement = DatabaseManager.getInstance().getCon().prepareStatement(sql)) {
            statement.setString(1, lastname);

            try (ResultSet set = statement.executeQuery()) {
                while (set.next()) {
                    int id = set.getInt("film_id");
                    String title = set.getString("title");
                    String description = set.getString("description");
                    int releaseYear = set.getInt("release_year");
                    int languageId = set.getInt("language_id");
                    int originalLanguageId = set.getInt("original_language_id");
                    int rentalDuration = set.getInt("rental_duration");
                    double rentalRate = set.getDouble("rental_rate");
                    int length = set.getInt("length");
                    double replacementCost = set.getDouble("replacement_cost");
                    String rating = set.getString("rating");
                    String specialFeatures = set.getString("special_features");
                    Timestamp lastUpdate = set.getTimestamp("last_update");

                    int actor_id = set.getInt("actor_id");
                    String first_name = set.getString("first_name");
                    String last_name = set.getString("last_name");
                    Timestamp last_update = set.getTimestamp("last_update");

                    Film film = new Film(id, title, description, releaseYear, languageId, originalLanguageId, rentalDuration, rentalRate, length, replacementCost, rating, specialFeatures, lastUpdate);
                    Actor actor = new Actor(actor_id, first_name, last_name, last_update);

                    Set<Film> films = map.getOrDefault(actor, new HashSet<>());
                    films.add(film);
                    map.put(actor, films);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Se van a exportar " + map.size() + " archivos");

        File dir = new File("films-csv");
        if (!dir.exists()) dir.mkdir();

        for (Map.Entry<Actor, Set<Film>> actorListEntry : map.entrySet()) {
            int id = actorListEntry.getKey().getId();
            String nombre = actorListEntry.getKey().getFirst_name();
            String apellido = actorListEntry.getKey().getLast_name();

            Set<Film> pelis = actorListEntry.getValue();

            System.out.println("El actor con id " + id + " tiene " + pelis.size() + " películas");

            String nombreFichero = id + "-" + nombre.toUpperCase() + "-" + apellido.toUpperCase() + ".csv";

            File file = new File(dir, nombreFichero);

            try {
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                for (Film peli : pelis) {
                    bw.append(peli.writer());
                    bw.newLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
