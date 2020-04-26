package Domain.PurchasePolicy;

import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import Domain.Product;

import java.util.HashMap;

public interface PurchasePolicy {

    public boolean standInPolicy(PaymentData paymentData, DeliveryData deliveryData);

}
