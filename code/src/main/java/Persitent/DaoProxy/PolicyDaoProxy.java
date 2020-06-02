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
    public boolean addPolicy(PurchasePolicy policy) {
        try {
            return dao.addPolicy(policy);
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean removePolicy(int id) {
        try {
            return dao.removePolicy(id);
        }catch (Exception e){
            return false;
        }
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
