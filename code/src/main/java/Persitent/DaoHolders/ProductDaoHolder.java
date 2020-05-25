package Persitent.DaoHolders;

import Persitent.*;

public class ProductDaoHolder {
    private PurchaseDao purchaseDao;
    private PurchaseTypeDao purchaseTypeDao;
    private RequestDao requestDao;
    private SubscribeDao subscribeDao;
    private ReviewDao reviewDao;

    public ProductDaoHolder() {
        this.purchaseDao = new PurchaseDao();
        this.purchaseTypeDao = new PurchaseTypeDao();
        this.requestDao = new RequestDao();
        this.subscribeDao = new SubscribeDao();
        this.reviewDao = new ReviewDao();
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
