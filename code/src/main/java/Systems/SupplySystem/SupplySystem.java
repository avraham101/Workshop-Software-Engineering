package Systems.SupplySystem;

import DataAPI.DeliveryData;

public abstract class SupplySystem {

    public abstract boolean connect();

    public abstract boolean delivry(DeliveryData deliveryData);

}
