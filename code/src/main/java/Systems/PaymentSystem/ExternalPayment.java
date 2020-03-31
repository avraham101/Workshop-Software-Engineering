package Systems.PaymentSystem;

import DataAPI.PaymentData;

public class ExternalPayment extends PaymentSystem{
    @Override
    public boolean connect() {
        return false;
    }

    @Override
    public boolean pay(PaymentData paymentData) {
        return false;
    }

    @Override
    public boolean cancel(PaymentData paymentData) {
        return false;
    }

}
