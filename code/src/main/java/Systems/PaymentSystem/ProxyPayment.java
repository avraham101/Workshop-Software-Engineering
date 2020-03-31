package Systems.PaymentSystem;

import DataAPI.PaymentData;

public class ProxyPayment extends PaymentSystem{

    private ExternalPayment realPayment;

    @Override
    public boolean connect() {
        if(realPayment!=null)
            realPayment.connect();
        return true;
    }

    @Override
    public boolean pay(PaymentData paymentData) {
        if(realPayment!=null)
            realPayment.connect();
        return true;
    }

    @Override
    public boolean cancel(PaymentData paymentData) {
        if(realPayment!=null)
            realPayment.connect();
        return true;
    }

}
