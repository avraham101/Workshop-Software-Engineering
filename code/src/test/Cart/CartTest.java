package Cart;

import Data.Data;
import Data.TestData;
import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import DataAPI.ProductData;
import Domain.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CartTest {

    private Domain.Cart cart;
    private TestData testData;

    @Before
    public void setUp(){
        cart = new Cart();
        testData = new TestData();
    }

    /**-------------------------set-ups------------------------------*/

    /**
     *  prepare product in the cart
     */
    private void setUpProductAdded(){
        Store store = testData.getRealStore(Data.VALID);
        Product product = testData.getRealProduct(Data.VALID);
        cart.addProduct(store,product,product.getAmount());
    }

    /**-------------------------set-ups------------------------------*/

    /**
     * use case 2.7 - add product to cart
     */
    @Test
    public void testAddProduct() {
        Store store = testData.getRealStore(Data.VALID);
        Product product = testData.getRealProduct(Data.VALID);
        assertTrue(cart.addProduct(store,product,product.getAmount()));
    }

    /**
     * use case 2.8 - buy
     */
    @Test
    public void testBuy() {
        setUpProductAdded();
        PaymentData paymentData = testData.getPaymentData(Data.VALID);
        DeliveryData deliveryData = testData.getDeliveryData(Data.VALID);
        List<Purchase> purchases = cart.buy(paymentData,deliveryData.getAddress());
        assertEquals(1, purchases.size());
        Purchase purchase = purchases.get(0);
        assertEquals(paymentData.getName(), purchase.getBuyer());
        List<ProductData> products =  purchase.getProduct();
        assertEquals(1, products.size());
        ProductData result = products.get(0);
        ProductData expected = deliveryData.getProducts().get(0);
        assertEquals(expected.getProductName(),result.getProductName());
        assertEquals(expected.getAmount(), result.getAmount());
    }
}
