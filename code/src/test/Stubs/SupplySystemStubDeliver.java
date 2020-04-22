package Stubs;

import DataAPI.DeliveryData;
import Systems.SupplySystem.SupplySystem;

public class SupplySystemStubDeliver extends SupplySystem {

    @Override
    public boolean connect() {
        return true;
    }

    @Override
    public boolean deliver(DeliveryData deliveryData) {
        return false;
    }

}
