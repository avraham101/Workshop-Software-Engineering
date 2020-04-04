package AcceptanceTests.AcceptanceTestsBridge;

import Service.ServiceAPI;

public abstract class AcceptanceTestsRealBridge implements AcceptanceTestsBridge {
    private ServiceAPI serviceAPI;

    public AcceptanceTestsRealBridge(){
        this.serviceAPI = new ServiceAPI();
    }
}
