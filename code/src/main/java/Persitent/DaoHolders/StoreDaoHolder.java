package Persitent.DaoHolders;

import Persitent.Dao.*;
import Persitent.DaoInterfaces.*;
import Persitent.DaoProxy.*;

public class StoreDaoHolder {
    private ICategoryDao categoryDao;
    private IDiscountDao discountDao;
    private INotificationDao notificationDao;
    private IPolicyDao policyDao;
    private IProductDao productDao;
    private IPurchaseDao purchaseDao;
    private IPurchaseTypeDao purchaseTypeDao;
    private IRequestDao requestDao;
    private ISubscribeDao subscribeDao;
    private IOwnerAgreementDao ownerAgreementDao;

    public StoreDaoHolder() {
        categoryDao = new CategoryDaoProxy();
        discountDao = new DiscountDaoProxy();
        notificationDao = new NotificationDaoProxy();
        policyDao = new PolicyDaoProxy();
        productDao = new ProductDaoProxy(categoryDao);
        purchaseDao = new PurchaseDaoProxy();
        purchaseTypeDao = new PurchaseTypeDaoProxy();
        requestDao = new RequestDaoProxy();
        subscribeDao=new SubscribeDaoProxy();
        ownerAgreementDao=new OwnerAgreementDaoProxy();
    }

    public ICategoryDao getCategoryDao() {
        return categoryDao;
    }

    public IDiscountDao getDiscountDao() {
        return discountDao;
    }

    public INotificationDao getNotificationDao() {
        return notificationDao;
    }

    public IPolicyDao getPolicyDao() {
        return policyDao;
    }

    public IProductDao getProductDao() {
        return productDao;
    }

    public IPurchaseDao getPurchaseDao() {
        return purchaseDao;
    }

    public IPurchaseTypeDao getPurchaseTypeDao() {
        return purchaseTypeDao;
    }

    public IRequestDao getRequestDao() {
        return requestDao;
    }

    public IOwnerAgreementDao getOwnerAgreementDao() {
        return ownerAgreementDao;
    }

    public ISubscribeDao getSubscribeDao() {
        return subscribeDao;
    }

}
