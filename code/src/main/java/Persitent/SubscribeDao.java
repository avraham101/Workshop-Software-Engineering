package Persitent;

import Domain.Discount.Discount;
import Domain.Store;
import Domain.Subscribe;

import javax.persistence.*;

public class SubscribeDao {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("product");

    public void addSubscribe(Subscribe subscribe) {
        // The EntityManager class allows operations such as create, read, update, delete
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        // Used to issue transactions on the EntityManager
        EntityTransaction et = null;

        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();

            // Save the object
            em.persist(subscribe);
            et.commit();
        }
        catch (Exception ex) {
            // If there is an exception rollback changes
            if (et != null) {
                et.rollback();
            }
            ex.printStackTrace();
        } finally {
            // Close EntityManager
            em.close();
        }
    }

    public Subscribe find(String userName){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Subscribe sub = null;
        try {
            sub=em.find(Subscribe.class,userName);
            sub.initPermissions();
        }
        catch(NoResultException ex) {
            ex.printStackTrace();
        }
        finally {
            em.close();
        }
        return sub;
    }
}
