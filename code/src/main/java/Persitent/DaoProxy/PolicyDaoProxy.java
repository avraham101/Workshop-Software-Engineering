package Persitent.DaoProxy;

import Domain.PurchasePolicy.PurchasePolicy;
import Persitent.Dao.PolicyDao;
import Persitent.DaoInterfaces.IPolicyDao;

public class PolicyDaoProxy implements IPolicyDao {

    private PolicyDao dao;

    public PolicyDaoProxy(){
        this.dao = new PolicyDao();
    }

    @Override
    public void addPolicy(PurchasePolicy policy) {
        dao.addPolicy(policy);
    }

    @Override
    public void removePolicy(int id) {
        dao.removePolicy(id);
    }

    @Override
    public void updatePolicy(PurchasePolicy policy) {
        dao.updatePolicy(policy);
    }

    @Override
    public PurchasePolicy find(int id) {
        try{
            return dao.find(id);
        }catch (Exception e) {
            return null;
        }
    }
}
