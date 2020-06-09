package Persitent.Dao;

import Domain.OwnerAgreement;
import Persitent.DaoInterfaces.IOwnerAgreementDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import Utils.*;

public class OwnerAgreementDao extends Dao<OwnerAgreement> implements IOwnerAgreementDao {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory(Utils.DB);



    public boolean add(OwnerAgreement value) {
        return super.add(ENTITY_MANAGER_FACTORY.createEntityManager(), value);
    }

    public boolean update(OwnerAgreement info) {
        return super.update(ENTITY_MANAGER_FACTORY.createEntityManager(), info);
    }

    public boolean remove( int id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        boolean output=false;

        try {
            et = em.getTransaction();
            et.begin();

            OwnerAgreement o=em.find(OwnerAgreement.class,id);
            em.remove(o);
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


    public OwnerAgreement find(int id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        OwnerAgreement ownerAgreement=null;
        try {
            ownerAgreement=em.find(OwnerAgreement.class,id);
        } catch (Exception ex) {
        } finally {
            // Close EntityManager
            em.close();
        }
        return ownerAgreement;
    }
}
