package Systems.PaymentSystem;

import DataAPI.PaymentData;

public class ProxyPayment extends PaymentSystem{

    private ExternalPayment realPayment;

    public ProxyPayment() {

    }

    public void setRealPayment(ExternalPayment realPayment) {
        this.realPayment = realPayment;
    }

    @Override
    public boolean connect() {
        if(realPayment!=null)
            realPayment.connect();
        return true;
    }

    @Override
    public boolean isConnected() {
        if(realPayment!=null)
            return realPayment.isConnected();
        return true;
    }

    @Override
    public boolean pay(PaymentData paymentData) {
        if(realPayment!=null)
            realPayment.pay(paymentData);
        return true;
    }

    @Override
    public boolean cancel(PaymentData paymentData) {
        if(realPayment!=null)
            realPayment.cancel(paymentData);
        return true;
    }

}
