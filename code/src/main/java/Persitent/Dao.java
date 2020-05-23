package Persitent;

import Domain.Subscribe;

import javax.persistence.*;

public class Dao<T> {
//
//    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
//            .createEntityManagerFactory("product");

    public boolean add(EntityManager em, T value) {
        boolean output = false;
        // Used to issue transactions on the EntityManager
        EntityTransaction et = null;
        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();
            // Save the object
            em.persist(value);
            et.commit();
            output = true;
        }
        catch (Exception ex) {
            output = false;
            // If there is an exception rollback changes
            if (et != null) {
                et.rollback();
            }
            ex.printStackTrace();
        } finally {
            // Close EntityManager
            em.close();
        }
        return output;
    }

    //need to be the same subscribe as the find
    public boolean update(EntityManager em,T info) {
        boolean output = false;
        EntityTransaction et = null;
        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();
            em.merge(info);
            et.commit();
            output = true;
        }
        catch (Exception ex) {
            output = false;
            // If there is an exception rollback changes
            if (et != null) {
                et.rollback();
            }
            ex.printStackTrace();
        }
        finally {
            // Close EntityManager
            em.close();
        }
        return output;
    }

}
