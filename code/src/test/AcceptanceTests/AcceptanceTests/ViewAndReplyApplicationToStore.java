package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.ApplicationToStoreTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import javafx.util.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 4.9 - view and replay user's applications
 */
public class ViewAndReplyApplicationToStore extends AcceptanceTests {

    private HashSet<ApplicationToStoreTestData> applications;
    private HashMap<ApplicationToStoreTestData,String> applicationsAndReplies;
    private Pair<ApplicationToStoreTestData, String> wrongApplication;
    private String storeName;
    private UserTestData asker;

    @Before
    public void setUp() {
        UserTestData responder = superUser;
        asker = users.get(1);
        storeName = stores.get(0).getStoreName();
        addStores(stores);

        registerAndLogin(responder);
        registerAndLogin(asker);
        setUpApplicationsAndReplies();
        registerAndLogin(responder);
    }

    private void setUpApplicationsAndReplies() {
        this.applications = new HashSet<>();
        this.applicationsAndReplies = new HashMap<>();

        applications.add(new ApplicationToStoreTestData(storeName,asker.getUsername(),"app0"));
        applications.add(new ApplicationToStoreTestData(storeName,asker.getUsername(),"app1"));

        int i = 0;
        for(ApplicationToStoreTestData app : applications) {
            bridge.sendApplicationToStore(storeName, app.getContent());
            applicationsAndReplies.put(app, "rep" + i);
            i++;
        }

        ApplicationToStoreTestData wrongApp = new ApplicationToStoreTestData(storeName, asker.getUsername() + asker.getUsername(), "wrongApp");
        wrongApplication = new Pair<>(wrongApp,"wrongRep");
    }

    /**
     * use case 4.9.1 - view user's application to store
     */

    @Test
    public void viewApplicationToStoreTestSuccess(){
        HashSet<ApplicationToStoreTestData> actualApplications = bridge.viewApplicationToStore(storeName);
        assertEquals(applications,actualApplications);
    }

    @Test
    public void viewApplicationToStoreTestFailNotLoggedIn(){
        bridge.logout();
        HashSet<ApplicationToStoreTestData> actualApplications = bridge.viewApplicationToStore(storeName);
        assertFalse(actualApplications.size() != 0);
    }

    @Test
    public void viewApplicationToStoreTestFailWrongStore(){
        HashSet<ApplicationToStoreTestData> actualApplications = bridge.viewApplicationToStore(storeName + storeName);
        assertFalse(actualApplications.size() != 0);
    }

    /**
     * 4.9.2 - reply to user's applications
     */

    @Test
    public void replyApplicationToStoreTestSuccess(){
        HashMap<ApplicationToStoreTestData,String> emptyAppAndRep = bridge.getUserApplicationsAndReplies(asker.getUsername(),storeName);
        for(ApplicationToStoreTestData app : applications)
            assertFalse(emptyAppAndRep.containsKey(app));

        int requestId = 1;
        for(Map.Entry<ApplicationToStoreTestData,String> appAndRep : applicationsAndReplies.entrySet()) {
            boolean isWritten = bridge.writeReplyToApplication(requestId,storeName,appAndRep.getKey(),appAndRep.getValue());
            assertTrue(isWritten);
            requestId++;
        }
        HashMap<ApplicationToStoreTestData,String> actualAppAndRep = bridge.getUserApplicationsAndReplies(asker.getUsername(),storeName);
        assertEquals(applicationsAndReplies,actualAppAndRep);
    }

    @Test
    public void replyApplicationToStoreTestFailNotLoggedIn(){
        bridge.logout();

        HashMap<ApplicationToStoreTestData,String> emptyAppAndRep = bridge.getUserApplicationsAndReplies(asker.getUsername(),storeName);
        assertTrue(emptyAppAndRep.size()==0);

        int requestId = 1;
        for(Map.Entry<ApplicationToStoreTestData,String> appAndRep : applicationsAndReplies.entrySet()) {
            boolean isWritten = bridge.writeReplyToApplication(requestId,storeName, appAndRep.getKey(),appAndRep.getValue());
            assertFalse(isWritten);
            requestId++;
        }
    }

    @Test
    public void replyApplicationToStoreTestFailWrongStore(){
        int requestId = 1;
        for(Map.Entry<ApplicationToStoreTestData,String> appAndRep : applicationsAndReplies.entrySet()) {
            boolean isWritten = bridge.writeReplyToApplication(requestId,storeName + storeName,appAndRep.getKey(),appAndRep.getValue());
            assertFalse(isWritten);
            requestId++;
        }

        HashMap<ApplicationToStoreTestData,String> emptyAppAndRep = bridge.getUserApplicationsAndReplies(asker.getUsername(),storeName + storeName);
        for(ApplicationToStoreTestData app : applications)
            assertFalse(emptyAppAndRep.containsKey(app));
    }

    @Test
    public void replyApplicationToStoreTestFailWrongApplication(){
        int requestId = 10;
        boolean isWritten = bridge.writeReplyToApplication(requestId,storeName, wrongApplication.getKey(), wrongApplication.getValue());
        assertFalse(isWritten);
        HashMap<ApplicationToStoreTestData,String> emptyAppAndRep = bridge.getUserApplicationsAndReplies(asker.getUsername(),storeName + storeName);
        assertFalse(emptyAppAndRep.containsKey(wrongApplication.getKey()));
    }

}
