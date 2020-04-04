package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.StoreTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DeleteManagerTest extends AcceptanceTests{
    UserTestData firstManager;
    UserTestData secondManager;
    UserTestData thirdManager;
    @Before
    //super->first->third
    //super->second
    public void setUp(){
        super.setUp();
        bridge.login(superUser.getUsername(),superUser.getPassword());
        addStores(stores);
        firstManager = users.get(1);
        secondManager = users.get(2);
        thirdManager = users.get(3);
        bridge.appointManager(stores.get(0).getStoreName(), firstManager.getUsername());
        bridge.appointManager(stores.get(0).getStoreName(), secondManager.getUsername());
        //TODO add premission??
        bridge.logout(superUser.getUsername());
        bridge.login(firstManager.getUsername(), firstManager.getPassword());

        bridge.appointManager(stores.get(0).getStoreName(), thirdManager.getUsername());

    }

    @Test
    public void deleteManagerSuccess(){
        boolean approval=bridge.deleteManager(stores.get(0).getStoreName(), firstManager.getUsername());
        assertTrue(approval);
        StoreTestData store = bridge.getStoreInfoByName(stores.get(0).getStoreName());
        assertFalse(store.isManager(firstManager.getUsername()));
        assertFalse(store.isManager(thirdManager.getUsername()));
    }

    @Test
    public void deleteManagerFailNotMyStore(){
        boolean approval=bridge.deleteManager(stores.get(2).getStoreName(), firstManager.getUsername());
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
