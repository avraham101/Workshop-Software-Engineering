package LogicManagerTests;

import Domain.Admin;
import Domain.LogicManager;
import org.junit.Before;

import java.util.HashMap;

import static org.junit.Assert.assertTrue;

public class LogicManagerUserStubTest extends LogicManagerUserAndStoresStubs {

    @Before
    public void setUp() {
        currUser=new UserStub();
        users=new HashMap<>();
        stores=new HashMap<>();
        logicManager = new LogicManager(users,stores,currUser);
        logicManager.register("Admin","Admin");
    }
}
