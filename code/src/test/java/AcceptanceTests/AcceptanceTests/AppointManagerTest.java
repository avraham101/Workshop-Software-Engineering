package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.StoreTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * use case 4.5 - appoint manager
 */

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
        boolean approval = bridge.appointManager(superUser.getId(),store.getStoreName(), userToAdd.getUsername());
        assertTrue(approval);
    }

    @Test
    public void appointMangerFailManagerAlreadyExist(){
        StoreTestData store = stores.get(0);
        bridge.appointManager(superUser.getId(),store.getStoreName(),userToAdd.getUsername());
        boolean approval = bridge.appointManager(superUser.getId(),store.getStoreName(),userToAdd.getUsername());
        assertFalse(approval);

    }

    @Test
    public void appointManagerFailInvalidUserName(){
        StoreTestData store = stores.get(0);
        boolean approval = bridge.appointManager(superUser.getId(),store.getStoreName(),"guest");
        assertFalse(approval);
    }

    @Test
    public void appointManagerFailNotMyStore(){
        StoreTestData store = stores.get(2);

        boolean approval = bridge.appointManager(superUser.getId(),store.getStoreName(),userToAdd.getUsername());
        assertFalse(approval);
    }
}
