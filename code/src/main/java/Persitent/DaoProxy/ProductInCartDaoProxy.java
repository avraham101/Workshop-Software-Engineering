package Persitent.DaoProxy;

import Domain.ProductInCart;
import Persitent.Dao.ProductInCartDao;
import Persitent.DaoInterfaces.IProductInCartDao;

public class ProductInCartDaoProxy implements IProductInCartDao {

    private ProductInCartDao dao;

    public ProductInCartDaoProxy(){
        this.dao = new ProductInCartDao();
    }

    @Override
    public boolean add(ProductInCart value) {
        try{
            return dao.add(value);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(ProductInCart info) {
        try{
            return dao.update(info);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean remove(ProductInCart value) {
        try{
            return dao.remove(value);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public ProductInCart find(ProductInCart value) {
        try{
            return dao.find(value);
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean remove(String product) {
        try{
            return dao.remove(product);
        }catch (Exception e) {
            return false;
        }
    }
}
