package AcceptanceTests.SystemMocks;

import DataAPI.PaymentData;
import Systems.PaymentSystem.PaymentSystem;

public class PaymenSystemMock extends PaymentSystem {
    @Override
    public boolean connect() {
        return false;
    }

    @Override
    public boolean isConnected() {
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
