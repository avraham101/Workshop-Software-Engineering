package Persitent;

import Domain.Discount.Discount;
import Domain.Product;
import Domain.Store;

import javax.persistence.*;
import java.util.List;

public class StoreDao {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("store");

    public void addStore(Store store) {
        // The EntityManager class allows operations such as create, read, update, delete
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        // Used to issue transactions on the EntityManager
        EntityTransaction et = null;

        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();

            // Save the object
            em.persist(store);
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

    public Store find(String storeName){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Store store = null;
        try {
            store=em.find(Store.class,storeName);
            store.initPermissions();
        }
        catch(NoResultException ex) {
            ex.printStackTrace();
        }
        finally {
            em.close();
        }
        return store;
    }

    public void removeStore(String name){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        Store store = null;

        try {
            et = em.getTransaction();
            et.begin();

            store=em.find(Store.class,name);
            em.remove(store);
            et.commit();

        }
        catch(Exception ex) {
            if (et != null) {
                et.rollback();
            }
            ex.printStackTrace();
        }
        finally {
            em.close();
        }
    }

    public void update(Store s) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();
            em.merge(s);
            et.commit();
        } catch (Exception ex) {
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
}
