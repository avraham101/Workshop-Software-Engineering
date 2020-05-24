package Persitent.DaoHolders;

import Persitent.*;

public class SubscribeDaoHolder {
    private NotificationDao notificationDao;
    private PurchaseDao purchaseDao;
    private RequestDao requestDao;
    private StoreDao storeDao;
    private CartDao cartDao;
    private PermissionDao permissionDao;

    public SubscribeDaoHolder() {
        notificationDao=new NotificationDao();
        purchaseDao=new PurchaseDao();
        requestDao=new RequestDao();
        storeDao=new StoreDao();
        permissionDao = new PermissionDao();
        cartDao=new CartDao();
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

    public PermissionDao getPermissionDao() {
        return permissionDao;
    }

    public CartDao getCartDao() {
        return cartDao;
    }
}
