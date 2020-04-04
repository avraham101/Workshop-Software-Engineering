import Data.Data;
import Data.TestData;
import DataAPI.PaymentData;
import DataAPI.ProductData;
import Domain.*;
import Systems.PaymentSystem.ProxyPayment;
import Systems.SupplySystem.ProxySupply;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import static org.junit.Assert.*;

/**
 * test functions of the class Basket
 */
public class BasketTest {

    private TestData data;
    private Basket basket;

    @Before
    public void setUp() {
        data = new TestData();
        Store store = new Store(data.getStore(Data.VALID).getName(), null, null
                , new Permission(data.getSubscribe(Data.VALID)), new ProxySupply(),new ProxyPayment());
        basket = new Basket(store);
    }

    @Test
    public void testBasket() {
        teatAddToBasket();
        testEditAmountFromBasket();
        testDeleteFromBasket();
        teatAddToBasket();
        testIfBasketAvailableToBuy();
    }

    /**
     * test edit amount of product in a basket
     */
    private void testEditAmountFromBasket() {
        HashMap<ProductData, Integer> products = data.getProductsInBasket(Data.VALID);
        for (ProductData productData: products.keySet()) {
            String productName = productData.getProductName();
            assertTrue(basket.editAmount(productName,5));
        }
        assertFalse(basket.editAmount(null,5));
    }

    /**
     * test add product in a basket
     */
    private void teatAddToBasket() {
        HashMap<ProductData, Integer> products = data.getProductsInBasket(Data.VALID);
        for (ProductData productData: products.keySet()) {
            Product product = new Product(productData,new Category(""));
            assertTrue(basket.addProduct(product, 10));
        }
    }

    /**
     * test delete product from a basket
     */
    private void testDeleteFromBasket() {
        HashMap<ProductData, Integer> products = data.getProductsInBasket(Data.VALID);
        for (ProductData productData: products.keySet()) {
            Product product = new Product(productData,new Category(""));
            assertTrue(basket.deleteProduct(product.getName()));
        }
    }

    /**
     * test if the basket is available for buying
     */
    private void testIfBasketAvailableToBuy() {
        testIfBasketAvailableToBuySuccess();
        testIfBasketAvailableToBuyFails();
    }

    /**
     * test if the basket is available for buying
     * fail test
     */
    private void testIfBasketAvailableToBuyFails() {
        // null data payment
        assertFalse(basket.available(null, null));
        // null address in payment
        PaymentData paymentData = data.getPaymentData(Data.NULL_ADDRESS);
        String address = data.getDeliveryData(Data.VALID).getAddress();
        assertFalse(basket.available(paymentData, address));
        // empty address in payment
        paymentData = data.getPaymentData(Data.EMPTY_ADDRESS);
        address = data.getDeliveryData(Data.VALID).getAddress();
        assertFalse(basket.available(paymentData, address));
        // null payment
        paymentData = data.getPaymentData(Data.NULL_PAYMENT);
        address = data.getDeliveryData(Data.VALID).getAddress();
        assertFalse(basket.available(paymentData, address));
        // empty payment
        paymentData = data.getPaymentData(Data.EMPTY_PAYMENT);
        address = data.getDeliveryData(Data.VALID).getAddress();
        assertFalse(basket.available(paymentData, address));
        // null name in payment
        paymentData = data.getPaymentData(Data.NULL_NAME);
        address = data.getDeliveryData(Data.VALID).getAddress();
        assertFalse(basket.available(paymentData, address));
        // empty name in payment
        paymentData = data.getPaymentData(Data.EMPTY_NAME);
        address = data.getDeliveryData(Data.VALID).getAddress();
        assertFalse(basket.available(paymentData, address));
        // null address
        paymentData = data.getPaymentData(Data.VALID);
        address = data.getDeliveryData(Data.NULL_ADDRESS).getAddress();
        assertFalse(basket.available(paymentData, address));
        // empty address
        paymentData = data.getPaymentData(Data.VALID);
        address = data.getDeliveryData(Data.EMPTY_ADDRESS).getAddress();
        assertFalse(basket.available(paymentData, address));
    }

    private void testIfBasketAvailableToBuySuccess() {

    }

}