package Persitent;

import Domain.Discount.Discount;
import Domain.Store;
import Domain.Subscribe;

import javax.persistence.*;

public class SubscribeDao {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("product");

    public boolean addSubscribe(Subscribe subscribe) {
        boolean output = false;
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
            output = true;
        }
        catch (Exception ex) {
            // If there is an exception rollback changes
            if (et != null) {
                et.rollback();
            }
            ex.printStackTrace();
            output = false;
        } finally {
            // Close EntityManager
            em.close();
        }
        return output;
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

    public void remove(String username){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        Subscribe sub = null;

        try {
            et = em.getTransaction();
            et.begin();
            sub=em.find(Subscribe.class,username);
            em.remove(sub);
            et.commit();

        }
        catch(NoResultException ex) {
            ex.printStackTrace();
        }
        finally {
            em.close();
        }
    }
}
