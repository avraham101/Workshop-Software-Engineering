package Persitent.DaoInterfaces;

import Domain.PurchasePolicy.PurchasePolicy;

public interface IPolicyDao {
    boolean addPolicy(PurchasePolicy policy);
    boolean removePolicy(int id);
    void updatePolicy(PurchasePolicy policy);
    PurchasePolicy find(int id);

}
