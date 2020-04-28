package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestsBridge.AcceptanceTestsBridge;
import AcceptanceTests.AcceptanceTestsBridge.AcceptanceTestsProxyBridge;
import AcceptanceTests.AcceptanceTestsBridge.AcceptanceTestsRealBridge;

public class AcceptanceTestsDriver {
    public static AcceptanceTestsBridge getBridge() {
        AcceptanceTestsProxyBridge bridge = new AcceptanceTestsProxyBridge();
        bridge.setRealBridge(new AcceptanceTestsRealBridge());
        return bridge;
    }
}
