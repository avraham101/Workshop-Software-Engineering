package Systems.PaymentSystem;

import DataAPI.PaymentData;

public abstract class PaymentSystem {

    public abstract boolean connect();

    /**
     * use case 2.8 - purchase cart
     * @param paymentData
     * @return
     */
    public abstract boolean pay(PaymentData paymentData);

    /**
     * use case 2.8 - purchase cart
     * the function cancel a payment
     * @param paymentData - the info
     * @return if the cancel succeed, otherwise false
     */
    public abstract boolean cancel(PaymentData paymentData);

}
