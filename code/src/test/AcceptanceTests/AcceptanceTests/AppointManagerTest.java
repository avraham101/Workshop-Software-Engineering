package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class AppointManagerTest extends AcceptanceTests  {
    UserTestData userToAdd;
    @Before
    public void setUp(){
        List<UserTestData> userLst = new ArrayList<>();
        userToAdd = users.get(2);
        userLst.add(userToAdd);
        registerUsers(userLst);

        registerAndLogin(superUser);
        addStores(stores);
    }

    @Test
    public void appointManagerSuccess() {
        StoreTestData store = stores.get(0);
        boolean approval = bridge.appointManager(store.getStoreName(), userToAdd.getUsername());
        assertTrue(approval);
    }

    @Test
    public void appointMangerFailManagerAlreadyExist(){
        StoreTestData store = stores.get(0);
        bridge.appointManager(store.getStoreName(),userToAdd.getUsername());
        boolean approval = bridge.appointManager(store.getStoreName(),userToAdd.getUsername());
        assertFalse(approval);

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

        boolean approval = bridge.appointManager(store.getStoreName(),userToAdd.getUsername());
        assertFalse(approval);
    }
}
