package Persitent.DaoHolders;

import Persitent.*;

public class ProductDaoHolder {
    private static PurchaseDao purchaseDao;
    private static PurchaseTypeDao purchaseTypeDao;
    private static RequestDao requestDao;
    private static SubscribeDao subscribeDao;
    private static ReviewDao reviewDao;

    public ProductDaoHolder() {
        purchaseDao = new PurchaseDao();
        purchaseTypeDao = new PurchaseTypeDao();
        requestDao = new RequestDao();
        subscribeDao = new SubscribeDao();
        reviewDao = new ReviewDao();
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
