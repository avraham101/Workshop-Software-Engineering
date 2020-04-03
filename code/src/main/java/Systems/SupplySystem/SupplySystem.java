package Systems.SupplySystem;

import DataAPI.DeliveryData;

public abstract class SupplySystem {

    /**
     * use case 1.1
     * the function connect to the external systems
     * @return true if the connection establish, otherwise false.
     */
    public abstract boolean connect();

    /**
     * use case 2.8 - purchase cart
     * the function supply the Data
     * @param deliveryData - the data of the deliver
     * @return true if the supply happen, otherwise false
     */
    public abstract boolean deliver(DeliveryData deliveryData);

    /**
     * use case 2.8 - purchase cart
     * the function check if the system is on
     * @return true if the system is connceted, otherwise false
     */
    public abstract boolean isConncted();

}
