package Stubs;

import DataAPI.DeliveryData;
import Systems.SupplySystem.SupplySystem;

public class SupplySystemStub extends SupplySystem {

    /**
     * use case 1.1
     * the function connect to the external systems
     * @return
     */
    @Override
    public boolean connect() {
        return false;
    }

    /**
     * use case 2.8 - purchase cart
     * the function supply the Data
     * @param deliveryData - the data of the deliver
     * @return
     */
    @Override
    public boolean deliver(DeliveryData deliveryData) {
        return false;
    }

}
