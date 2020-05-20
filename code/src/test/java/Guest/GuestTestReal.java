package Guest;

import Data.Data;
import Data.TestData;
import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import Domain.*;
import Domain.PurchasePolicy.BasketPurchasePolicy;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.junit.Assert.*;

public class GuestTestReal extends GuestTest{

    @Override
    @Before
    public void setUp(){
        data = new TestData();
        cart = new Cart("Guest");
        guest = new Domain.Guest(cart);
    }

    /**------------set-ups-------------------*/

    /**
     * add valid product to the cart
     */
    private void setUpProductAddedToCart(){
        Store store = data.getRealStore(Data.VALID);
        Product product = data.getRealProduct(Data.VALID);
        guest.addProductToCart(store,product,product.getAmount());
    }

    /**------------set-ups-------------------*/

    /**
     * use case 2.7 add to cart
     */
    @Override @Test
    public void testAddProductToCart() {
        super.testAddProductToCart();
        Store store = data.getRealStore(Data.VALID);
        Product product = data.getRealProduct(Data.VALID);
        Map<String, ProductInCart> products = cart.getBasket(store.getName()).getProducts();
        assertEquals(1,products.size());
        Iterator<ProductInCart> iterator =  products.values().iterator();
        ProductInCart real = iterator.next();
        assertEquals(real.getProductName(),product.getName());
    }

    /**
     * use case - 2.8 reserveCart cart
     */
    @Override @Test
    public void testReservedCart() {
        setUpReserved();
        assertTrue(guest.reserveCart());
    }

    /**
     * use case - 2.8 reserveCart cart
     */
    @Test
    public void testCancelCart() {
        setUpReserved();
        int expected = amountProductInStore();
        guest.reserveCart();
        guest.cancelCart();
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
     * use case 2.8 - buy cart save test
     */
    @Test
    public void testSavePurchase() {
        setUpSave();
        Store store = null;
        int storeExpected = 0;
        for(Basket basket: guest.getCart().getBaskets().values()) {
            store = basket.getStore();
            storeExpected = store.getPurchases().size() + 1;
            break;
        }
        guest.savePurchase(guest.getName());
        assertEquals(storeExpected, store.getPurchases().size());
        assertTrue(guest.getCart().getBaskets().isEmpty());
    }

    /**
     * use case - 2.8 buy cart
     */
    @Test
    public void testBuyCart(){
        setUpBuy();
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID2);
        guest.getCart().getBaskets().get(data.getStore(Data.VALID).getName()).getStore().setPurchasePolicy(
                new BasketPurchasePolicy(0));
        assertFalse(guest.buyCart(paymentData,deliveryData));
        assertEquals(0,paymentData.getTotalPrice(),0.001);
        assertTrue(deliveryData.getProducts().isEmpty());
    }


    @Test
    public void testGetMyManagedStoresNoStoresSuccess(){

        assertNull(guest.getMyManagedStores());
    }

    @Test
    public void testGetPermissionsForStoreSuccess(){
        assertNull(guest.getPermissionsForStore(data.getStore(Data.VALID).getName()));
    }

}
