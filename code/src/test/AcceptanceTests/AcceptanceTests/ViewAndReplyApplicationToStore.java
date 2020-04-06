package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.ApplicationToStoreTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import javafx.util.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ViewAndReplyApplicationToStore extends AcceptanceTests {

    private HashSet<ApplicationToStoreTestData> applications;
    private HashMap<ApplicationToStoreTestData,String> applicationsAndReplies;
    private Pair<ApplicationToStoreTestData, String> wrongApplication;
    private String storeName;
    private UserTestData responder;
    private UserTestData asker;

    @Before
    public void setUp() {
        super.setUp();
        responder  = superUser;
        asker = users.get(1);
        storeName = stores.get(0).getStoreName();
        bridge.register(responder.getUsername(),responder.getPassword());
        bridge.register(asker.getUsername(),asker.getPassword());
        bridge.login(responder.getUsername(),responder.getPassword());
        addStores(stores);
        bridge.logout(responder.getUsername());
        setUpApplicationsAndReplies();
        bridge.login(responder.getUsername(),responder.getPassword());
    }

    private void setUpApplicationsAndReplies() {
        bridge.login(asker.getUsername(),asker.getPassword());
        this.applications = new HashSet<>();
        this.applicationsAndReplies = new HashMap<>();

        applications.add(new ApplicationToStoreTestData(storeName,asker.getUsername(),"app0"));
        applications.add(new ApplicationToStoreTestData(storeName,asker.getUsername(),"app1"));

        int i = 0;
        for(ApplicationToStoreTestData app : applications) {
            bridge.sendApplicationToStore(storeName, app.getContent());
            applicationsAndReplies.put(app, "rep" + i);
        }

        ApplicationToStoreTestData wrongApp = new ApplicationToStoreTestData(storeName, asker.getUsername() + asker.getUsername(), "wrongApp");
        String wrongRep = "wrongRep";
        wrongApplication = new Pair<>(wrongApp,wrongRep);

        bridge.logout(asker.getUsername());
    }

    @Test
    public void viewApplicationToStoreTestSuccess(){
        HashSet<ApplicationToStoreTestData> actualApplications = bridge.viewApplicationToStore(storeName);
        assertEquals(applications,actualApplications);
    }

    @Test
    public void viewApplicationToStoreTestFailNotLoggedIn(){
        bridge.logout(responder.getUsername());
        HashSet<ApplicationToStoreTestData> actualApplications = bridge.viewApplicationToStore(storeName);
        assertNull(actualApplications);
    }

    @Test
    public void viewApplicationToStoreTestFailWrongStore(){
        HashSet<ApplicationToStoreTestData> actualApplications = bridge.viewApplicationToStore(storeName + storeName);
        assertNull(actualApplications);
    }

    @Test
    public void replyApplicationToStoreTestSuccess(){
        HashMap<ApplicationToStoreTestData,String> emptyAppAndRep = bridge.getUserApplicationsAndReplies(asker.getUsername());
        for(ApplicationToStoreTestData app : applications)
            assertFalse(emptyAppAndRep.containsKey(app));

        for(Map.Entry<ApplicationToStoreTestData,String> appAndRep : applicationsAndReplies.entrySet()) {
            boolean isWritten = bridge.writeReplyToApplication(storeName,appAndRep.getKey(),appAndRep.getValue());
            assertTrue(isWritten);
        }
        HashMap<ApplicationToStoreTestData,String> actualAppAndRep = bridge.getUserApplicationsAndReplies(asker.getUsername());
        assertEquals(applicationsAndReplies,actualAppAndRep);
    }

    @Test
    public void replyApplicationToStoreTestFailNotLoggedIn(){
        bridge.logout(responder.getUsername());

        HashMap<ApplicationToStoreTestData,String> emptyAppAndRep = bridge.getUserApplicationsAndReplies(asker.getUsername());
        assertNull(emptyAppAndRep);

        for(Map.Entry<ApplicationToStoreTestData,String> appAndRep : applicationsAndReplies.entrySet()) {
            boolean isWritten = bridge.writeReplyToApplication(storeName, appAndRep.getKey(),appAndRep.getValue());
            assertFalse(isWritten);
        }
    }

    @Test
    public void replyApplicationToStoreTestFailWrongStore(){
        HashMap<ApplicationToStoreTestData,String> emptyAppAndRep = bridge.getUserApplicationsAndReplies(asker.getUsername());
        for(ApplicationToStoreTestData app : applications)
            assertFalse(emptyAppAndRep.containsKey(app));

        for(Map.Entry<ApplicationToStoreTestData,String> appAndRep : applicationsAndReplies.entrySet()) {
            boolean isWritten = bridge.writeReplyToApplication(storeName + storeName,appAndRep.getKey(),appAndRep.getValue());
            assertFalse(isWritten);
        }
    }

    @Test
    public void replyApplicationToStoreTestFailWrongApplication(){
        boolean isWritten = bridge.writeReplyToApplication(storeName, wrongApplication.getKey(), wrongApplication.getValue());
        assertFalse(isWritten);
    }

    @After
    public void tearDown(){
        deleteStores(stores);
        deleteUser(asker.getUsername());
        deleteUser(responder.getUsername());
    }
}
