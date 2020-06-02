package Persitent.DaoHolders;

import Persitent.Dao.*;
import Persitent.DaoInterfaces.*;
import Persitent.DaoProxy.*;

public class SubscribeDaoHolder {
    private INotificationDao notificationDao;
    private IPurchaseDao purchaseDao;
    private IRequestDao requestDao;
    private IStoreDao storeDao;
    private ICartDao cartDao;
    private IPermissionDao permissionDao;
    private ISubscribeDao subscribeDao;

    public SubscribeDaoHolder() {
        notificationDao=new NotificationDaoProxy();
        purchaseDao=new PurchaseDaoProxy();
        requestDao=new RequestDaoProxy();
        storeDao=new StoreDaoProxy();
        permissionDao = new PermissionDaoProxy();
        cartDao=new CartDaoProxy();
        subscribeDao=new SubscribeDaoProxy();
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

    public ISubscribeDao getSubscribeDao() {return subscribeDao;}
}
