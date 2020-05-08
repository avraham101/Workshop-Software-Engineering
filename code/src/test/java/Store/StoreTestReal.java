package Store;

import Data.Data;
import DataAPI.ProductData;
import Domain.Product;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class StoreTestReal extends StoreTestsAllStubs {


    /**
     * part of 2.8 - buy cart
     */
    @Test
    public void calculatePrice(){
        setUpDiscountAdded();
        HashMap<Product,Integer> productsAmount=new HashMap<>();
        productsAmount.put(data.getRealProduct(Data.VALID).clone(),3);
        double price=store.calculatePrice(productsAmount);
        assertEquals(price,27,0.001);
    }

    /**
     * use case 4.1.1
     */
    @Override
    protected void testAddProductSuccess() {
        super.testAddProductSuccess();
        ProductData p=data.getProductData(Data.VALID);
        assertTrue(store.getProducts().get(p.getProductName()).equal(p));
    }

    /**
     * test use case 4.1.2
     */
    @Override
    protected void testSuccessRemoveProduct() {
        super.testSuccessRemoveProduct();
        assertFalse(store.getProducts().containsKey(data.getProductData(Data.VALID).getProductName()));
    }

    /**
     * use case 4.1.3
     */
    @Override
    protected void testSuccessEditProduct() {
        super.testSuccessEditProduct();
        ProductData product=data.getProductData(Data.EDIT);
        assertTrue(store.getProducts().get(product.getProductName()).equal(product));
    }

}
