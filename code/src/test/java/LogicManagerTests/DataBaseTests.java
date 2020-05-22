package LogicManagerTests;

import Data.Data;
import Data.TestData;
import DataAPI.ProductData;
import DataAPI.PurchaseTypeData;
import DataAPI.StoreData;
import Domain.*;
import Persitent.ProductDao;
import Persitent.ReviewDao;
import Persitent.StoreDao;
import Persitent.SubscribeDao;
import org.junit.Test;
import static org.junit.Assert.*;
public class DataBaseTests {

    TestData data = new TestData();
    ReviewDao reviewDao = new ReviewDao();
    ProductDao productDao = new ProductDao();
    StoreDao storeDao = new StoreDao();
    SubscribeDao subscribeDao = new SubscribeDao();
    @Test
    public void testReviewDao(){
        Store store = new Store("kfc",new Permission(data.getSubscribe(Data.VALID)),"food");
        assertTrue(subscribeDao.addSubscribe(data.getSubscribe(Data.VALID)));
        assertTrue(storeDao.addStore(store));
        Category cat = new Category("meat");
        ProductData pData = new ProductData("chicken","kfc",
                "meat",null,1,1, PurchaseTypeData.IMMEDDIATE);
        Product chicken = new Product(pData,cat);
        productDao.addProduct(chicken);
        Review review = new Review("niv", "kfc","chicken","very good");
        assertTrue(reviewDao.addReview(review));

    }
}
