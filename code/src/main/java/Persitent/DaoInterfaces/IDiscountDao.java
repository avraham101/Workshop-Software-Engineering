package Persitent.DaoInterfaces;

import Domain.Discount.Discount;

public interface IDiscountDao {
    boolean addDiscount(Discount discount);
    boolean removeDiscount(int id);
    Discount find(int id);
}
