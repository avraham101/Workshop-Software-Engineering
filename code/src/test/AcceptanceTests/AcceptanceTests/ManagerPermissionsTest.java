package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.ProductTestData;
import AcceptanceTests.AcceptanceTestDataObjects.ReviewTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

public class ManagerPermissionsTest extends AcceptanceTests {
    private UserTestData manager;
    private UserTestData newManager;
    private ProductTestData newProduct;

    @Before
    public void setUp(){
        super.setUp();
        manager = users.get(1);
        newManager = users.get(2);

        bridge.register(superUser.getUsername(),superUser.getPassword());
        bridge.register(manager.getUsername(),manager.getPassword());
        bridge.register(newManager.getUsername(),newManager.getPassword());
        bridge.login(superUser.getUsername(),superUser.getPassword());
        bridge.appointManager(stores.get(0).getStoreName(), manager.getUsername());
        bridge.logout(superUser.getUsername());
        bridge.login(manager.getUsername(),manager.getPassword());

        newProduct = new ProductTestData("newProductTest",
                                        "store0Test",
                                        100,
                                        6,
                                        "Dairy",
                                        new ArrayList<ReviewTestData>());
        addStores(stores);
        addProducts(products);
    }


    @Test
    public void managerPermissionsTestSuccess(){
        boolean isAdded = bridge.addProduct(newProduct);
        assertTrue(isAdded);
        products.add(newProduct);
        HashSet<ProductTestData> actualProducts = new HashSet<>(bridge.getStoreProducts(newProduct.getStoreName()));
        HashSet<ProductTestData> expectedProducts = new HashSet<>(products);
        assertEquals(expectedProducts,actualProducts);
    }

    @Test
    public void managerPermissionsTestFailAddNewManager(){
        boolean isAdded = bridge.appointManager(newProduct.getStoreName(),newManager.getUsername());
        assertFalse(isAdded);
        boolean isManager = bridge.getStoreInfoByName(newProduct.getStoreName()).isManager(newManager.getUsername());
        assertFalse(isManager);
    }

    @Test
    public void managerPermissionsTestFailAddNewOwner(){
        boolean isAdded = bridge.appointOwnerToStore(newProduct.getStoreName(),newManager.getUsername());
        assertFalse(isAdded);
        boolean isOwner = bridge.getStoreInfoByName(newProduct.getStoreName()).isOwner(newManager.getUsername());
        assertFalse(isOwner);
    }

    @After
    public void tearDown(){
        bridge.logout(manager.getUsername());
        bridge.login(superUser.getUsername(),superUser.getPassword());
        deleteProducts(products);
        deleteStores(stores);
        deleteUser(newManager.getUsername());
        deleteUser(manager.getUsername());
        deleteUser(superUser.getUsername());
    }
}
