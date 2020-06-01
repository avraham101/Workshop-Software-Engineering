package Persitent.DaoProxy;

import Domain.Cart;
import Persitent.Dao.CartDao;
import Persitent.DaoInterfaces.ICartDao;

public class CartDaoProxy implements ICartDao {

    private CartDao dao;

    public CartDaoProxy(){
        this.dao = new CartDao();
    }

    @Override
    public boolean add(Cart cart) {
        try{
            return dao.add(cart);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public void remove(Cart cart) {
        try{
            dao.remove(cart);
        }catch (Exception e){

        }

    }

    @Override
    public boolean update(Cart info) {
        try{
            return dao.update(info);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public Cart find(String name) {
        try{
            dao.find(name);
        }catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public boolean replaceCart(Cart cart) {
        try{
            return dao.replaceCart(cart);
        }catch (Exception e) {
            return false;
        }
    }
}
