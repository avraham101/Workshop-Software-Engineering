package AcceptanceTests.SystemMocks;

import DataAPI.DeliveryData;
import Systems.SupplySystem.SupplySystem;

public class DeliverySystemMockAllPositive extends SupplySystem {
    @Override
    public boolean connect() {
        return true;
    }

    @Override
    public boolean deliver(DeliveryData deliveryData) {
        return true;
    }

    @Override
    public boolean isConnected() {
        return true;
    }
}
