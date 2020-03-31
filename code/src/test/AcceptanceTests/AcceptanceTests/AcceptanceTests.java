package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestsBridge.AcceptanceTestsBridge;
import junit.framework.TestCase;

public abstract class AcceptanceTests extends TestCase{
    private AcceptanceTestsBridge acceptanceTestsBridge;

    public void setUp(){
        this.acceptanceTestsBridge = AcceptanceTestsDriver.getBridge();
    }
}
