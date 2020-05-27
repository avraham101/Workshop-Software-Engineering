package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.*;
import AcceptanceTests.AcceptanceTestsBridge.AcceptanceTestsBridge;
import AcceptanceTests.SystemMocks.DeliverySystemMockAllPositive;
import AcceptanceTests.SystemMocks.PaymentSystemMockAllPositive;
import Systems.PaymentSystem.PaymentSystem;
import Systems.SupplySystem.SupplySystem;
import org.junit.After;
import org.junit.Before;

import java.util.*;

public class AcceptanceTests {
    protected  AcceptanceTestsBridge bridge;
    protected  List<UserTestData> users;
    protected  List<StoreTestData> stores;
    protected  List<ProductTestData> products;
    protected  StoreTestData notExistingStore;
    protected  DeliveryDetailsTestData validDelivery;
    protected  DeliveryDetailsTestData invalidDelivery;
    protected  PaymentTestData validPayment;
    protected  PaymentTestData invalidPayment;
    protected  UserTestData superUser;
    protected  UserTestData admin;
    private PaymentSystem paymentSystem;
    private SupplySystem supplySystem;

    public AcceptanceTests(){
        this.paymentSystem = new PaymentSystemMockAllPositive();
        this.supplySystem = new DeliverySystemMockAllPositive();
    }

    @Before
    public  void setUpAll(){
        bridge = AcceptanceTestsDriver.getBridge();
        users = new ArrayList<>();
        stores = new ArrayList<>();
        products = new ArrayList<>();

        initSystem();
        setUpUsers();
        setUpDelivery();
        setUpPayments();
        setUpProducts();
        setUpStores();
        setUpCarts();
        setUpPayments();
        setUpDelivery();

    }


    private  void setUpPayments() {
        Date validDate = new Date();
        validPayment = new PaymentTestData("ownerValid","1111","343",validDate);
        invalidPayment = null;
    }

    private  void setUpDelivery() {
        validDelivery = new DeliveryDetailsTestData
                ("israel","ashdod","valid",7);
        invalidDelivery =null;
    }

    private void initSystem(){
        bridge.initialStart("admin","admin",paymentSystem,supplySystem);
    }

    protected   void setUpUsers() {

        admin = new UserTestData(generateUserId(),"admin","admin");
        UserTestData user0 = new UserTestData(generateUserId(),"testUser0","testUser0Pass");
        UserTestData user1 = new UserTestData(generateUserId(),"testUser1","testUser1Pass");
        UserTestData user2 = new UserTestData(generateUserId(),"testUser2","testUser2Pass");
        UserTestData user3 = new UserTestData(generateUserId(),"testUser3","testUser3Pass");
        superUser = user0;
        users.addAll(Arrays.asList(user0, user1,user2,user3));

    }
    protected  int generateUserId(){
        return bridge.connect();
    }

    private  void setUpProducts(){
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
                                                    new ArrayList<ReviewTestData>()
        );

        products.addAll(Arrays.asList(prod0, prod1,prod2,
                                        prod3,prod4,prod5,
                                        prod6,prod7,prod8,prod9));

    }

    private  void setUpStores(){

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

    private  void setUpCarts() {
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

    private void setUpBasketProductsAndAmounts(BasketTestData basket,
                                               List<ProductTestData> basketProducts,
                                               int[] amounts) {
        for (int i=0;i<amounts.length;i++)
            basket.addProductToBasket(basketProducts.get(i),amounts[i]);
    }

    private  String getPasswordByUser(String userName){
        for (UserTestData ud : users) {
            if(ud.getUsername().equals(userName))
                return ud.getPassword();
        }
        return null;
    }


    protected  void addStores(List<StoreTestData> stores){
        bridge.register(admin.getUsername(),admin.getPassword());
        bridge.login(admin.getId(),admin.getUsername(),admin.getPassword());

        for(StoreTestData store : stores) {
            UserTestData owner = store.getStoreOwner();
            registerAndLogin(owner);
            bridge.openStore(owner.getId(),store.getStoreName());
            bridge.appointOwnerToStore(owner.getId(),store.getStoreName(),admin.getUsername());
            bridge.logout(owner.getId());
        }

        bridge.logout(admin.getId());
    }


    protected  void addProducts(List<ProductTestData> products){

        bridge.login(admin.getId(),admin.getUsername(),admin.getPassword());
        bridge.addProducts(admin.getId(),products);
        bridge.logout(admin.getId());


    }

    protected  void changeAmountOfProductInStore(ProductTestData product,int amount){
        bridge.login(admin.getId(),admin.getUsername(),admin.getPassword());
        bridge.changeAmountOfProductInStore(admin.getId(),product,amount);
        bridge.logout(admin.getId());

    }
    protected void deleteProductFromStore(ProductTestData product){
        bridge.login(admin.getId(),admin.getUsername(),admin.getPassword());
        bridge.deleteProduct(admin.getId(),product);
        bridge.logout(admin.getId());
    }


    protected  void registerAndLogin(UserTestData user){
        int id = user.getId();
        String username = user.getUsername();
        String password = user.getPassword();
        bridge.register(username,password);
        bridge.login(id,username,password);
    }

    protected  void logoutAndLogin(UserTestData toLoginUser){
        bridge.login(toLoginUser.getId(),toLoginUser.getUsername(),toLoginUser.getPassword());
    }

    protected  void addUserStoresAndProducts(UserTestData user){

        addStores(stores);
        addProducts(products);
        registerAndLogin(user);
    }
    protected  void addUserAndStores(UserTestData user){
        registerAndLogin(user);
        addStores(stores);

    }


    protected  void registerUsers(List<UserTestData> usersToRegister){
        for(UserTestData user : usersToRegister)
            bridge.register(user.getUsername(),user.getPassword());
    }

    protected void addCartToUser(int id, CartTestData cart) {
        for(BasketTestData basket : cart.getBaskets())
            for(Map.Entry<ProductTestData,Integer> prodAndAmount : basket.getProductsAndAmountInBasket().entrySet())
                bridge.addToUserCart(id,prodAndAmount.getKey(),prodAndAmount.getValue());
    }

    protected void setExternalSystems(PaymentSystem paymentSystem, SupplySystem supplySystem){
        this.paymentSystem = paymentSystem;
        this.supplySystem = supplySystem;
    }

    protected void removeUser(String username){
        this.bridge.removeUser(username);
    }
    protected void removeUsers(List<String> users){
        for(String user : users)
            removeUser(user);
    }


    protected void removeProduct(ProductTestData product){
        this.bridge.removeProduct(product);
    }

    protected void removeProducts(List<ProductTestData> products){
        for(ProductTestData product : products)
            removeProduct(product);
    }

    protected void removeStore(StoreTestData store){
        this.bridge.removeStore(store);
    }

    protected void removeStores(List<StoreTestData> stores){
        for(StoreTestData store : stores)
            removeStore(store);
    }

    public void removeUserStoresAndProducts(UserTestData user){
        //removeProducts(products);
        removeStores(stores);
        removeUser(user.getUsername());
    }

    @After
    public  void tearDownAll(){
        removeUsers(Arrays.asList(admin.getUsername(),users.get(0).getUsername(),users.get(1).getUsername(),users.get(3).getUsername()));
        bridge.resetSystem();
    }
}
