package Cart;

import Data.Data;
import Data.TestData;
import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import Domain.*;
import Domain.PurchasePolicy.BasketPurchasePolicy;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

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

    /**
     * set up for But Cart
     */
    private void setUpBuy() {
        setUpProductAdded();
        cart.reserveCart();
    }

    /**
     * set up for But Cart
     */
    private void setUpSave() {
        setUpProductAdded();
        cart.reserveCart();
        PaymentData paymentData = testData.getPaymentData(Data.VALID);
        DeliveryData deliveryData = testData.getDeliveryData(Data.VALID);
        cart.buy(paymentData, deliveryData);
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
     * use case 2.8 - reserveCart
     */
    @Test
    public void testReservedCart() {
        setUpProductAdded();
        assertTrue(cart.reserveCart());
    }

    /**
     * use case 2.8 - reserveCart
     */
    @Test
    public void testBuyCartSuccess() {
        setUpBuy();
        int size = 0;
        double sum =0;
        for(Basket b:cart.getBaskets().values()) {
            HashMap<Product,Integer> products = b.getProducts();
            for(Product p:products.keySet()) {
                int amount = products.get(p);
                sum += amount * p.getPrice();
                size++;
            }
        }
        PaymentData paymentData = testData.getPaymentData(Data.VALID);
        DeliveryData deliveryData = testData.getDeliveryData(Data.VALID2);
        assertTrue(cart.buy(paymentData, deliveryData));
        assertEquals(sum,paymentData.getTotalPrice(),0.001);
        assertEquals(size,deliveryData.getProducts().size());
    }

    /**
     * 2.8 -buy cart
     * buy cart fail because one basket policy fails
     */
    @Test
    public void testBuyCartPolicyFail(){
        setUpBuy();
        cart.getBaskets().get(testData.getStore(Data.VALID).getName()).getStore().setPurchasePolicy(
                new BasketPurchasePolicy(0));
        PaymentData paymentData = testData.getPaymentData(Data.VALID);
        DeliveryData deliveryData = testData.getDeliveryData(Data.VALID2);
        assertFalse(cart.buy(paymentData,deliveryData));
        assertTrue(deliveryData.getProducts().isEmpty());
        assertEquals(paymentData.getTotalPrice(),0,0.001);
    }

    /**
     * use case 2.8 - reserved Cart Empty
     */
    @Test
    public void testReservedEmptyCart() {
        assertFalse(cart.reserveCart());
    }

    /**
     * use case 2.8 - reserveCart
     */
    @Test
    public void testCancelCart() {
        setUpProductAdded();
        int expected = amountProductInStore();
        cart.reserveCart();
        cart.cancel();
        int result = amountProductInStore();
        assertEquals(expected,result);
    }

    /**
     * use case 2.8
     * help function for getting the amount
     * @return
     */
    private int amountProductInStore() {
        Store store = null;
        for(Basket b: cart.getBaskets().values()) {
            store = b.getStore();
            break;
        }
        assertNotNull(store);
        Product product = null;
        for(Product p :store.getProducts().values()) {
            product = p;
            break;
        }
        assertNotNull(product);
        return product.getAmount();
    }

    /**
     * use case 2.8 - reserveCart
     */
    @Test
    public void testSavePurchases() {
        setUpSave();
        int size = cart.getBaskets().size();
        String name = testData.getSubscribe(Data.VALID).getName();
        List<Purchase> list = cart.savePurchases(name);
        assertNotNull(list);
        assertEquals(size, list.size());
    }

}
