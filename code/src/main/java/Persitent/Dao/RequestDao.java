package Persitent.Dao;

import Domain.Request;
import Persitent.DaoInterfaces.IRequestDao;
import Utils.Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class RequestDao extends Dao<Request> implements IRequestDao {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory(Utils.DB);

    public boolean addRequest(Request request) {
        // The EntityManager class allows operations such as create, read, update, delete
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        // Used to issue transactions on the EntityManager
        EntityTransaction et = null;
        boolean output = false;

        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();

            // Save the customer object
            em.persist(request);
            et.commit();
            output = true;
        } catch (Exception ex) {
            // If there is an exception rollback changes
            if (et != null) {
                et.rollback();
            }
            ex.printStackTrace();
            output =  false;
        } finally {
            // Close EntityManager
            em.close();
        }
        return output;

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

    public boolean removeRequest(int id){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        Request request = null;
        boolean output = false;

        try {
            et = em.getTransaction();
            et.begin();

            request=em.find(Request.class,id);
            em.remove(request);
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

    public boolean update(Request request){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        return super.update(em,request);
    }
}
