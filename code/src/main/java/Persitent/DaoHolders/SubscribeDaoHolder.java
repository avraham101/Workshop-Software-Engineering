package Persitent.DaoHolders;

import Persitent.Dao.*;
import Persitent.DaoInterfaces.*;
import Persitent.DaoProxy.*;

public class SubscribeDaoHolder {
    private INotificationDao notificationDao;
    private IPurchaseDao purchaseDao;
    private IRequestDao requestDao;
    private IStoreDao storeDao;
    private static ICartDao cartDao;
    private IPermissionDao permissionDao;

    public SubscribeDaoHolder() {
        notificationDao=new NotificationDaoProxy();
        purchaseDao=new PurchaseDaoProxy();
        requestDao=new RequestDaoProxy();
        storeDao=new StoreDaoProxy();
        permissionDao = new PermissionDaoProxy();
        cartDao=new CartDaoProxy();
    }

    public INotificationDao getNotificationDao() {
        return notificationDao;
    }

    public IPurchaseDao getPurchaseDao() {
        return purchaseDao;
    }

    public IRequestDao getRequestDao() {
        return requestDao;
    }

    public IStoreDao getStoreDao() {
        return storeDao;
    }

    public IPermissionDao getPermissionDao() {
        return permissionDao;
    }

    public ICartDao getCartDao() {
        return cartDao;
    }

    public SubscribeDao getSubscribeDao() {return subscribeDao;}
}
