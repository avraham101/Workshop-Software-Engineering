package Persitent.DaoInterfaces;

import Domain.Product;

public interface IProductDao {
    boolean addProduct(Product product);
    boolean updateProduct(Product product);
    boolean removeProduct(Product product);
    Product find(Product keys);
    boolean removeProduct(String productName, String storeName);

    void openTransaction();

    void closeTransaction();
}
