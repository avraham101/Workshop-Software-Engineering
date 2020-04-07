package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestsBridge.AcceptanceTestsBridge;
import AcceptanceTests.AcceptanceTestsBridge.AcceptanceTestsProxyBridge;

public class AcceptanceTestsDriver {
    public static AcceptanceTestsBridge getBridge() {
        AcceptanceTestsProxyBridge bridge = new AcceptanceTestsProxyBridge();
        //bridge.setRealBridge(null);
        return bridge;
    }
}
