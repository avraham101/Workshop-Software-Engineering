package Persitent;

import Domain.Notification.Notification;

import javax.persistence.*;
import javax.transaction.Transactional;

public class NotificationDao extends Dao<Notification>{
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("request");

    @Transactional
    public boolean add(Notification notification, String username) {
        // The EntityManager class allows operations such as create, read, update, delete
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        // Used to issue transactions on the EntityManager
        boolean output = super.add(em,notification);
        if(output) {
            //
            EntityTransaction et = null;
            try {
                em = ENTITY_MANAGER_FACTORY.createEntityManager();
                et = em.getTransaction();
                et.begin();
                Query query = em.createNativeQuery("INSERT INTO subscibe_notifications VALUES (?,?)");
                query.setParameter(1, username);
                query.setParameter(2, notification.getId());
                int res = query.executeUpdate();
                et.commit();
            } catch (Exception ignored) {
                if (et != null) {
                    et.rollback();
                }
            } finally {
                em.close();
            }
        }
        return output;
    }

    public Notification find(int id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        Notification n = null;

        try {
            n = em.find(Notification.class, id);
        } catch (Exception ex) {
        } finally {
            // Close EntityManager
            em.close();
        }
        return n;
    }

    public boolean remove(int id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        boolean output=false;

        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();
            Notification notification = em.find(Notification.class, id);
            em.remove(notification);
            et.commit();
            output=true;
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
}
