package Persitent.DaoHolders;

import Persitent.Dao.*;
import Persitent.DaoInterfaces.*;
import Persitent.DaoProxy.*;

public class DaoHolder {
    private ICartDao cartDao;
    private ICategoryDao categoryDao;
    private IDiscountDao discountDao;
    private INotificationDao notificationDao;
    private IPermissionDao permissionDao;
    private IPolicyDao policyDao;
    private IProductDao productDao;
    private IPurchaseDao purchaseDao;
    private IRequestDao requestDao;
    private IStoreDao storeDao;
    private ISubscribeDao subscribeDao;
    private IReviewDao reviewDao;
    private IRevenueDao revenueDao;
    private IOwnerAgreementDao ownerAgreementDao;

    public DaoHolder() {
        cartDao =new CartDaoProxy();
        categoryDao=new CategoryDaoProxy();
        discountDao=new DiscountDaoProxy();
        notificationDao=new NotificationDaoProxy();
        permissionDao = new PermissionDaoProxy();
        policyDao=new PolicyDaoProxy();
        purchaseDao=new PurchaseDaoProxy();
        productDao=new ProductDaoProxy(categoryDao);
        requestDao=new RequestDaoProxy();
        storeDao=new StoreDaoProxy();
        subscribeDao=new SubscribeDaoProxy();
        reviewDao = new ReviewDaoProxy();
        revenueDao=new RevenueDaoProxy();
        ownerAgreementDao=new OwnerAgreementDaoProxy();
    }

    public ICartDao getCartDao() {
        return cartDao;
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

    public IRequestDao getRequestDao() {
        return requestDao;
    }

    public IStoreDao getStoreDao() {
        return storeDao;
    }

    public ISubscribeDao getSubscribeDao() {
        return subscribeDao;
    }

    public IReviewDao getReviewDao() {
        return reviewDao;
    }

    public IPermissionDao getPermissionDao() {
        return permissionDao;
    }

    public IRevenueDao getRevenueDao() {
        return revenueDao;
    }

    public IOwnerAgreementDao getOwnerAgreementDao() {
        return ownerAgreementDao;
    }
}
