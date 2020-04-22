package Guest;

import Data.Data;
import Data.TestData;
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
        data=new TestData();
        cart = new Cart();
        guest=new Domain.Guest(cart);
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
     * use case 2.8
     */
    @Override @Test
    public void testbuyCart() {
        setUpProductAddedToCart();
        super.testbuyCart();
    }
}
