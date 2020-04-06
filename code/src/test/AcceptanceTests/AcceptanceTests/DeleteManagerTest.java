package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.StoreTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeleteManagerTest extends AcceptanceTests{
    private UserTestData firstManager;
    private UserTestData secondManager;
    private UserTestData thirdManager;
    private List<UserTestData> managers;


    //super->first->third
    //super->second
    @Before
    public void setUp(){
        addUserStoresAndProducts(superUser);
        firstManager = users.get(1);
        secondManager = users.get(2);
        thirdManager = users.get(3);
        managers = new ArrayList<>(Arrays.asList(firstManager,secondManager,thirdManager));

        bridge.appointManager(stores.get(0).getStoreName(), firstManager.getUsername());
        bridge.appointManager(stores.get(0).getStoreName(), secondManager.getUsername());
        //TODO add premission??
        bridge.logout();
        bridge.login(firstManager.getUsername(), firstManager.getPassword());

        bridge.appointManager(stores.get(0).getStoreName(), thirdManager.getUsername());

    }

    @Test
    public void deleteManagerSuccess(){
        boolean approval = bridge.deleteManager(stores.get(0).getStoreName(), firstManager.getUsername());
        assertTrue(approval);
        StoreTestData store = bridge.getStoreInfoByName(stores.get(0).getStoreName());
        assertFalse(store.isManager(firstManager.getUsername()));
        assertFalse(store.isManager(thirdManager.getUsername()));
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
        boolean approval=bridge.deleteManager(stores.get(0).getStoreName(),"notExist");
        assertFalse(approval);
    }

    @Test
    public void deleteManagerFailNotMyAppointment(){
        boolean approval = bridge.deleteManager(stores.get(0).getStoreName(),secondManager.getUsername());
        assertFalse(approval);
    }

    @After
    public void tearDown(){

        deleteStores(stores);//also delete the manager
        bridge.logout(firstManager.getUsername());
    }
}
