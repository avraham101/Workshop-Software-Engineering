import Data.Data;
import Data.TestData;
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

}