package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.*;
import AcceptanceTests.AcceptanceTestsBridge.AcceptanceTestsBridge;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.util.*;

import static junit.framework.TestCase.assertTrue;

public class AcceptanceTests {
    protected static AcceptanceTestsBridge bridge;
    protected static List<UserTestData> users;
    protected static List<StoreTestData> stores;
    protected static List<ProductTestData> products;
    protected static StoreTestData notExistingStore;
    protected static DeliveryDetailsTestData validDelivery;
    protected static DeliveryDetailsTestData invalidDelivery;
    protected static PaymentTestData validPayment;
    protected static PaymentTestData invalidPayment;
    protected static UserTestData superUser;
    protected static UserTestData admin;

    @BeforeClass
    public static void setUpAll(){
        bridge = AcceptanceTestsDriver.getBridge();
        users = new ArrayList<>();
        stores = new ArrayList<>();
        products = new ArrayList<>();

        setUpUsers();
        setUpDelivery();
        setUpPayments();
        setUpProducts();
        setUpStores();
        setUpCarts();
        setUpPayments();
        setUpDelivery();
    }


    private static void setUpPayments() {
        Date validDate = new Date();
        Date invalidDate = new Date(0);
        validPayment = new PaymentTestData("ownerValid","1111","343",validDate);
        invalidPayment = new PaymentTestData("ownerInvalid","1111","343",invalidDate);
    }

    private static void setUpDelivery() {
        validDelivery = new DeliveryDetailsTestData
                ("israel","ashdod","valid",7);
        invalidDelivery = new DeliveryDetailsTestData
                ("israel","ashdod","invalid",-15);
    }


    private static void setUpUsers() {
        admin = new UserTestData("admin","admin");
        UserTestData user0 = new UserTestData("testUser0","testUser0Pass");
        UserTestData user1 = new UserTestData("testUser1","testUser1Pass");
        UserTestData user2 = new UserTestData("testUser2","testUser2Pass");
        UserTestData user3 = new UserTestData("testUser3","testUser3Pass");
        superUser = user0;
        users.addAll(Arrays.asList(user0, user1,user2,user3));
        boolean init = bridge.initialStart(admin.getUsername(),admin.getPassword());
        assertTrue(init);
    }

    private static void setUpProducts(){
        ProductTestData prod0 = new ProductTestData("appleTest",
                                                    "store0Test",
                                                    30,
                                                    1.5,
                                                    "Fruits",
                                                    new ArrayList<>(),new ArrayList<>());
        ProductTestData prod1 = new ProductTestData("milkTest",
                                                    "store0Test",
                                                    100,
                                                    5.79,
                                                    "Dairy",
                                                    new ArrayList<>(),new ArrayList<>());
        ProductTestData prod2 = new ProductTestData("burekasTest",
                                                    "store0Test",
                                                    90,
                                                    2,
                                                    "Pastries",
                                                    new ArrayList<>(),new ArrayList<>());
        ProductTestData prod3 = new ProductTestData("cocacolaTest",
                                                    "store1Test",
                                                    200,
                                                    6,
                                                    "Sodas",
                                                    new ArrayList<>(),new ArrayList<>());
        ProductTestData prod4 = new ProductTestData("waterTest",
                                                    "store1Test",
                                                    500,
                                                    3,
                                                    "Sodas",
                                                    new ArrayList<>(),new ArrayList<>());
        ProductTestData prod5 = new ProductTestData("milkiTest",
                                                    "store1Test",
                                                    52,
                                                    4,
                                                    "Dairy",
                                                    new ArrayList<>(),new ArrayList<>());
        ProductTestData prod6 = new ProductTestData("tomatoTest",
                                                    "store2Test",
                                                    140,
                                                    3.5,
                                                    "Vegetables",
                                                    new ArrayList<>(),new ArrayList<>());
        ProductTestData prod7 = new ProductTestData("onionTest",
                                                    "store2Test",
                                                    100,
                                                    1.5,
                                                    "Vegetables",
                                                    new ArrayList<>(),new ArrayList<>());
        ProductTestData prod8 = new ProductTestData("bambaTest",
                                                    "store2Test",
                                                    100,
                                                    3.80,
                                                    "Snacks",
                                                    new ArrayList<>(), new ArrayList<>());
        ProductTestData prod9 = new ProductTestData("cheeseTest",
                                                    "store2Test",
                                                    500,
                                                    4.5,
                                                    "Dairy",
                                                    new ArrayList<>(),
                                                    new ArrayList<>());

        products.addAll(Arrays.asList(prod0, prod1,prod2,
                                        prod3,prod4,prod5,
                                        prod6,prod7,prod8,prod9));

    }

    private static void setUpStores(){

        UserTestData store0Owner = users.get(0);
        UserTestData store1Owner = users.get(1);

        bridge.register(store0Owner.getUsername(),store0Owner.getPassword());
        bridge.register(store1Owner.getUsername(),store1Owner.getPassword());

        StoreTestData store0 = new StoreTestData("store0Test",store0Owner);
        StoreTestData store1 = new StoreTestData("store1Test",store0Owner);
        StoreTestData store2 = new StoreTestData("store2Test",store1Owner);

        List<ProductTestData> store0Products = products.subList(0,3);
        List<ProductTestData> store1Products = products.subList(3,6);
        List<ProductTestData> store2Products = products.subList(6,10);

        store0.setProducts(store0Products);
        store1.setProducts(store1Products);
        store2.setProducts(store2Products);

        stores.addAll(Arrays.asList(store0,store1,store2));

        UserTestData notExistingStoreManager = users.get(1);
        notExistingStore = new StoreTestData("notExistingStore",
                                            notExistingStoreManager);

    }

    private static void setUpCarts() {
        List<ProductTestData> basket0Products = products.subList(0,2);
        List<ProductTestData> basket1Products = Arrays.asList(products.get(4));
        List<ProductTestData> basket2Products = Arrays.asList(products.get(9));

        BasketTestData basket0 = new BasketTestData(stores.get(0).getStoreName());
        BasketTestData basket1 = new BasketTestData(stores.get(1).getStoreName());
        BasketTestData basket2 = new BasketTestData(stores.get(2).getStoreName());

        int[] amounts0 = {12,3};
        int[] amounts1 = {7};
        int[] amounts2 = {42};

        setUpBasketProductsAndAmounts(basket0,basket0Products, amounts0);
        setUpBasketProductsAndAmounts(basket1,basket1Products, amounts1);
        setUpBasketProductsAndAmounts(basket2,basket2Products, amounts2);

        List<BasketTestData> baskets = Arrays.asList(basket0,basket1,basket2);
        superUser.getCart().addBasketsToCart(baskets);

    }

    private static void setUpBasketProductsAndAmounts(BasketTestData basket,
                                               List<ProductTestData> basketProducts,
                                               int[] amounts) {
        for (int i=0;i<amounts.length;i++)
            basket.addProductToBasket(basketProducts.get(i),amounts[i]);
    }

    private static String getPasswordByUser(String userName){
        for (UserTestData ud : users) {
            if(ud.getUsername().equals(userName))
                return ud.getPassword();
        }
        return null;
    }


    protected static void addStores(List<StoreTestData> stores){
        String userName = bridge.getCurrentLoggedInUser();
        if(userName!=null){
            bridge.logout();
        }
        bridge.login(admin.getUsername(),admin.getPassword());

        for(StoreTestData store : stores) {
            UserTestData owner = store.getStoreOwner();
            registerAndLogin(owner);
            bridge.openStore(store.getStoreName());
            bridge.appointOwnerToStore(store.getStoreName(),admin.getUsername());
        }

        bridge.logout();

        if(userName!=null){
            bridge.login(userName,getPasswordByUser(userName));
        }

    }


    protected static void addProducts(List<ProductTestData> products){
        String userName = bridge.getCurrentLoggedInUser();
        if(userName != null){
            bridge.logout();
        }
        bridge.login(admin.getUsername(),admin.getPassword());
        bridge.addProducts(products);
        bridge.logout();
        if(userName != null){
            bridge.login(userName,getPasswordByUser(userName));
        }

    }

    protected static void changeAmountOfProductInStore(ProductTestData product,int amount){
        String userName = bridge.getCurrentLoggedInUser();
        if(userName != null){
            bridge.logout();
        }
        bridge.login(admin.getUsername(),admin.getPassword());
        bridge.changeAmountOfProductInStore(product,amount);
        bridge.logout();
        if(userName != null){
            bridge.login(userName,getPasswordByUser(userName));
        }
    }


    protected static void registerAndLogin(UserTestData user){
        bridge.logout();
        String username = user.getUsername();
        String password = user.getPassword();
        bridge.register(username,password);
        bridge.login(username,password);
    }

    protected static void logoutAndLogin(UserTestData toLoginUser){
        bridge.logout();
        bridge.login(toLoginUser.getUsername(),toLoginUser.getPassword());
    }

    protected static void addUserStoresAndProducts(UserTestData user){
        registerAndLogin(user);
        addStores(stores);
        addProducts(products);
    }


    protected static void registerUsers(List<UserTestData> usersToRegister){
        for(UserTestData user : usersToRegister)
            bridge.register(user.getUsername(),user.getPassword());
    }

    protected void addCartToUser(CartTestData cart) {
        for(BasketTestData basket : cart.getBaskets())
            for(Map.Entry<ProductTestData,Integer> prodAndAmount : basket.getProductsAndAmountInBasket().entrySet())
                bridge.addToCurrentUserCart(prodAndAmount.getKey(),prodAndAmount.getValue());
    }

    @After
    public  void tearDownAll(){
        bridge.resetSystem();
    }
}
