package Stubs;

import Persitent.*;
import Persitent.DaoHolders.DaoHolder;

public class StubDaoHolder extends DaoHolder {

    private CartDao cartDao;
    private CategoryDao categoryDao;
    private DiscountDao discountDao;
    private NotificationDao notificationDao;
    private PolicyDao policyDao;
    private ProductDao productDao;
    private PurchaseDao purchaseDao;
    private RequestDao requestDao;
    private StubStoreDao storeDao;
    private StubSubscribeDao subscribeDao;

    public StubDaoHolder() {
        storeDao=new StubStoreDao();
        subscribeDao=new StubSubscribeDao();
    }

    @Override
    public StoreDao getStoreDao() {
        return storeDao;
    }

    @Override
    public SubscribeDao getSubscribeDao() {
        return subscribeDao;
    }
}
