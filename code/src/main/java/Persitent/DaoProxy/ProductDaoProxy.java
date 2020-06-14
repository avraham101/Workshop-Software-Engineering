package Persitent.DaoProxy;

import Domain.Product;
import Persitent.Dao.ProductDao;
import Persitent.DaoInterfaces.ICategoryDao;
import Persitent.DaoInterfaces.IProductDao;

public class ProductDaoProxy implements IProductDao {

    private ProductDao dao;

    public ProductDaoProxy(ICategoryDao categoryDao){

        dao = new ProductDao(categoryDao);
    }

    @Override
    public boolean addProduct(Product product) {
        try{
            return dao.addProduct(product);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateProduct(Product product) {
        try{
            return dao.updateProduct(product);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean removeProduct(Product product) {
        try{
            return dao.removeProduct(product);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public Product find(Product keys) {
        try{
            return dao.find(keys);
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean removeProduct(String productName, String storeName) {
        try{
            return dao.removeProduct(productName, storeName);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public void openTransaction() {
        try {
            dao.openTransaction();
        }
        catch (Exception ignored){
        }
    }

    @Override
    public void closeTransaction() {
        try {
            dao.closeTransaction();
        }
        catch (Exception ignored){
        }
    }
}
