package Persitent.DaoHolders;

import Persitent.*;

public class StoreDaoHolder {
    private static CategoryDao categoryDao;
    private static DiscountDao discountDao;
    private static NotificationDao notificationDao;
    private static PolicyDao policyDao;
    private static ProductDao productDao;
    private static PurchaseDao purchaseDao;
    private static PurchaseTypeDao purchaseTypeDao;
    private static RequestDao requestDao;
    private static SubscribeDao subscribeDao;
    private static OwnerAgreementDao ownerAgreementDao;

    public StoreDaoHolder() {
        categoryDao = new CategoryDao();
        discountDao = new DiscountDao();
        notificationDao = new NotificationDao();
        policyDao = new PolicyDao();
        productDao = new ProductDao(categoryDao);
        purchaseDao = new PurchaseDao();
        purchaseTypeDao = new PurchaseTypeDao();
        requestDao = new RequestDao();
        subscribeDao=new SubscribeDao();
        ownerAgreementDao=new OwnerAgreementDao();
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

    public PurchaseTypeDao getPurchaseTypeDao() {
        return purchaseTypeDao;
    }

    public RequestDao getRequestDao() {
        return requestDao;
    }

    public OwnerAgreementDao getOwnerAgreementDao() {
        return ownerAgreementDao;
    }

    public SubscribeDao getSubscribeDao() {
        return subscribeDao;
    }

}
