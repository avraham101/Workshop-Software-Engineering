package Persitent.Dao;

import DataAPI.PermissionType;
import Domain.Permission;
import Domain.Subscribe;
import Persitent.DaoInterfaces.IPermissionDao;
import Persitent.DaoInterfaces.ISubscribeDao;
import Persitent.DaoProxy.SubscribeDaoProxy;
import Utils.Utils;

import javax.persistence.*;

public class PermissionDao extends Dao<Permission> implements IPermissionDao {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory(Utils.DB);

    public boolean addPermission(Permission permission){
        boolean output = false;
        // The EntityManager class allows operations such as create, read, update, delete
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        return super.add(em,permission);
    }

    public boolean removePermissionFromSubscribe(Permission perToDelete){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        boolean output=false;

        try {
            et = em.getTransaction();
            et.begin();

            int x =  em.createNativeQuery("DELETE FROM permission WHERE store=? AND owner=?")
                    .setParameter(1, perToDelete.getStore())
                    .setParameter(2, perToDelete.getOwner())
                    .executeUpdate();

            et.commit();
            output=x>0;
        }
        catch(Exception ex) {
            if (et != null) {
                et.rollback();
            }
            output= false;
        }
        finally {
            em.close();
        }
        return output;
    }

    public  Permission findPermission(Permission perToFind){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Permission permission = null;
        try {
            permission=em.find(Permission.class,perToFind);
        }
        catch(NoResultException ex) {

        }
        finally {
            em.close();
        }
        return permission;
    }

    public boolean addPermissionType(String storeName, String owner, PermissionType type){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et= em.getTransaction();
        et.begin();

        int x=  em.createNativeQuery("INSERT INTO permission_type (store,owner,type) VALUES (?,?,?)")
                .setParameter(1, storeName)
                .setParameter(2, owner)
                .setParameter(3, type.toString())
                .executeUpdate();


        et.commit();
        return x>0;
    }

    public void removePermissionFromSubscribe(Permission p, Subscribe subscribe) {
        ISubscribeDao dao=new SubscribeDaoProxy();
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
