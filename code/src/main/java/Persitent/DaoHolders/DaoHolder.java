package Persitent.DaoHolders;

import Persitent.*;
import Publisher.Publisher;

public class DaoHolder {
    private CartDao cartDao;
    private CategoryDao categoryDao;
    private DiscountDao discountDao;
    private NotificationDao notificationDao;
    private PermissionDao permissionDao;
    private PolicyDao policyDao;
    private ProductDao productDao;
    private PurchaseDao purchaseDao;
    private RequestDao requestDao;
    private StoreDao storeDao;
    private SubscribeDao subscribeDao;
    private ReviewDao reviewDao;
    private RevenueDao revenueDao;

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
}
