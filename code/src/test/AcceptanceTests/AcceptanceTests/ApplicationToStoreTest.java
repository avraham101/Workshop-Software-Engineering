package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.ApplicationToStoreTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * use case 3.5 - write application to store
 */

public class ApplicationToStoreTest extends AcceptanceTests {
    private ApplicationToStoreTestData application;
    private UserTestData user;

    @Before
    public void setUp(){
        addStores(stores);
        user = users.get(0);
        registerAndLogin(user);
        application = new ApplicationToStoreTestData(stores.get(0).getStoreName(), user.getUsername(),"message");
    }

    @Test
    public void applicationToStoreTestSuccess(){
        boolean approval= bridge.sendApplicationToStore(application.getStoreName(),application.getContent());
        assertTrue(approval);
        registerAndLogin(superUser);
        List<ApplicationToStoreTestData> applications = bridge.getUserApplications(user.getUsername(),application.getStoreName());
        assertTrue(applications.contains(application));
    }

    @Test
    public void applicationToStoreFailInvalidStoreName(){
        application.setStoreName("Invalid");
        boolean approval= bridge.sendApplicationToStore(application.getStoreName(),application.getContent());
        assertFalse(approval);
        List<ApplicationToStoreTestData> applications = bridge.getUserApplications(user.getUsername(),application.getStoreName());
        assertFalse(applications.contains(application));
    }

    @Test
    public void applicationToStoreFailNotSubscribed(){
        bridge.logout(user.getId());
        boolean approval= bridge.sendApplicationToStore(application.getStoreName(),application.getContent());
        assertFalse(approval);
        List<ApplicationToStoreTestData> applications = bridge.getUserApplications(user.getUsername(),application.getStoreName());
        assertFalse(applications.contains(application));
    }
}
