package Systems.PaymentSystem;

import DataAPI.PaymentData;

public class ProxyPayment extends PaymentSystem{

    private ExternalPayment realPayment;

    public ProxyPayment() {
        realPayment=new ExternalPayment();
    }

    public void setRealPayment(ExternalPayment realPayment) {
        this.realPayment = realPayment;
    }

    @Override
    public boolean connect() {
        if(realPayment!=null)
            return realPayment.connect();
        return true;
    }

    @Override
    public boolean pay(PaymentData paymentData) {
        if(realPayment!=null)
            return realPayment.pay(paymentData);
        return true;
    }

    @Override
    public boolean cancel(PaymentData paymentData) {
        if(realPayment!=null)
            return realPayment.cancel(paymentData);
        return true;
    }

}
