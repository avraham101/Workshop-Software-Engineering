package AcceptanceTests.SystemMocks;

import DataAPI.DeliveryData;
import Systems.SupplySystem.SupplySystem;

public class DeliverySystemMockCantDeliver extends SupplySystem{
    @Override
    public boolean connect() {
        return true;
    }

    @Override
    public boolean deliver(DeliveryData deliveryData) {
        return false;
    }

    @Override
    public boolean isConnected() {
        return true;
    }
}
