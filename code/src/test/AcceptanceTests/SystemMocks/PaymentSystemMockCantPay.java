package AcceptanceTests.SystemMocks;

import DataAPI.PaymentData;
import Systems.PaymentSystem.PaymentSystem;

public class PaymentSystemMockCantPay extends PaymentSystem {
    @Override
    public boolean connect() {
        return true;
    }

    @Override
    public boolean isConnected() {
        return true;
    }

    @Override
    public boolean pay(PaymentData paymentData) {
        return false;
    }

    @Override
    public boolean cancel(PaymentData paymentData) {
        return true;
    }
}
