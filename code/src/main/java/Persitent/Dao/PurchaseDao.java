package Persitent.Dao;

import DataAPI.Purchase;
import Persitent.DaoInterfaces.IPurchaseDao;
import Persitent.DaoInterfaces.IPurchaseTypeDao;
import Utils.Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class PurchaseDao implements IPurchaseDao {
    private static EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory(Utils.DB);

    public boolean add(Purchase purchase) {
        // The EntityManager class allows operations such as create, read, update, delete
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        // Used to issue transactions on the EntityManager
        EntityTransaction et = null;
        boolean output=false;
        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();

            // Save the customer object
            em.persist(purchase);
            et.commit();
            output=true;
        } catch (Exception ex) {
            // If there is an exception rollback changes
            if (et != null) {
                et.rollback();
            }

        } finally {
            // Close EntityManager
            em.close();
        }
        return output;
    }
}
