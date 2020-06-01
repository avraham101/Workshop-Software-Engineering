package Persitent.DaoHolders;

import Persitent.*;
import Publisher.Publisher;

public class DaoHolder {
    private static CartDao cartDao;
    private static CategoryDao categoryDao;
    private static DiscountDao discountDao;
    private static NotificationDao notificationDao;
    private static PermissionDao permissionDao;
    private static PolicyDao policyDao;
    private static ProductDao productDao;
    private static PurchaseDao purchaseDao;
    private static RequestDao requestDao;
    private static StoreDao storeDao;
    private static SubscribeDao subscribeDao;
    private static ReviewDao reviewDao;
    private static RevenueDao revenueDao;
    private static OwnerAgreementDao ownerAgreementDao;

    public DaoHolder() {
        cartDao =new CartDao();
        categoryDao=new CategoryDao();
        discountDao=new DiscountDao();
        notificationDao=new NotificationDao();
        permissionDao = new PermissionDao();
        policyDao=new PolicyDao();
        purchaseDao=new PurchaseDao();
        productDao=new ProductDao(categoryDao);
        requestDao=new RequestDao();
        storeDao=new StoreDao();
        subscribeDao=new SubscribeDao();
        reviewDao = new ReviewDao();
        revenueDao=new RevenueDao();
        ownerAgreementDao=new OwnerAgreementDao();
    }

    public CartDao getCartDao() {
        return cartDao;
    }

    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

    public DiscountDao getDiscountDao() {
        return discountDao;
    }

    public NotificationDao getNotificationDao() {
        return notificationDao;
    }

    public PolicyDao getPolicyDao() {
        return policyDao;
    }

    public ProductDao getProductDao() {
        return productDao;
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

    public SubscribeDao getSubscribeDao() {
        return subscribeDao;
    }

    public ReviewDao getReviewDao() {
        return reviewDao;
    }

    public PermissionDao getPermissionDao() {
        return permissionDao;
    }

    public RevenueDao getRevenueDao() {
        return revenueDao;
    }

    public OwnerAgreementDao getOwnerAgreementDao() {
        return ownerAgreementDao;
    }
}
