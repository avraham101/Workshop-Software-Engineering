package Persitent;

import Domain.PurchaseType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

public class PurchaseTypeDao {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("product");

    public PurchaseType find(PurchaseType type){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        PurchaseType purchaseType = null;
        try {
            purchaseType=em.find(PurchaseType.class,type.getPurchaseTypeData());
        }
        catch(NoResultException ex) {
            ex.printStackTrace();
        }
        finally {
            em.close();
        }
        return purchaseType;
    }
}
