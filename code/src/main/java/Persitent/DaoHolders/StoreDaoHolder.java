package Persitent.DaoHolders;

import Persitent.*;

public class StoreDaoHolder {
    private CategoryDao categoryDao;
    private DiscountDao discountDao;
    private NotificationDao notificationDao;
    private PolicyDao policyDao;
    private ProductDao productDao;
    private PurchaseDao purchaseDao;
    private PurchaseTypeDao purchaseTypeDao;
    private RequestDao requestDao;
    private SubscribeDao subscribeDao;
    private OwnerAgreementDao ownerAgreementDao;

    public StoreDaoHolder() {
        this.categoryDao = new CategoryDao();
        this.discountDao = new DiscountDao();
        this.notificationDao = new NotificationDao();
        this.policyDao = new PolicyDao();
        this.productDao = new ProductDao(categoryDao);
        this.purchaseDao = new PurchaseDao();
        this.purchaseTypeDao = new PurchaseTypeDao();
        this.requestDao = new RequestDao();
        this.subscribeDao=new SubscribeDao();
        this.ownerAgreementDao=new OwnerAgreementDao();
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
