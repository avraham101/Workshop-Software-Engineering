package Persitent.DaoInterfaces;

import Domain.Notification.Notification;

public interface INotificationDao {
    boolean add(Notification<?> notification);
    Notification<?> find(int id);
    boolean remove(int id);
}

