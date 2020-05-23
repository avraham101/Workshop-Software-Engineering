package Persitent;

import Domain.Permission;
import Domain.Product;
import Domain.Subscribe;

import javax.persistence.*;

public class PermissionDao extends Dao<Permission> {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("product");

    public boolean addPermission(Permission permission){
        boolean output = false;
        // The EntityManager class allows operations such as create, read, update, delete
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        return super.add(em,permission);
    }

    public boolean removePermission(Permission perToDelete){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();
            Permission permission = em.find(Permission.class,perToDelete);
           // em.remove(permission);
            em.remove(em.contains(permission) ? permission : em.merge(permission));
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


}
