package dam.accesodatos.dao.interfaces;

import java.util.Collection;

public interface IFilmDAO {
    double getRentalDurationAvg();

    Collection<?> getDistinctSpecialFeatures();

    void exportFilmsForActorsWithLastname(String lastname);
}
