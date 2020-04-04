package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.*;
import AcceptanceTests.AcceptanceTestsBridge.AcceptanceTestsBridge;
import Domain.Product;
import junit.framework.TestCase;
import org.junit.Before;

import java.util.*;

public abstract class AcceptanceTests extends TestCase{
    protected AcceptanceTestsBridge bridge;
    protected List<UserTestData> users;
    protected List<StoreTestData> stores;
    protected List<ProductTestData> products;
    protected StoreTestData notExistingStore;
    protected DeliveryDetailsTestData validDelivery;
    protected  DeliveryDetailsTestData invalidDelivery;
    protected PaymentTestData validPayment;
    protected PaymentTestData invalidPayment;
    protected UserTestData superUser;
    protected UserTestData admin;

    public void setUp(){
        this.bridge = AcceptanceTestsDriver.getBridge();
        this.users = new ArrayList<>();
        this.stores = new ArrayList<>();
        this.products = new ArrayList<>();

        setUpUsers();
        setUpDelivery();
        setUpPayments();
        setUpProducts();
        setUpStores();
        setUpCarts();
        setUpPayments();
        setUpDelivery();
    }

    private void setUpPayments() {
        Date validDate = new Date();
        Date invalidDate = new Date(0);
        validPayment = new PaymentTestData("1111","343",validDate);
        invalidPayment = new PaymentTestData("1111","343",invalidDate);
    }

    private void setUpDelivery() {
        validDelivery = new DeliveryDetailsTestData("israel","ashdod","valid",7);
        invalidDelivery = new DeliveryDetailsTestData("israel","ashdod","invalid",-15);
    }


    private void setUpUsers() {
        admin = new UserTestData("admin","admin");
        UserTestData user0 = new UserTestData("testUser0","testUser0Pass");
        UserTestData user1 = new UserTestData("testUser1","testUser1Pass");
        UserTestData user2 = new UserTestData("testUser2","testUser2Pass");
        UserTestData user3 = new UserTestData("testUser3","testUser3Pass");
        superUser=user0;
        users.addAll(Arrays.asList(user0, user1,user2,user3));
        //TODO register Part Of Them???

    }

    private void setUpProducts(){
        ProductTestData prod0 = new ProductTestData("appleTest",
                                                    "store0Test",
                                                    30,
                                                    1.5,
                                                    "Fruits",
                                                    new ArrayList<ReviewTestData>());
        ProductTestData prod1 = new ProductTestData("milkTest",
                                                    "store0Test",
                                                    100,
                                                    5.79,
                                                    "Dairy",
                                                    new ArrayList<ReviewTestData>());
        ProductTestData prod2 = new ProductTestData("burekasTest",
                                                    "store0Test",
                                                    90,
                                                    2,
                                                    "Pastries",
                                                    new ArrayList<ReviewTestData>());
        ProductTestData prod3 = new ProductTestData("cocacolaTest",
                                                    "store1Test",
                                                    200,
                                                    6,
                                                    "Sodas",
                                                    new ArrayList<ReviewTestData>());
        ProductTestData prod4 = new ProductTestData("waterTest",
                                                    "store1Test",
                                                    500,
                                                    3,
                                                    "Sodas",
                                                    new ArrayList<ReviewTestData>());
        ProductTestData prod5 = new ProductTestData("milkiTest",
                                                    "store1Test",
                                                    52,
                                                    4,
                                                    "Dairy",
                                                    new ArrayList<ReviewTestData>());
        ProductTestData prod6 = new ProductTestData("tomatoTest",
                                                    "store2Test",
                                                    140,
                                                    3.5,
                                                    "Vegetables",
                                                    new ArrayList<ReviewTestData>());
        ProductTestData prod7 = new ProductTestData("onionTest",
                                                    "store2Test",
                                                    100,
                                                    1.5,
                                                    "Vegetables",
                                                    new ArrayList<ReviewTestData>());
        ProductTestData prod8 = new ProductTestData("bambaTest",
                                                    "store2Test",
                                                    100,
                                                    3.80,
                                                    "Snacks",
                                                    new ArrayList<ReviewTestData>());
        ProductTestData prod9 = new ProductTestData("cheeseTest",
                                                    "store2Test",
                                                    500,
                                                    4.5,
                                                    "Dairy",
                                                    new ArrayList<ReviewTestData>());

        products.addAll(Arrays.asList(prod0, prod1,prod2,
                                        prod3,prod4,prod5,
                                        prod6,prod7,prod8,prod9));

    }

    private void setUpStores(){

        UserTestData store0Manager = users.get(0);
        UserTestData store1Manager = users.get(1);
        UserTestData store2Manager = users.get(2);
        StoreTestData store0 = new StoreTestData("store0Test",store0Manager);
        StoreTestData store1 = new StoreTestData("store1Test",store0Manager);
        StoreTestData store2 = new StoreTestData("store2Test",store1Manager);
        store0.addPermission(store1Manager.getUsername(),PermissionsTypeTestData.PRODUCTS_INVENTORY);
        stores.addAll(Arrays.asList(store0,store1,store2));

        List<ProductTestData> notExistingStoreProducts = products.subList(0,4);
        UserTestData notExistingStoreManager = users.get(1);
        notExistingStore = new StoreTestData("notExistingStore",
                                            notExistingStoreManager);

    }

    private void setUpCarts() {
        List<ProductTestData> basket0Products = products.subList(0,2);
        List<ProductTestData> basket1Products = Arrays.asList(products.get(4));
        List<ProductTestData> basket2Products = Arrays.asList(products.get(9));

        BasketTestData basket0 = new BasketTestData(stores.get(0).getStoreName());
        BasketTestData basket1 = new BasketTestData(stores.get(1).getStoreName());
        BasketTestData basket2 = new BasketTestData(stores.get(2).getStoreName());

        int[] amounts0 = {12,3,6};
        int[] amounts1 = {7};
        int[] amounts2 = {42};

        setUpBasketProductsAndAmounts(basket0,basket0Products, amounts0);
        setUpBasketProductsAndAmounts(basket1,basket1Products, amounts1);
        setUpBasketProductsAndAmounts(basket2,basket2Products, amounts2);

        List<BasketTestData> baskets0 = Arrays.asList(basket0,basket1,basket2);
        superUser.getCart().addBasketsToCart(baskets0);

    }

    private void setUpBasketProductsAndAmounts(BasketTestData basket, List<ProductTestData> basketProducts, int[] amounts) {
        for (int i=0;i<amounts.length;i++)
            basket.addProductToBasket(basketProducts.get(i),amounts[i]);
    }

    private String getPasswordByUser(String userName){
        for (UserTestData ud:users) {
            if(ud.getUsername().equals(userName))
                return ud.getPassword();

        }
        return null;
    }

    protected  void deleteStores (List<StoreTestData> stores){
        String userName = bridge.getCurrentLoggedInUser();
        if(userName!=null){
            bridge.logout(userName);
        }
        bridge.login(superUser.getUsername(),superUser.getPassword());
        bridge.deleteStores(stores);
        bridge.logout(superUser.getUsername());
        if(userName!=null){
            bridge.login(userName,getPasswordByUser(userName));
        }

    }

    protected  void addStores(List<StoreTestData> stores){
        String userName = bridge.getCurrentLoggedInUser();
        if(userName!=null){
            bridge.logout(userName);
        }
        bridge.login(superUser.getUsername(),superUser.getPassword());
        bridge.addStores(stores);
        bridge.logout(superUser.getUsername());
        if(userName!=null){
            bridge.login(userName,getPasswordByUser(userName));
        }

    }

    protected void deleteProducts( List<ProductTestData> products){
        String userName = bridge.getCurrentLoggedInUser();
        if(userName!=null){
            bridge.logout(userName);
        }
        bridge.login(superUser.getUsername(),superUser.getPassword());
        bridge.deleteProducts(products);
        bridge.logout(superUser.getUsername());
        if(userName!=null){
            bridge.login(userName,getPasswordByUser(userName));
        }

    }
    protected  void addProducts(List<ProductTestData> products){
        String userName = bridge.getCurrentLoggedInUser();
        if(userName!=null){
            bridge.logout(userName);
        }
        bridge.login(superUser.getUsername(),superUser.getPassword());
        bridge.addProducts(products);
        bridge.logout(superUser.getUsername());
        if(userName!=null){
            bridge.login(userName,getPasswordByUser(userName));
        }

    }

    protected void changeAmountOfProductInStore(ProductTestData product,int amount){
        String userName = bridge.getCurrentLoggedInUser();
        if(userName!=null){
            bridge.logout(userName);
        }
        bridge.login(superUser.getUsername(),superUser.getPassword());
        bridge.changeAmountOfProductInStore(product,amount);
        bridge.logout(superUser.getUsername());
        if(userName!=null){
            bridge.login(userName,getPasswordByUser(userName));
        }
    }


    protected void deleteUser(String username){
        String currUsername = bridge.getCurrentLoggedInUser();

        if(!username.equals(currUsername)){
            bridge.logout(currUsername);
            bridge.login(superUser.getUsername(),superUser.getPassword());
            bridge.deleteUser(username);
            String currPassword = getPasswordByUser(currUsername);
            bridge.login(currUsername,currPassword);
        }
    }




}
