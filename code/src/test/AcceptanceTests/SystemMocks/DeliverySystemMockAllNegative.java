package AcceptanceTests.SystemMocks;

import DataAPI.DeliveryData;
import Systems.SupplySystem.SupplySystem;

public class DeliverySystemMockAllNegative extends SupplySystem {

    @Override
    public boolean connect() {
        return false;
    }

    @Override
    public boolean deliver(DeliveryData deliveryData) {
        return false;
    }
}
