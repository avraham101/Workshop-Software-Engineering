package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.ApplicationToStoreTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import AcceptanceTests.SystemMocks.PublisherMock;
import javafx.util.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.event.annotation.AfterTestClass;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * 4.9 - view and replay user's applications
 */
public class ViewAndReplyApplicationToStore extends AcceptanceTests {

    private HashSet<ApplicationToStoreTestData> applications;
    private HashMap<ApplicationToStoreTestData,String> applicationsAndReplies;
    private Pair<ApplicationToStoreTestData, String> wrongApplication;
    private String storeName;
    private UserTestData asker;
    private UserTestData responder;

    @Before
    public void setUp() {
        responder = superUser;
        asker = users.get(1);
        storeName = stores.get(0).getStoreName();
        addStores(stores);

        registerAndLogin(responder);
        registerAndLogin(asker);
        setUpApplicationsAndReplies();
        //registerAndLogin(responder);
    }

    private void setUpApplicationsAndReplies() {
        applications = new HashSet<>();
        applicationsAndReplies = new HashMap<>();

        applications.add(new ApplicationToStoreTestData(storeName,asker.getUsername(),"app0"));
        applications.add(new ApplicationToStoreTestData(storeName,asker.getUsername(),"app1"));

        int i = 0;
        for(ApplicationToStoreTestData app : applications) {
            bridge.sendApplicationToStore(asker.getId(),storeName, app.getContent());
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
        HashSet<ApplicationToStoreTestData> actualApplications = bridge.viewApplicationToStore(responder.getId(),storeName);
        assertEquals(applications,actualApplications);
    }

    @Test
    public void viewApplicationToStoreTestFailNotLoggedIn(){
        bridge.logout(responder.getId());
        HashSet<ApplicationToStoreTestData> actualApplications = bridge.viewApplicationToStore(responder.getId(), storeName);
        assertFalse(actualApplications.size() != 0);
    }

    @Test
    public void viewApplicationToStoreTestFailWrongStore(){
        HashSet<ApplicationToStoreTestData> actualApplications = bridge.viewApplicationToStore(responder.getId(),storeName + storeName);
        assertFalse(actualApplications.size() != 0);
    }

    /**
     * 4.9.2 - reply to user's applications
     */

    @Test
    public void replyApplicationToStoreTestSuccess(){
        PublisherMock publisherMock=new PublisherMock();
        bridge.setPublisher(publisherMock);
     //   HashSet<ApplicationToStoreTestData> applications= bridge.viewApplicationToStore(responder.getId(),storeName);
        HashMap<ApplicationToStoreTestData,String> emptyAppAndRep = bridge.getUserApplicationsAndReplies(responder.getId(),asker.getUsername(),storeName);
        for(ApplicationToStoreTestData app : applications)
            assertFalse(emptyAppAndRep.containsKey(app));

        int requestId = 1;
        for(Map.Entry<ApplicationToStoreTestData,String> appAndRep : applicationsAndReplies.entrySet()) {
            boolean isWritten = bridge.writeReplyToApplication(responder.getId(),requestId,storeName,appAndRep.getKey(),appAndRep.getValue());
            assertTrue(isWritten);
            requestId++;
        }
        HashMap<ApplicationToStoreTestData,String> actualAppAndRep = bridge.getUserApplicationsAndReplies(responder.getId(), asker.getUsername(),storeName);
        assertEquals(applicationsAndReplies,actualAppAndRep);
        //check notification
        assertFalse(publisherMock.getNotificationList().isEmpty());

    }

    @Test
    public void replyApplicationToStoreTestFailNotLoggedIn(){
        bridge.logout(responder.getId());

        HashMap<ApplicationToStoreTestData,String> emptyAppAndRep = bridge.getUserApplicationsAndReplies(responder.getId(), asker.getUsername(),storeName);
        assertTrue(emptyAppAndRep.size()==0);

        int requestId = 1;
        for(Map.Entry<ApplicationToStoreTestData,String> appAndRep : applicationsAndReplies.entrySet()) {
            boolean isWritten = bridge.writeReplyToApplication(responder.getId(),requestId,storeName, appAndRep.getKey(),appAndRep.getValue());
            assertFalse(isWritten);
            requestId++;
        }
    }

    @Test
    public void replyApplicationToStoreTestFailWrongStore(){
        int requestId = 1;
        for(Map.Entry<ApplicationToStoreTestData,String> appAndRep : applicationsAndReplies.entrySet()) {
            boolean isWritten = bridge.writeReplyToApplication(responder.getId(),requestId,storeName + storeName,appAndRep.getKey(),appAndRep.getValue());
            assertFalse(isWritten);
            requestId++;
        }

        HashMap<ApplicationToStoreTestData,String> emptyAppAndRep = bridge.getUserApplicationsAndReplies(responder.getId(), asker.getUsername(),storeName + storeName);
        for(ApplicationToStoreTestData app : applications)
            assertFalse(emptyAppAndRep.containsKey(app));
    }

    @Test
    public void replyApplicationToStoreTestFailWrongApplication(){
        int requestId = 10;
        boolean isWritten = bridge.writeReplyToApplication(responder.getId(),requestId,storeName, wrongApplication.getKey(), wrongApplication.getValue());
        assertFalse(isWritten);
        HashMap<ApplicationToStoreTestData,String> emptyAppAndRep = bridge.getUserApplicationsAndReplies(responder.getId(), asker.getUsername(),storeName + storeName);
        assertFalse(emptyAppAndRep.containsKey(wrongApplication.getKey()));
    }

    @After
    public void tearDown() {
        tearDownApplicationsAndReplies();
        removeUser(asker.getUsername());
        removeUser(responder.getUsername());
        removeStores(stores);
    }

    private void tearDownApplicationsAndReplies() {
        applications=new HashSet<>();
        applicationsAndReplies=new HashMap<>();
    }
}
