package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.PermissionsTypeTestData;
import AcceptanceTests.AcceptanceTestDataObjects.ProductTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class ManagerPermissionsTest extends AcceptanceTests {
    private UserTestData manager;
    private UserTestData newManager;
    private ProductTestData newProduct;

    @Before
    public void setUp(){
        addStores(stores);
        addProducts(products);
        setUpManagerPermissionsTestManagers();
        newProduct = new ProductTestData("newProductTest",
                                        "store0Test",
                                        100,
                                        6,
                                        "Dairy",
                                        new ArrayList<>(),new ArrayList<>());

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
        HashSet<ProductTestData> actualProducts = new HashSet<>(bridge.getStoreProducts(newProduct.getStoreName()));
        List<ProductTestData> expectedProductsList = products.subList(0,3);
        expectedProductsList.add(newProduct);
        HashSet<ProductTestData> expectedProducts = new HashSet<>(expectedProductsList);
        assertEquals(expectedProducts,actualProducts);
    }

    @Test
    public void managerPermissionsTestFailAddNewManager(){
        boolean isAdded = bridge.appointManager(newProduct.getStoreName(),newManager.getUsername());
        assertFalse(isAdded);
        logoutAndLogin(newManager);
        boolean isAddedProduct = bridge.addProduct(newProduct);
        assertFalse(isAddedProduct);
    }

    @Test
    public void managerPermissionsTestFailAddNewOwner(){
        boolean isAdded = bridge.appointOwnerToStore(newProduct.getStoreName(),newManager.getUsername());
        assertFalse(isAdded);
    }
}
