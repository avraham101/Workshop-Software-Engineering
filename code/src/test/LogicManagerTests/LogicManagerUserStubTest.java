package LogicManagerTests;

import Data.Data;
import DataAPI.ProductData;
import DataAPI.StoreData;
import Domain.Store;
import org.junit.Before;

import static org.junit.Assert.*;

public class LogicManagerUserStubTest extends LogicManagerUserAndStoresStubs {

    @Before
    public void setUp() {
        super.setUp();
    }

    /**
     * test use case 3.2 - Open Store
     */
    @Override
    protected void testOpenStore() {
        super.testOpenStore();
        StoreData storeData = data.getStore(Data.VALID);
        Store store = stores.get(storeData.getName());
        //This test check if store added
        assertNotNull(store);
        assertEquals(storeData.getName(),store.getName());
        //This test check if can add store twiced
        assertFalse(logicManager.openStore(data.getStore(Data.VALID)));
    }

    /**
     *  use case 2.7.4 - add product to cart
     */
    @Override
    protected void testAddProductToCart() {
        super.testAddProductToCart();
        testAddProductToCartInvalidProduct();
    }

    /**
     * part of test use case 3.2 - Open Store
     */
    @Override
    protected void testOpenStoreSucces(){
        StoreData storeData = data.getStore(Data.VALID);
        assertTrue(logicManager.openStore(storeData));
    }

    /**
     * part of use case 2.7.4 - add product to cart
     */
    private void testAddProductToCartInvalidProduct() {
        ProductData product = data.getProductData(Data.NULL_PRODUCT);
        assertFalse(logicManager.aadProductToCart(product.getProductName(),product.getStoreName(),product.getAmount()));
        product = data.getProductData(Data.NEGATIVE_AMOUNT);
        assertFalse(logicManager.aadProductToCart(product.getProductName(),product.getStoreName(),product.getAmount()));
        product = data.getProductData(Data.ZERO);
        assertFalse(logicManager.aadProductToCart(product.getProductName(),product.getStoreName(),product.getAmount()));
        product = data.getProductData(Data.VALID);
        assertFalse(logicManager.aadProductToCart(product.getProductName(),product.getStoreName(),product.getAmount() * 2));
    }

}
