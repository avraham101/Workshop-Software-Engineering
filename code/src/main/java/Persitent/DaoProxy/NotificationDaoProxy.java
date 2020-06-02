package Persitent.DaoProxy;

import Domain.Notification.Notification;
import Persitent.Dao.NotificationDao;
import Persitent.DaoInterfaces.INotificationDao;

public class NotificationDaoProxy implements INotificationDao {

    private NotificationDao dao;

    public NotificationDaoProxy(){
        this.dao = new NotificationDao();
    }

    @Override
    public boolean add(Notification<?> notification, String username) {
        try{
            return dao.add(notification,username);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public Notification<?> find(int id) {
        try{
            return dao.find(id);
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean remove(int id) {
        try{
            return dao.remove(id);
        }catch (Exception e) {
            return false;
        }
    }
}
