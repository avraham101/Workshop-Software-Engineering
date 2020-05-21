package Persitent;

import Domain.Subscribe;

import javax.persistence.*;

public class Dao<T> {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("product");

    //need to be the same subscribe as the find
    public void update(T info) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();
            em.merge(info);
            et.commit();
        }
        catch (Exception ex) {
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
    }
}
