package Persitent.DaoInterfaces;

import Domain.Revenue;

import java.time.LocalDate;

public interface IRevenueDao {
    boolean add(Revenue value);
    boolean update(Revenue info);
    boolean remove( LocalDate date);
    Revenue find(LocalDate date);
}