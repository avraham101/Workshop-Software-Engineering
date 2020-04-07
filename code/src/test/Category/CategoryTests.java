package Category;

import Data.Data;
import Data.TestData;
import DataAPI.PaymentData;
import DataAPI.ProductData;
import DataAPI.StoreData;
import Domain.*;
import Stubs.ProductStub;
import Stubs.StoreStub;
import Systems.PaymentSystem.ProxyPayment;
import Systems.SupplySystem.ProxySupply;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * test functions of the class Basket
 */
public class CategoryTests {

    protected TestData data;
    protected Category category;

    @Before
    public void setUp() {
        data = new TestData();
        String cat = data.getProductData(Data.VALID).getCategory();
        category = new Category(cat);
    }

    @Test
    public void test(){
        testAddProduct();
        testRemoveProduct();
    }

    /**
     * use case 4.1.1 - add product to store
     */
    protected void testAddProduct() {
        Product product = data.getRealProduct(Data.VALID);
        assertTrue(category.addProduct(product));
    }

    /**
     * use case 4.1.2 - remove product from store
     */
    private void testRemoveProduct() {
        ProductData productData = data.getProductData(Data.VALID);
        category.removeProduct(productData.getProductName());
        List<Product> list = category.getProducts();
        assertTrue(list.isEmpty());
    }

}