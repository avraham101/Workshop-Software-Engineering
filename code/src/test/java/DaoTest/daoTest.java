package DaoTest;

import Data.*;
import DataAPI.*;
import Domain.*;
import Domain.Discount.Discount;
import Domain.Discount.RegularDiscount;
import Domain.Notification.*;
import Domain.PurchasePolicy.*;
import Domain.PurchasePolicy.ComposePolicys.*;
import Persitent.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class daoTest {

    private TestData data;
    @Before
    public void setUp() throws Exception {
        data=new TestData();
    }

    @Test
    public void test(){
        SubscribeDao dao=new SubscribeDao();
        StoreDao storeDao=new StoreDao();
        Subscribe shmu=new Subscribe("yuv","pa");
//        //shmu.addRequest(0,"hanut","hello");
//        Subscribe shmu=dao.find("yuv");
//        dao.addSubscribe(shmu);

        Permission p=new Permission(shmu);

        Store s=storeDao.find("hanut");
        p.setStore(s);
        shmu.getPermissions().put("hanut",p);
        shmu.addReview(new Review(shmu.getName(),s.getName(),"proc8","hello"));
        dao.addSubscribe(shmu);
        //StoreDao storeDao=new StoreDao();
//        storeDao.addStore(s);
//        Store store=storeDao.find("hanut");
        assertTrue(true);
    }


    @Test
    public void addAgePolicy(){
//        SubscribeDao dao=new SubscribeDao();
//        Subscribe shmu=dao.find("yub");
////        dao.addSubscribe(shmu);
////        Permission p=new Permission(shmu);
////        Store s=new Store("hanut",p,"yuv");
////        p.setStore(s);
//        StoreDao storeDao=new StoreDao();
//        //Store store=storeDao.find("hanut");
//        assertTrue(true);

        PolicyDao pdao= new PolicyDao();
        SystemPurchasePolicy sysp = new SystemPurchasePolicy(7);
        List<PurchasePolicy> lst = new ArrayList<>();
        lst.add(sysp);
        PurchasePolicy p = new OrPolicy(lst);
        pdao.addPolicy(p);
        assertTrue(true);
    }

    @Test
    public void addSubscribe(){
        SubscribeDao dao=new SubscribeDao();
        StoreDao storeDao=new StoreDao();
        Subscribe shmu=new Admin("yuv","Admin");
//        Basket b=new Basket(storeDao.find("store"),"yuv");
//        ProductData p=data.getProductData(Data.VALID);
//        b.addProduct(new Product(p,new Category("cat")),3);
//        shmu.getCart().getBaskets().put("Store",b);
        dao.addSubscribe(shmu);
    }

    @Test
    public void addStore(){
        SubscribeDao dao=new SubscribeDao();
        Subscribe shmu=dao.find("yuv");
        StoreDao storeDao=new StoreDao();
        Permission p=new Permission(shmu);

        Store s=new Store("Store",p,"yuvdesc");
        p.setStore(s);
        //shmu.getPermissions().put("hanut",p);
        //shmu.addReview(new Review(shmu.getName(),s.getName(),"proc8","hello"));
        storeDao.addStore(s);
    }

    @Test
    public void removePermission(){
        PermissionDao daop=new PermissionDao();
        SubscribeDao dao=new SubscribeDao();
        Subscribe shmu=dao.find("yuv");
        Permission p=shmu.getPermissions().get("Store");
        StoreDao storeDao=new StoreDao();
        Store store=storeDao.find("Store");
        store.getPermissions().remove("yuv");
        daop.removePermissionFromSubscribe(p);
        storeDao.update(store);

        shmu.getPermissions().remove("Store");
        dao.update(shmu);

//        daop.removePermission(new Permission(dao.find("yuv"),
//                storeDao.find("Store")));


    }


    @Test
    public void addStoreWith(){
        StoreDao storeDao=new StoreDao();
        Store store=storeDao.find("hanut");
        PolicyDao pdao= new PolicyDao();
        PurchasePolicy basketp = new BasketPurchasePolicy(25);
        List<PurchasePolicy> lst1 = new ArrayList<>();
        lst1.add(basketp);
        PurchasePolicy p = new XorPolicy(lst1);
        //pdao.addPolicy(p);
        //storeDao.update(store);
        //pdao.addPolicy(p);
        store.addPolicy(p);
        storeDao.update(store);
    }

    @Test
    public void findSub(){
        SubscribeDao subdao=new SubscribeDao();
        Subscribe sub=subdao.find("yuv");
        assertTrue(true);
    }

    @Test
    public void findStore(){
        StoreDao storeDao=new StoreDao();
        Store s=storeDao.find("Store");
        assertTrue(true);
    }

    @Test
    public void addCountryPolicy(){
        PolicyDao pdao= new PolicyDao();
        List<String> lst = new ArrayList<>();
        lst.add("england");
        lst.add("usa");
        PurchasePolicy usrp = new UserPurchasePolicy(lst);
        List<PurchasePolicy> lst1 = new ArrayList<>();
        lst1.add(usrp);
        PurchasePolicy p = new AndPolicy(lst1);
        pdao.addPolicy(p);
        assertTrue(true);
    }

    @Test
    public void addBasket(){
        PolicyDao pdao = new PolicyDao();
        PurchasePolicy basketp = new BasketPurchasePolicy(25);
        List<PurchasePolicy> lst1 = new ArrayList<>();
        lst1.add(basketp);
        PurchasePolicy p = new XorPolicy(lst1);
        pdao.addPolicy(p);
        assertTrue(true);
    }

    @Test
    public void removeSubscribe(){
        SubscribeDao subdao=new SubscribeDao();
        //subdao.remove("Admin");
        subdao.remove("Yuval");
    }

    @Test
    public void reset(){
        SubscribeDao subdao=new SubscribeDao();
        //subdao.remove("Admin");
        subdao.remove("Yuval");
        subdao.remove("Admin");
        subdao.remove("Niv");
        subdao.remove("yuv");
        StoreDao storeDao=new StoreDao();
        //Store store=storeDao.find("hanut");
        storeDao.removeStore("Store");
    }

    @Test
    public void removeStore(){
        StoreDao storeDao=new StoreDao();
        //Store store=storeDao.find("hanut");
        storeDao.removeStore("Store");
    }

    @Test
    public void addProductPolicy(){
        PolicyDao dao = new PolicyDao();
        ProductMinMax minmax = new ProductMinMax("banana",7,0);
        HashMap<String,ProductMinMax> map = new HashMap<>();
        map.put("banana",minmax);
        ProductPurchasePolicy p = new ProductPurchasePolicy(map);
        dao.addPolicy(p);
        assertTrue(true);
    }
    @Test
    public void addRequest(){
        RequestDao requestDao=new RequestDao();
        requestDao.addRequest(new Request("yuv","hanut","hello",0));
    }

    @Test
    public void testDis(){
        Discount d = new RegularDiscount("banana",15 );
        DiscountDao dao = new DiscountDao();
        dao.addDiscount(d);

    }

    @Test
    public void addDiscount(){
        DiscountDao discountDao=new DiscountDao();
        discountDao.addDiscount(new RegularDiscount("shok",8));
    }

    @Test
    public void findPolicy(){
        int id=227;
        PolicyDao policyDao=new PolicyDao();
        PurchasePolicy p= policyDao.find(id);
        assertTrue(true);
    }

    @Test
    public void removePolicy(){
        int id=227;
        PolicyDao policyDao=new PolicyDao();
        policyDao.removePolicy(id);
    }

    @Test
    public void findDis(){
        DiscountDao dao = new DiscountDao();
        Discount d =dao.find(2);
        int i=2;
    }

    @Test
    public void addPurchase(){
        PurchaseDao purchaseDao=new PurchaseDao();
        List<ProductData> l=new ArrayList<>();
        l.add(new ProductData("pro","hanut","hey",new ArrayList<>(),5,6, PurchaseTypeData.IMMEDDIATE));
        purchaseDao.add(new Purchase("hanut","yuv",l));
    }

    @Test
    public void addRemoveNotification(){
        RemoveNotification notification=new RemoveNotification("hanut", OpCode.Removed_From_Management);
        NotificationDao dao=new NotificationDao();
        dao.add(notification);
    }

    @Test
    public void addSubscribeWith(){
        SubscribeDao dao=new SubscribeDao();
        //StoreDao storeDao=new StoreDao();
        Subscribe shmu=new Subscribe("shhu","pa");
        dao.addSubscribe(shmu);
    }

    @Test
    public void addRequestNotification(){
        RequestDao requestDao=new RequestDao();
        Request r=requestDao.find(1);
        RequestNotification notification=new RequestNotification(r,OpCode.Reply_Request);
        NotificationDao dao=new NotificationDao();
        dao.add(notification);
    }

    @Test
    public void addBuyNotification(){
        List<ProductData> products=new ArrayList<>();
        products.add(data.getProductData(Data.VALID));
        BuyNotification notification=new BuyNotification(products,OpCode.Buy_Product);
        NotificationDao dao=new NotificationDao();
        dao.add(notification);
    }

    @Test
    public void findNotification(){
        NotificationDao dao=new NotificationDao();
        Notification n=dao.find(4);
        assertTrue(true);
    }

    @Test
    public void removeNotification(){
        NotificationDao dao=new NotificationDao();
        dao.remove(4);
    }
}
