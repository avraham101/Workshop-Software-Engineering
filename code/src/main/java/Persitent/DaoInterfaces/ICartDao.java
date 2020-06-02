package Persitent.DaoInterfaces;

import Domain.Cart;

public interface ICartDao {
    boolean add(Cart cart);
    void remove(Cart cart);
    boolean update(Cart info);
    Cart find(String name);
    boolean replaceCart(Cart cart);
}
