package AcceptanceTests.SystemMocks;

import DataAPI.DeliveryData;
import Systems.SupplySystem.SupplySystem;

public class DeliverySystemMock extends SupplySystem {

    @Override
    public boolean connect() {
        return false;
    }

    @Override
    public boolean deliver(DeliveryData deliveryData) {
        return false;
    }

    @Override
    public boolean isConnected() {
        return false;
    }
}
