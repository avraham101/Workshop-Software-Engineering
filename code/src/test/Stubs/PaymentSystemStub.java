package Stubs;

import DataAPI.PaymentData;
import Systems.PaymentSystem.PaymentSystem;

public class PaymentSystemStub extends PaymentSystem {

    @Override
    public boolean connect() {
        return false;
    }

    /**
     * use case 2.8 - purchase cart
     * @return
     */
    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public boolean pay(PaymentData paymentData) {
        return false;
    }

    /**
     * use case 2.8 - purchase cart
     * @param paymentData - the info
     * @return
     */
    @Override
    public boolean cancel(PaymentData paymentData) {
        return false;
    }
}
