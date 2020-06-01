package Persitent.DaoInterfaces;

import Domain.PurchasePolicy.PurchasePolicy;

public interface IPolicyDao {
    void addPolicy(PurchasePolicy policy);
    void removePolicy(int id);
    void updatePolicy(PurchasePolicy policy);
    PurchasePolicy find(int id);

}
