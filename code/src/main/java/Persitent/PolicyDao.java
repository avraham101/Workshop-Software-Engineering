package Persitent;

import DataAPI.Purchase;
import Domain.Discount.Discount;
import Domain.PurchasePolicy.PurchasePolicy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.security.Policy;

public class PolicyDao {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("product");

    public PolicyDao() {
    }

    public void addPolicy(PurchasePolicy policy){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();

            // Save the object
            em.persist(policy);
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

    public void removePolicy(int id){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();
            PurchasePolicy policy=em.find(PurchasePolicy.class,id);
            em.remove(policy);
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

    public void updatePolicy(PurchasePolicy policy){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();
            em.merge(policy);
            et.commit();
        } catch (Exception ex) {
            // If there is an exception rollback changes
            if (et != null) {
                et.rollback();
            }
//            ex.printStackTrace();
        } finally {
            // Close EntityManager
            em.close();
        }


    }

    public Policy find(int id){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        Policy policy=null;

        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();
            policy=em.find(Policy.class,id);
        } catch (Exception ex) {
        } finally {
            // Close EntityManager
            em.close();
        }
        return policy;
    }



}
