package LogicManagerTests;

import Data.Data;
import Data.TestData;
import DataAPI.PermissionType;
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

    public void permissionSetUp(Subscribe sub,Store store){
        assertTrue(subscribeDao.addSubscribe(sub));
        assertTrue(storeDao.addStore(store));
        assertTrue(subscribeDao.addSubscribe(data.getSubscribe(Data.VALID2)));

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

    @Test
    public void addSub(){
        Subscribe sub = new Subscribe("nvi","vvin");
        SubscribeDao dao = new SubscribeDao();
       assertTrue( dao.addSubscribe(sub));

    }

    @Test
    public  void delSub(){
        SubscribeDao dao = new SubscribeDao();
        dao.remove("Yuval");
        dao.remove("niv");
    }

    @Test
    public void delStore(){
        storeDao.removeStore("Store");
    }

    @Test
    public void addPermission(){
        Subscribe sub = data.getSubscribe(Data.VALID);

        //permissionSetUp(sub,store);
        assertTrue(subscribeDao.addSubscribe(sub));
        Store store = sub.openStore(data.getStore(Data.VALID));
        assertTrue(subscribeDao.addSubscribe(data.getSubscribe(Data.VALID2)));
        PermissionDao dao = new PermissionDao();
        Permission per = new Permission(data.getSubscribe(Data.VALID2),store);
        per.setGivenBy(sub.getName());
        assertTrue(dao.addPermission(per));
    }

    @Test
    public void delPermission(){
        PermissionDao dao = new PermissionDao();
        Store store = storeDao.find("Store");
        Permission per = dao.findPermission(new Permission(data.getSubscribe(Data.VALID2),store));
        assertTrue(dao.removePermission(per));

    }

    @Test
    public void addPermissionType(){
        PermissionDao dao = new PermissionDao();
        assertTrue(dao.addPermissionType("Store","Yuval", PermissionType.CRUD_POLICY_DISCOUNT));
       // Store store = storeDao.find("Store");
     //   Permission per = dao.findPermission(new Permission(data.getSubscribe(Data.VALID),store));



    }

    @Test
    public void deletePermissionType(){
        PermissionDao dao = new PermissionDao();
        assertTrue(dao.deletePermissionType("Store","Yuval",PermissionType.CRUD_POLICY_DISCOUNT));
    }
}
