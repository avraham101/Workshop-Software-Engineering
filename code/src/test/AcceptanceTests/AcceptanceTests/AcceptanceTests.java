package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.UserData;
import AcceptanceTests.AcceptanceTestsBridge.AcceptanceTestsBridge;
import junit.framework.TestCase;
import java.util.ArrayList;
import java.util.List;

public abstract class AcceptanceTests extends TestCase{
    protected AcceptanceTestsBridge bridge;
    protected List<UserData> users;

    public void setUp(){
        this.bridge = AcceptanceTestsDriver.getBridge();
        this.users = new ArrayList<>();
        setUpUsers();
    }

    private void setUpUsers() {
        UserData user1 = new UserData("Roy","123");
        UserData user2 = new UserData("Tal","456");
        users.add(user1);
        users.add(user2);
    }
}
