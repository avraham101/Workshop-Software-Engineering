package Persitent.Dao;

import Domain.Admin;

import Domain.Subscribe;
import Persitent.DaoInterfaces.ISubscribeDao;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

public class SubscribeDao extends Dao<Subscribe> implements ISubscribeDao {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("subscribe");


    public boolean addSubscribe(Subscribe subscribe) {
        // The EntityManager class allows operations such as create, read, update, delete
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        return super.add(em,subscribe);
    }

    public Subscribe find(String userName){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Subscribe sub = null;
        try {
            sub=em.find(Subscribe.class,userName);
            if(sub!=null)
                sub.initPermissions();
        }
        catch(NoResultException ex) {
        }
        finally {
            em.close();
        }
        return sub;
    }

    public void remove(String username){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        Subscribe sub = null;

        try {
            et = em.getTransaction();
            et.begin();
            sub=em.find(Subscribe.class,username);
            em.remove(sub);
            et.commit();
        }
        catch(Exception ex) {
            if (et != null)
                et.rollback();
        }
        finally {
            em.close();
        }
    }

    public boolean update(Subscribe info) {
        return super.update(ENTITY_MANAGER_FACTORY.createEntityManager(), info);
    }

    public List<Admin> getAllAdmins() {
        List<Admin> output = new LinkedList<>();
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            Query query = em.createNativeQuery("SELECT admin.username FROM admin");
            List resultList = query.getResultList();
            for (Object admin : resultList) {
                if (admin != null)
                    output.add((Admin)find((String)admin));
            }
        }
        catch (Exception e){
        }
        finally {
            em.close();
        }
        return output;

    }

    public List<String> getAllUserName() {
        List<String> output = new LinkedList<>();
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            Query query = em.createNativeQuery("SELECT Subscribe.username FROM Subscribe");
            output = query.getResultList();
        }
        catch (Exception e){
        }
        finally {
            em.close();
        }
        return output;
    }
}
