package Systems.SupplySystem;

import DataAPI.DeliveryData;

public class ProxySupply extends SupplySystem{

    private ExternalSupply real;

    public ProxySupply() {
        real=new ExternalSupply();
    }

    public void setReal(ExternalSupply real) {
        this.real = real;
    }

    @Override
    public boolean connect() {
        if(real!=null) {
            return real.connect();
        }
        return true;
    }

    @Override
    public boolean deliver(DeliveryData deliveryData) {
        if(real!=null) {
            return real.deliver(deliveryData);
        }
        return true;
    }

}
