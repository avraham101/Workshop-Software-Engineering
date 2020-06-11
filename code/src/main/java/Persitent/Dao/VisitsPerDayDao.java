package Persitent.Dao;

import Domain.DayVisit;
import Domain.Revenue;
import Persitent.DaoInterfaces.IVisitsPerDayDao;
import Utils.Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class VisitsPerDayDao extends Dao<DayVisit> implements IVisitsPerDayDao {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory(Utils.DB);

    @Override
    public boolean add(DayVisit dayVisit) {
        return super.add(ENTITY_MANAGER_FACTORY.createEntityManager(), dayVisit);
    }

    @Override
    public boolean remove(LocalDate date) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        DayVisit visit = null;
        boolean output=false;

        try {
            et = em.getTransaction();
            et.begin();

            visit=em.find(DayVisit.class,date);
            em.remove(visit);
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

    @Override
    public boolean update(DayVisit dayVisit) {
        return super.update(ENTITY_MANAGER_FACTORY.createEntityManager(), dayVisit);
    }

    @Override
    public DayVisit find(LocalDate date) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        DayVisit visit=null;
        try {
            visit=em.find(DayVisit.class,date);
        } catch (Exception ex) {
        } finally {
            // Close EntityManager
            em.close();
        }
        return visit;
    }
}
