package Persitent.DaoInterfaces;

import Domain.ProductInCart;

public interface IProductInCartDao {
    boolean add(ProductInCart value);
    boolean update(ProductInCart info);
    boolean remove(ProductInCart value);
    ProductInCart find( ProductInCart value);
    boolean remove(String product);

}
