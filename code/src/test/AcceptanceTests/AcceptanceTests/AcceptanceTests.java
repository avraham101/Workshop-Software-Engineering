package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.ProductTestData;
import AcceptanceTests.AcceptanceTestDataObjects.ReviewTestData;
import AcceptanceTests.AcceptanceTestDataObjects.StoreTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import AcceptanceTests.AcceptanceTestsBridge.AcceptanceTestsBridge;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AcceptanceTests extends TestCase{
    protected AcceptanceTestsBridge bridge;
    protected List<UserTestData> users;
    protected List<StoreTestData> stores;
    protected List<ProductTestData> products;

    public void setUp(){
        this.bridge = AcceptanceTestsDriver.getBridge();
        this.users = new ArrayList<>();
        this.stores = new ArrayList<>();
        this.products = new ArrayList<>();

        setUpUsers();
        setUpProducts();
        setUpStores();
    }

    private void setUpUsers() {
        UserTestData user0 = new UserTestData("testUser0","testUser0Pass");
        UserTestData user1 = new UserTestData("testUser1","testUser1Pass");
        UserTestData user2 = new UserTestData("testUser2","testUser2Pass");

        users.addAll(Arrays.asList(user0, user1,user2));
    }

    private void setUpProducts(){
        ProductTestData prod0 = new ProductTestData("appleTest",
                                                    "",
                                                    30,
                                                    1.5,
                                                    "Fruits",
                                                    new ArrayList<ReviewTestData>());
        ProductTestData prod1 = new ProductTestData("milkTest",
                                                    "",
                                                    100,
                                                    5.79,
                                                    "Dairy",
                                                    new ArrayList<ReviewTestData>());
        ProductTestData prod2 = new ProductTestData("burekasTest",
                                                    "",
                                                    90,
                                                    2,
                                                    "Pastries",
                                                    new ArrayList<ReviewTestData>());
        ProductTestData prod3 = new ProductTestData("cocacolaTest",
                                                    "",
                                                    200,
                                                    6,
                                                    "Sodas",
                                                    new ArrayList<ReviewTestData>());
        ProductTestData prod4 = new ProductTestData("waterTest",
                                                    "",
                                                    500,
                                                    3,
                                                    "Sodas",
                                                    new ArrayList<ReviewTestData>());
        ProductTestData prod5 = new ProductTestData("milkiTest",
                                                    "",
                                                    52,
                                                    4,
                                                    "Dairy",
                                                    new ArrayList<ReviewTestData>());
        ProductTestData prod6 = new ProductTestData("tomatoTest",
                                                    "",
                                                    140,
                                                    3.5,
                                                    "Vegetables",
                                                    new ArrayList<ReviewTestData>());
        ProductTestData prod7 = new ProductTestData("onionTest",
                                                    "",
                                                    100,
                                                    1.5,
                                                    "Vegetables",
                                                    new ArrayList<ReviewTestData>());
        ProductTestData prod8 = new ProductTestData("bambaTest",
                                                    "",
                                                    100,
                                                    3.80,
                                                    "Snacks",
                                                    new ArrayList<ReviewTestData>());
        ProductTestData prod9 = new ProductTestData("cheeseTest",
                                                    "",
                                                    500,
                                                    4.5,
                                                    "Dairy",
                                                    new ArrayList<ReviewTestData>());

        products.addAll(Arrays.asList(prod0, prod1,prod2,
                                        prod3,prod4,prod5,
                                        prod6,prod7,prod8,prod9));

    }

    private void setUpStores(){
        List<ProductTestData> store0Products = products.subList(0,3);
        List<ProductTestData> store1Products = products.subList(3,6);
        List<ProductTestData> store2Products = products.subList(6,10);

        setUpStoreNameToProducts(store0Products,stores.get(0).getStoreName());
        setUpStoreNameToProducts(store0Products,stores.get(1).getStoreName());
        setUpStoreNameToProducts(store0Products,stores.get(2).getStoreName());

        UserTestData store0Manager = users.get(0);
        UserTestData store1Manager = users.get(1);
        UserTestData store2Manager = users.get(2);

        StoreTestData store0 = new StoreTestData("store0Test",store0Manager,store0Products);
        StoreTestData store1 = new StoreTestData("store1Test",store1Manager,store1Products);
        StoreTestData store2 = new StoreTestData("store2Test",store2Manager,store2Products);

        stores.addAll(Arrays.asList(store0,store1,store2));

    }

    private void setUpStoreNameToProducts(List<ProductTestData> storeProducts, String storeName) {
        for (ProductTestData product : storeProducts)
            product.setStoreName(storeName);
    }
}
