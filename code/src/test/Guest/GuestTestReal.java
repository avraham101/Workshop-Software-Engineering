package Guest;

import Data.Data;
import Data.TestData;
import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import Domain.Basket;
import Domain.Cart;
import Domain.Product;
import Domain.Store;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;

import static org.junit.Assert.*;

public class GuestTestReal extends GuestTest{

    @Override
    @Before
    public void setUp(){
        data = new TestData();
        cart = new Cart();
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
        HashMap<Product,Integer> products = cart.getBasket(store.getName()).getProducts();
        assertEquals(1,products.size());
        Iterator<Product> iterator =  products.keySet().iterator();
        Product real = iterator.next();
        assertEquals(real.getName(),product.getName());
    }

    /**
     * use case 2.8 - purchase cart
     */
    @Test
    public void testBuyCart() {
        setUpReservedCart();
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
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID);
        guest.buyCart(paymentData,deliveryData);
        assertEquals(sum,paymentData.getTotalPrice(),0.001);
        assertEquals(size,deliveryData.getProducts().size());
    }

    /**
     * use case 2.8 - purchase cart
     */
    @Test
    public void testSavePurchase() {
        setUpReservedCart();
        int number =  user.getState().getCart().getBaskets().keySet().size();
        String name = data.getSubscribe(Data.VALID).getName();
        user.savePurchase(name);
        assertEquals(number, user.watchMyPurchaseHistory().size());
    }

    /**
     * use case 2.8 - purchase cart
     */
    @Test
    public void testCancel() {
        setUpProductAddedToCart();
        int expected = amountProductInStore();
        user.reservedCart();
        user.cancelCart();
        int result = amountProductInStore();
        assertEquals(expected,result);
    }

    /**
     * use case 2.8
     * help function for getting the amount
     * @return
     */
    private int amountProductInStore() {
        Cart cart = user.getState().getCart();
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

}
