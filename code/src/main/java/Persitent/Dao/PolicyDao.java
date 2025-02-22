package Persitent.Dao;

import Domain.PurchasePolicy.PurchasePolicy;
import Persitent.DaoInterfaces.IPolicyDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import Utils.Utils;

public class PolicyDao implements IPolicyDao {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory(Utils.DB);


    public boolean addPolicy(PurchasePolicy policy){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        boolean output=false;
        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();

            // Save the object
            em.persist(policy);
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

    public boolean removePolicy(int id){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        boolean output=false;

        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();
            PurchasePolicy policy=em.find(PurchasePolicy.class,id);
            em.remove(policy);
            et.commit();
            output = true;
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
    public PurchasePolicy find(int id){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        PurchasePolicy policy=null;
        try {
            policy=em.find(PurchasePolicy.class,id);
        } catch (Exception ex) {

        } finally {
            // Close EntityManager
            em.close();
        }
        return policy;
    }



}
