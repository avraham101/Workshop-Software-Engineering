package Persitent.DaoHolders;

import Persitent.*;

public class ProductDaoHolder {
    private ProductDao productDao;
    private PurchaseDao purchaseDao;
    private PurchaseTypeDao purchaseTypeDao;
    private RequestDao requestDao;
    private SubscribeDao subscribeDao;
    private ReviewDao reviewDao;

    public ProductDaoHolder() {
        this.productDao = productDao;
        this.purchaseDao = purchaseDao;
        this.purchaseTypeDao = purchaseTypeDao;
        this.requestDao = requestDao;
        this.subscribeDao = subscribeDao;
        this.reviewDao = reviewDao;
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

    public SubscribeDao getSubscribeDao() {
        return subscribeDao;
    }

    public ReviewDao getReviewDao() {
        return reviewDao;
    }
}
