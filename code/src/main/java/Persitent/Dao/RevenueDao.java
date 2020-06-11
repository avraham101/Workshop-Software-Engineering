package Persitent.Dao;

import Domain.Revenue;
import Persitent.DaoInterfaces.IRevenueDao;
import Utils.Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class RevenueDao extends Dao<Revenue> implements IRevenueDao {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory(Utils.DB);


    public boolean add(Revenue value) {
        return super.add(ENTITY_MANAGER_FACTORY.createEntityManager(), value);
    }


    public boolean update(Revenue info) {
        return super.update(ENTITY_MANAGER_FACTORY.createEntityManager(), info);
    }


    public boolean remove( LocalDate date) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        Revenue r = null;
        boolean output=false;

        try {
            et = em.getTransaction();
            et.begin();

            r=em.find(Revenue.class,date);
            em.remove(r);
            et.commit();
            output=true;

        }
        catch(Exception ex) {
            if (et != null) {
                et.rollback();
            }
        }
        finally {
            em.close();
        }
        return output;
    }


    public Revenue find(LocalDate date) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Revenue revenue=null;
        try {
            revenue=em.find(Revenue.class,date);
        } catch (Exception ex) {
        } finally {
            // Close EntityManager
            em.close();
        }
        return revenue;
    }
}
