package Persitent.DaoProxy;

import DataAPI.Purchase;
import Persitent.Dao.PurchaseDao;
import Persitent.DaoInterfaces.IPurchaseDao;

public class PurchaseDaoProxy implements IPurchaseDao {

    private PurchaseDao dao;

    public PurchaseDaoProxy(){
        this.dao = new PurchaseDao();
    }

    @Override
    public boolean add(Purchase purchase) {
        try{
            return dao.add(purchase);
        }catch (Exception e) {
            return false;
        }
    }
}
