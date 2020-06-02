package Persitent.DaoHolders;

import Persitent.Dao.*;
import Persitent.DaoInterfaces.*;
import Persitent.DaoProxy.*;

public class ProductDaoHolder {
    private IPurchaseDao purchaseDao;
    private IPurchaseTypeDao purchaseTypeDao;
    private IRequestDao requestDao;
    private ISubscribeDao subscribeDao;
    private IReviewDao reviewDao;

    public ProductDaoHolder() {
        purchaseDao = new PurchaseDaoProxy();
        purchaseTypeDao = new PurchaseTypeDaoProxy();
        requestDao = new RequestDaoProxy();
        subscribeDao = new SubscribeDaoProxy();
        reviewDao = new ReviewDaoProxy();
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

    public ISubscribeDao getSubscribeDao() {
        return subscribeDao;
    }

    public IReviewDao getReviewDao() {
        return reviewDao;
    }
}
