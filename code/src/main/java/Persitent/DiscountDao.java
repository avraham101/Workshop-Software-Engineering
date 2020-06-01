package Persitent;

import Domain.Discount.Discount;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class DiscountDao {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("product");

    public DiscountDao() {
    }

    public boolean addDiscount(Discount discount) {
        // The EntityManager class allows operations such as create, read, update, delete
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        // Used to issue transactions on the EntityManager
        EntityTransaction et = null;

        boolean output=false;
        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();

            // Save the object
            em.persist(discount);
            et.commit();
            output=true;
        }
        catch (Exception ex) {
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

    public boolean removeDiscount(int id){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        boolean output=false;
        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();
            Discount discount=em.find(Discount.class,id);
            em.remove(discount);
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

    public Discount find(int id){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        Discount d=null;

        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();
            d=em.find(Discount.class,id);
        } catch (Exception ex) {
        } finally {
            // Close EntityManager
            em.close();
        }
        return d;
    }
}
