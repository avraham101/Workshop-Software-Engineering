package Persitent.DaoProxy;

import Domain.PurchaseType;
import Persitent.Dao.PurchaseTypeDao;
import Persitent.DaoInterfaces.IPurchaseTypeDao;

public class PurchaseTypeDaoProxy implements IPurchaseTypeDao {

    private PurchaseTypeDao dao;

    public PurchaseTypeDaoProxy(){
        this.dao = new PurchaseTypeDao();
    }

    @Override
    public PurchaseType find(PurchaseType type) {
        try {
            return dao.find(type);
        }catch (Exception e) {
            return null;
        }
    }
}
