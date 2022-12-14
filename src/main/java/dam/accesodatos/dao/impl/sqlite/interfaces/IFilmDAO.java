package dam.accesodatos.dao.impl.sqlite.interfaces;

import java.util.Collection;

public interface IFilmDAO {
    public double getRentalDurationAvg();

    public Collection<?> getDistinctSpecialFeatures();

    public void exportFilmsForActorsWithLastname(String lastname);
}
