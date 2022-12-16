package dam.accesodatos.dao.impl.sqlite.interfaces;

import java.util.Collection;

public interface IFilmDAO {
    double getRentalDurationAvg();

    Collection<?> getDistinctSpecialFeatures();

    void exportFilmsForActorsWithLastname(String lastname);
}
