package Persitent.DaoHolders;

import Persitent.*;

public class SubscribeDaoHolder {
    private NotificationDao notificationDao;
    private PurchaseDao purchaseDao;
    private RequestDao requestDao;
    private StoreDao storeDao;

    public SubscribeDaoHolder() {
        notificationDao=new NotificationDao();
        purchaseDao=new PurchaseDao();
        requestDao=new RequestDao();
        storeDao=new StoreDao();
    }

    public NotificationDao getNotificationDao() {
        return notificationDao;
    }

    public PurchaseDao getPurchaseDao() {
        return purchaseDao;
    }

    public RequestDao getRequestDao() {
        return requestDao;
    }

    public StoreDao getStoreDao() {
        return storeDao;
    }
}
