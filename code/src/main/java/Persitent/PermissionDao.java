package Persitent;

import DataAPI.PermissionType;
import Domain.Permission;
import Domain.Product;
import Domain.Store;
import Domain.Subscribe;
import com.mysql.cj.Session;

import javax.persistence.*;
import javax.transaction.Transaction;
import javax.transaction.Transactional;

public class PermissionDao extends Dao<Permission> {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("product");

    public boolean addPermission(Permission permission){
        boolean output = false;
        // The EntityManager class allows operations such as create, read, update, delete
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        return super.add(em,permission);
    }

    public boolean removePermissionFromSubscribe(Permission perToDelete){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();
            Permission permission = em.find(Permission.class,perToDelete);
            em.remove(permission);
            //em.remove(em.contains(permission) ? permission : em.merge(permission));
            et.commit();
        }
        catch(Exception ex) {
            if (et != null) {
                et.rollback();
            }
            return false;
        }
        finally {
            em.close();
        }
        return true;
    }

    public  Permission findPermission(Permission perToFind){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Permission permission = null;
        try {
            permission=em.find(Permission.class,perToFind);
        }
        catch(NoResultException ex) {
            ex.printStackTrace();
        }
        finally {
            em.close();
        }
        return permission;
    }

    //@Transactional
    public boolean addPermissionType(String storeName, String owner, PermissionType type){
        //Session session = HibernateUtil.getSessionFactory().openSession();
        //Transaction transaction = session.beginTransaction();
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
       EntityTransaction et= em.getTransaction();
       et.begin();
       // em.joinTransaction();
        int x=  em.createNativeQuery("INSERT INTO permission_type (store,owner,type) VALUES (?,?,?)")
                .setParameter(1, storeName)
                .setParameter(2, owner)
                .setParameter(3, type.toString())
                .executeUpdate();


    et.commit();
      return x>0;
    }

    public void removePermissionFromSubscribe(Permission p, Subscribe subscribe) {
        SubscribeDao dao=new SubscribeDao();
        dao.remove(subscribe.getName());
        dao.addSubscribe(subscribe);
    }

    public boolean deletePermissionType(String storeName, String owner, PermissionType type){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et= em.getTransaction();
        et.begin();
        // em.joinTransaction();
        int x=  em.createNativeQuery("DELETE FROM permission_type WHERE store=? AND owner=? AND type =?")
                .setParameter(1, storeName)
                .setParameter(2, owner)
                .setParameter(3, type.toString())
                .executeUpdate();


        et.commit();
        return x>0;

    }

}
