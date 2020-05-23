package Persitent;

import Domain.Discount.Discount;
import Domain.Store;
import Domain.Subscribe;

import javax.persistence.*;

public class SubscribeDao extends Dao<Subscribe>{

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("product");

    public boolean addSubscribe(Subscribe subscribe) {
        boolean output = false;
        // The EntityManager class allows operations such as create, read, update, delete
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        return super.add(em,subscribe);
    }

    public Subscribe find(String userName){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Subscribe sub = null;
        try {
            sub=em.find(Subscribe.class,userName);
            if(sub!=null)
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
        catch(Exception ex) {
        }
        finally {
            em.close();
        }
    }


    public boolean update(Subscribe info) {
        return super.update(ENTITY_MANAGER_FACTORY.createEntityManager(), info);
    }

    public void clearTable() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("DELETE FROM Domain.Subscribe");
        int rowsDeleted = query.executeUpdate();
        //System.out.println("entities deleted: " + rowsDeleted);
        em.getTransaction().commit();
        em.close();
    }
}
