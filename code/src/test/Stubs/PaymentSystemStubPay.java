package Stubs;

import DataAPI.PaymentData;
import Systems.PaymentSystem.PaymentSystem;

public class PaymentSystemStubPay extends PaymentSystem {

    @Override
    public boolean connect() {
        return true;
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
