package Persitent;

import Domain.Discount.Discount;
import Domain.Request;
import Domain.Store;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class RequestDao extends Dao<Request> {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("request");

    public boolean addRequest(Request request) {
        // The EntityManager class allows operations such as create, read, update, delete
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        // Used to issue transactions on the EntityManager
        EntityTransaction et = null;

        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();

            // Save the customer object
            em.persist(request);
            et.commit();
        } catch (Exception ex) {
            // If there is an exception rollback changes
            if (et != null) {
                et.rollback();
            }
            ex.printStackTrace();
            return false;
        } finally {
            // Close EntityManager
            em.close();
        }
        return true;

    }

    public Request find(int id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        Request r=null;

        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();
            r=em.find(Request.class,id);
        } catch (Exception ex) {
        } finally {
            // Close EntityManager
            em.close();
        }
        return r;
    }

    public void removeRequest(int id){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        Request request = null;

        try {
            et = em.getTransaction();
            et.begin();

            request=em.find(Request.class,id);
            em.remove(request);
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

    public boolean update(Request request){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        return super.update(em,request);

    }
}
