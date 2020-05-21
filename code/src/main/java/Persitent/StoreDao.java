package Persitent;

import Domain.Discount.Discount;
import Domain.Product;
import Domain.Store;

import javax.persistence.*;
import java.util.List;

public class StoreDao extends Dao<Store>{
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("store");

    public boolean addStore(Store store) {
        boolean output = false;
        // The EntityManager class allows operations such as create, read, update, delete
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        return super.add(em, store);
    }

    public Store find(String storeName){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Store store = null;
        try {
            store=em.find(Store.class,storeName);
            if(store!=null) {
                store.initPermissions();
            }
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

    public void clearTable() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("DELETE FROM Domain.Store");
        int rowsDeleted = query.executeUpdate();
        //System.out.println("entities deleted: " + rowsDeleted);
        em.getTransaction().commit();
        em.close();
    }
}
