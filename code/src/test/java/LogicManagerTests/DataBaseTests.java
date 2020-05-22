package LogicManagerTests;

import Data.Data;
import Data.TestData;
import DataAPI.ProductData;
import DataAPI.PurchaseTypeData;
import DataAPI.StoreData;
import Domain.*;
import Persitent.*;
import org.junit.Test;
import static org.junit.Assert.*;
public class DataBaseTests {

    TestData data = new TestData();
    ReviewDao reviewDao = new ReviewDao();
    ProductDao productDao = new ProductDao(new CategoryDao());
    StoreDao storeDao = new StoreDao();
    SubscribeDao subscribeDao = new SubscribeDao();

    public void reviewSetUp(Subscribe sub,Store store){
        assertTrue(subscribeDao.addSubscribe(data.getSubscribe(Data.VALID)));
        assertTrue(storeDao.addStore(store));
        Category cat = new Category("meat");
        ProductData pData = new ProductData("chicken",store.getName(),
                "meat",null,1,1, PurchaseTypeData.IMMEDDIATE);
        Product chicken = new Product(pData,cat);
        productDao.addProduct(chicken);

    }
    @Test
    public void testReviewDaoAdd(){
        Subscribe sub = data.getSubscribe(Data.VALID);
        Store store = sub.openStore(data.getStore(Data.VALID));
        //reviewSetUp(sub,store);
        Review review = new Review(sub.getName(), store.getName(),"chicken","very good");
        assertTrue(reviewDao.addReview(review));
        int i=7;

    }

    @Test
    public void testReviewDaoFind(){
       Review review=  reviewDao.find(3);
       assertNotNull(review);
    }

    @Test
    public void testReviewDaoDelete(){
        assertTrue( reviewDao.remove(3));
    }

}
