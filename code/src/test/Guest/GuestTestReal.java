package Guest;

import Data.Data;
import Data.TestData;
import Domain.Cart;
import Domain.Product;
import Domain.Store;
import Stubs.CartStub;
import org.junit.Before;

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

    /**
     * use case 2.7 add to cart
     */
    @Override
    public void testAddProductToCart() {
        super.testAddProductToCart();
        Store store = data.getRealStore(Data.VALID);
        Product product = data.makeProduct(data.getProductData(Data.VALID));
        HashMap<Product,Integer> products = cart.getBasket(store.getName()).getProducts();
        assertEquals(1,products.size());
        Iterator<Product> iterator =  products.keySet().iterator();
        Product real = iterator.next();
        assertEquals(real.getName(),product.getName());
    }
}
