package Stubs;

import DataAPI.PaymentData;
import Systems.PaymentSystem.PaymentSystem;

public class PaymentSystemStub extends PaymentSystem {

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
