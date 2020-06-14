package Persitent.DaoInterfaces;

import Domain.DayVisit;

import java.time.LocalDate;

public interface IVisitsPerDayDao {

    boolean add(DayVisit dayVisit);

    boolean remove(LocalDate date);

    boolean update(DayVisit dayVisit);

    DayVisit find(LocalDate date);
}
