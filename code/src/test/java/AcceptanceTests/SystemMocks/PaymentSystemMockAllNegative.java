package AcceptanceTests.SystemMocks;

import DataAPI.PaymentData;
import Systems.PaymentSystem.PaymentSystem;

public class PaymentSystemMockAllNegative extends PaymentSystem {
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
