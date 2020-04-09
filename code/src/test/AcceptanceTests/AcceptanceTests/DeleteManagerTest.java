package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;


public class DeleteManagerTest extends AcceptanceTests{
    private UserTestData firstManager;
    private UserTestData secondManager;
    private UserTestData thirdManager;
    private List<UserTestData> managers;
    private ProductTestData productToAdd;


    //super->first->third
    //super->second
    @Before
    public void setUp(){
        firstManager = users.get(1);
        secondManager = users.get(2);
        thirdManager = users.get(3);
        managers = new ArrayList<>(Arrays.asList(firstManager,secondManager,thirdManager));
        registerUsers(managers);
        addUserStoresAndProducts(superUser);
        bridge.appointManager(stores.get(0).getStoreName(), firstManager.getUsername());
        bridge.appointManager(stores.get(0).getStoreName(), secondManager.getUsername());
        bridge.addPermissionToManager(stores.get(0).getStoreName(),
                                    firstManager.getUsername(),
                                    PermissionsTypeTestData.ADD_MANAGER);

        bridge.logout();
        bridge.login(firstManager.getUsername(), firstManager.getPassword());
        bridge.appointManager(stores.get(0).getStoreName(), thirdManager.getUsername());
        bridge.logout();
        bridge.login(superUser.getUsername(),superUser.getPassword());

        productToAdd = new ProductTestData("newProductTest",
                                            stores.get(0).getStoreName(),
                                            100,
                                            4,
                                            "Dairy",
                                            new ArrayList<ReviewTestData>(),
                                            new ArrayList<DiscountTestData>());

    }

    @Test
    public void deleteManagerSuccess(){
        boolean approval = bridge.deleteManager(stores.get(0).getStoreName(), firstManager.getUsername());
        assertTrue(approval);
        logoutAndLogin(firstManager);
        boolean isManager = bridge.addProduct(productToAdd);
        assertFalse(isManager);
        logoutAndLogin(thirdManager);
        isManager = bridge.addProduct(productToAdd);
        assertFalse(isManager);
    }

    @Test
    public void deleteManagerFailNotMyStore(){
        boolean approval = bridge.deleteManager(stores.get(2).getStoreName(), firstManager.getUsername());
        assertFalse(approval);
    }

    @Test
    public void deleteManagerFailInvalidStore(){
        boolean approval=bridge.deleteManager("not-exist", firstManager.getUsername());
        assertFalse(approval);
    }

    @Test
    public void deleteManagerFailInvalidManager(){
        boolean approval = bridge.deleteManager(stores.get(0).getStoreName(),"notExist");
        assertFalse(approval);
    }

    @Test
    public void deleteManagerFailNotMyAppointment(){
        bridge.logout();
        bridge.login(firstManager.getUsername(),firstManager.getPassword());
        boolean approval = bridge.deleteManager(stores.get(0).getStoreName(),secondManager.getUsername());
        assertFalse(approval);
    }
}
