package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.PermissionsTypeTestData;
import AcceptanceTests.AcceptanceTestDataObjects.ProductTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class ManagerPermissionsTest extends AcceptanceTests {
    private UserTestData manager;
    private UserTestData newManager;
    private ProductTestData newProduct;

    @Before
    public void setUp(){

        setUpManagerPermissionsTestManagers();
        newProduct = new ProductTestData("newProductTest",
                                        "store0Test",
                                        100,
                                        6,
                                        "Dairy",
                                        new ArrayList<>());
        addStores(stores);
        addProducts(products);
    }

    private void setUpManagerPermissionsTestManagers(){
        manager = users.get(1);
        newManager = users.get(2);

        registerAndLogin(superUser);
        registerAndLogin(manager);
        registerAndLogin(newManager);
        logoutAndLogin(superUser);
        bridge.appointManager(stores.get(0).getStoreName(), manager.getUsername());
        bridge.addPermissionToManager(stores.get(0).getStoreName(),manager.getUsername(), PermissionsTypeTestData.PRODUCTS_INVENTORY);
       logoutAndLogin(manager);
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
        deleteProducts(products);
        deleteStores(stores);
        deleteUsers(Arrays.asList(newManager,manager,superUser));
    }
}
