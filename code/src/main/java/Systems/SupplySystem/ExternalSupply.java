package Systems.SupplySystem;

import DataAPI.DeliveryData;

public class ExternalSupply extends SupplySystem {

    @Override
    public boolean connect() {
        return false;
    }

    @Override
    public boolean delivry(DeliveryData deliveryData) {
        return false;
    }
}
