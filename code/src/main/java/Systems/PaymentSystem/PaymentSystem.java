package Systems.PaymentSystem;

import DataAPI.PaymentData;

public abstract class PaymentSystem {

    public abstract boolean connect();

    public abstract boolean pay(PaymentData paymentData);

    public abstract boolean cancel(PaymentData paymentData);

}
