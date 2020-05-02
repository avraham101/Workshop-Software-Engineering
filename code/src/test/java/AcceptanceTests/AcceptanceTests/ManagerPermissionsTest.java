package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 5.1 - manager permission operations
 */
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
                                        new ArrayList<ReviewTestData>());

    }

    private void setUpManagerPermissionsTestManagers(){
        manager = users.get(1);
        newManager = users.get(2);

        registerAndLogin(superUser);
        registerAndLogin(manager);
        registerAndLogin(newManager);
        logoutAndLogin(superUser);
        bridge.appointManager(superUser.getId(),stores.get(0).getStoreName(), manager.getUsername());
        bridge.addPermissionToManager(superUser.getId(),stores.get(0).getStoreName(),manager.getUsername(), PermissionsTypeTestData.PRODUCTS_INVENTORY);
       logoutAndLogin(manager);
    }
    @Test
    public void managerPermissionsTestSuccess(){
        boolean isAdded = bridge.addProduct(manager.getId(),newProduct);
        assertTrue(isAdded);
        HashSet<ProductTestData> actualProducts = new HashSet<>(bridge.getStoreProducts(newProduct.getStoreName()));
        List<ProductTestData> expectedProductsList = products.subList(0,3);
        expectedProductsList.add(newProduct);
        HashSet<ProductTestData> expectedProducts = new HashSet<>(expectedProductsList);
        assertEquals(expectedProducts,actualProducts);
    }

    @Test
    public void managerPermissionsTestFailAddNewManager(){
        boolean isAdded = bridge.appointManager(manager.getId(),newProduct.getStoreName(),newManager.getUsername());
        assertFalse(isAdded);
        logoutAndLogin(newManager);
        boolean isAddedProduct = bridge.addProduct(newManager.getId(),newProduct);
        assertFalse(isAddedProduct);
    }

    @Test
    public void managerPermissionsTestFailAddNewOwner(){
        boolean isAdded = bridge.appointOwnerToStore(manager.getId(),
                newProduct.getStoreName(),newManager.getUsername());
        assertFalse(isAdded);
    }


    @Test
    public void getManagersOfStoreSuccess(){
        List<String> managers=bridge.getAllManagersOfStore(newProduct.getStoreName());
        List<String> expectedManagers=new ArrayList<>();
        expectedManagers.add(superUser.getUsername());
        expectedManagers.add(admin.getUsername());
        expectedManagers.add(users.get(1).getUsername());
        assertEquals(managers,expectedManagers);
    }

    @Test
    public void getManagersOfStoreNotExistStore(){
        assertNull(bridge.getAllManagersOfStore(newProduct.getProductName()));
    }


    @Test
    public void getManagersOfStoreIManagedStoreSuccess(){
        List<String> managers=bridge.getManagersOfStoreIManaged(superUser.getId(),newProduct.getStoreName());
        List<String> expectedManagers=new ArrayList<>();
        expectedManagers.add(admin.getUsername());
        expectedManagers.add(users.get(1).getUsername());
        assertEquals(managers,expectedManagers);
    }

    @Test
    public void getManagersOfStoreIManagedNotExistStore(){
        assertNull(bridge.getManagersOfStoreIManaged(superUser.getId(),newProduct.getProductName()));
    }

}
