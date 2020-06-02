package Persitent.DaoProxy;

import Domain.Discount.Discount;
import Persitent.Dao.DiscountDao;
import Persitent.DaoInterfaces.IDiscountDao;

public class DiscountDaoProxy implements IDiscountDao {

    private DiscountDao dao;

    public DiscountDaoProxy(){
        dao = new DiscountDao();
    }

    @Override
    public boolean addDiscount(Discount discount) {
        try{
            return dao.addDiscount(discount);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean removeDiscount(int id) {
        try{
            return dao.removeDiscount(id);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public Discount find(int id) {
        try {
            return dao.find(id);
        }catch (Exception e) {
            return null;
        }
    }
}
