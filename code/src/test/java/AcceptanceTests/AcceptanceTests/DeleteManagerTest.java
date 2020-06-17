package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.PermissionsTypeTestData;
import AcceptanceTests.AcceptanceTestDataObjects.PurchaseTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import AcceptanceTests.SystemMocks.PublisherMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.*;

/**
 * use case 4.7 - delete manager
 */

public class DeleteManagerTest extends AcceptanceTests{
    private UserTestData firstManager;
    private UserTestData secondManager;
    private UserTestData thirdManager;
    private List<UserTestData> managers;
    private List<String> managersNames;

    //super->first->third
    //super->second
    @Before
    public void setUp(){
        firstManager = users.get(1);
        secondManager = users.get(2);
        thirdManager = users.get(3);
        managers = new ArrayList<>(Arrays.asList(firstManager, secondManager, thirdManager));
        managersNames = new ArrayList<>();
        for(UserTestData manager: managers)
            managersNames.add(manager.getUsername());
        registerUsers(managers);
        addUserStoresAndProducts(superUser);
        bridge.appointManager(superUser.getId(),stores.get(0).getStoreName(), firstManager.getUsername());
        bridge.appointManager(superUser.getId(),stores.get(0).getStoreName(), secondManager.getUsername());
        bridge.addPermissionToManager(superUser.getId(),stores.get(0).getStoreName(),
                                    firstManager.getUsername(),
                                    PermissionsTypeTestData.ADD_MANAGER);

        bridge.logout(superUser.getId());
        bridge.login(firstManager.getId(),firstManager.getUsername(), firstManager.getPassword());
        bridge.appointManager(firstManager.getId(),stores.get(0).getStoreName(), thirdManager.getUsername());
        bridge.logout(firstManager.getId());
        bridge.login(superUser.getId(),superUser.getUsername(),superUser.getPassword());

        bridge.login(firstManager.getId(),firstManager.getUsername(),firstManager.getPassword());
        bridge.login(secondManager.getId(),secondManager.getUsername(),secondManager.getPassword());
        bridge.login(thirdManager.getId(),thirdManager.getUsername(),thirdManager.getPassword());

    }

    @Test
    public void deleteManagerSuccess(){
        PublisherMock publisherMock=new PublisherMock();
        bridge.setPublisher(publisherMock);
        logoutAndLogin(firstManager);
        logoutAndLogin(thirdManager);
        boolean approval = bridge.deleteManager(superUser.getId(),stores.get(0).getStoreName(), firstManager.getUsername());
        assertTrue(approval);

        List<PurchaseTestData> isManager = bridge.getStorePurchasesHistory(firstManager.getId(),
                stores.get(0).getStoreName());
        assertNull(isManager);

        isManager = bridge.getStorePurchasesHistory(thirdManager.getId(),
                stores.get(0).getStoreName());
        assertNull(isManager);

        //check notification
        assertFalse(publisherMock.getNotificationList().isEmpty());
    }

    @Test
    public void deleteManagerFailNotMyStore(){
        boolean approval = bridge.deleteManager(superUser.getId(),stores.get(2).getStoreName(), firstManager.getUsername());
        assertFalse(approval);
    }

    @Test
    public void deleteManagerFailInvalidStore(){
        boolean approval=bridge.deleteManager(superUser.getId(),"not-exist", firstManager.getUsername());
        assertFalse(approval);
    }

    @Test
    public void deleteManagerFailInvalidManager(){
        boolean approval = bridge.deleteManager(superUser.getId(),stores.get(0).getStoreName(),"notExist");
        assertFalse(approval);
    }

    @Test
    public void deleteManagerFailNotMyAppointment(){
        boolean approval = bridge.deleteManager(firstManager.getId(),stores.get(0).getStoreName(),secondManager.getUsername());
        assertFalse(approval);
    }

    @After
    public void tearDown(){
        removeProducts(products);
        removeStores(stores);
        removeUsers(managersNames);
    }
}
