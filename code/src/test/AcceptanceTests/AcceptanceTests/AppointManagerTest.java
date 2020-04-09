package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.ProductTestData;
import AcceptanceTests.AcceptanceTestDataObjects.StoreTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class AppointManagerTest extends AcceptanceTests  {
    private ProductTestData productToAdd;
    @Before
    public void setUp(){

        registerAndLogin(superUser);
        productToAdd = new ProductTestData("appointManagerProductTest",
                                                            stores.get(0).getStoreName(),
                                                            100,
                                                            10,
                                                            "Dairy",
                                                            new ArrayList<>(),
                                                            new ArrayList<>());
    }

    @Test
    public void appointManagerSuccess(){
        StoreTestData store = stores.get(0);
        UserTestData newManager = users.get(2);
        boolean approval = bridge.appointManager(store.getStoreName(),newManager.getUsername());
        assertTrue(approval);
        logoutAndLogin(newManager);
        boolean isManager = bridge.addProduct(productToAdd);
        assertTrue(isManager);
    }

    @Test
    public void appointMangerFailManagerAlreadyExist(){
        StoreTestData store = stores.get(0);
        UserTestData newManager = users.get(2);
        bridge.appointManager(store.getStoreName(),newManager.getUsername());
        boolean approval = bridge.appointManager(store.getStoreName(),newManager.getUsername());
        assertFalse(approval);
        logoutAndLogin(newManager);
        boolean isManager = bridge.addProduct(productToAdd);
        assertTrue(isManager);
    }

    @Test
    public void appointManagerFailInvalidUserName(){
        StoreTestData store = stores.get(0);
        boolean approval = bridge.appointManager(store.getStoreName(),"guest");
        assertFalse(approval);
    }

    @Test
    public void appointManagerFailNotMyStore(){
        StoreTestData store = stores.get(2);
        UserTestData newManager = users.get(2);
        boolean approval = bridge.appointManager(store.getStoreName(),newManager.getUsername());
        assertFalse(approval);
    }
}
