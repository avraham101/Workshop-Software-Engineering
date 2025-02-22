package Persitent.Dao;


import Domain.Store;
import Persitent.DaoInterfaces.IStoreDao;
import Utils.Utils;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

public class StoreDao extends Dao<Store> implements IStoreDao {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory(Utils.DB);

    public boolean addStore(Store store) {
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
        catch(Exception ex) {
            ex.printStackTrace();
        }
        finally {
            em.close();
        }
        return store;
    }

    public List<Store> getAll() {
        List<Store> output = new LinkedList<>();
        List<String> storeNames = new LinkedList<>();
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Query query = em.createQuery("SELECT name FROM Domain.Store");
        List<String> resultList = query.getResultList();
        for(String name:resultList) {
            if(name!=null)
                output.add(find(name));
        }
        return output;
    }

    public boolean removeStore(String name){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        Store store = null;
        boolean output = false;

        try {
            et = em.getTransaction();
            et.begin();

            store=em.find(Store.class,name);
            em.remove(em.contains(store) ? store : em.merge(store));
            et.commit();
            output = true;

        }
        catch(Exception ex) {
            if (et != null) {
                et.rollback();
            }
            output = false;
        }
        finally {
            em.close();
        }
        return output;
    }

    public void update(Store store) {
        super.update(ENTITY_MANAGER_FACTORY.createEntityManager(),store);
    }

}
