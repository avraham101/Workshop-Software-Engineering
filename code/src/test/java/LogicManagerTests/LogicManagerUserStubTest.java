package LogicManagerTests;

import Data.Data;
import DataAPI.ProductData;
import DataAPI.StoreData;
import Domain.Store;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LogicManagerUserStubTest extends LogicManagerUserAndStoresStubs {

    @Before
    public void setUp() {
        super.setUp();
    }

    /**---------------------------------set up-------------------------------------*/

    @Override
    protected void setUpOpenedStore() {
        setUpLogedInUser();
        StoreData storeData = data.getStore(Data.VALID);
        logicManager.openStore(data.getId(Data.VALID), storeData);
    }

    /**---------------------------------set-ups-------------------------------------*/


    /**
     *  use case 2.7.4 - add product to cart
     */
    @Override
    public void testAddProductToCart() {
        super.testAddProductToCart();
        testAddProductToCartNullProduct();
        testAddProductToCartNegativeAmount();
        testAddProductToCartZeroAmount();
        testAddProductToCartNullStore();
    }

    /**
     * part of use case 2.7.4 - check adding null product to cart
     */
    private void testAddProductToCartNullProduct() {
        ProductData product = data.getProductData(Data.NULL_PRODUCT);
        assertFalse(logicManager.addProductToCart(data.getId(Data.VALID), product.getProductName(), product.getStoreName(), product.getAmount()).getValue());
        logicManager.deleteFromCart(data.getId(Data.VALID), product.getProductName(), product.getStoreName());
    }

    /**
     * part of use case 2.7.4 - check adding negative amount of product to cart
     */
    private void testAddProductToCartNegativeAmount() {
        ProductData product = data.getProductData(Data.NEGATIVE_AMOUNT);
        assertFalse(logicManager.addProductToCart(data.getId(Data.VALID), product.getProductName(), product.getStoreName(), product.getAmount()).getValue());
        logicManager.deleteFromCart(data.getId(Data.VALID), product.getProductName(), product.getStoreName());
    }

    /**
     * part of use case 2.7.4 - check adding negative amount of product to cart
     */
    private void testAddProductToCartZeroAmount() {
        ProductData product = data.getProductData(Data.ZERO);
        assertFalse(logicManager.addProductToCart(data.getId(Data.VALID), product.getProductName(), product.getStoreName(), product.getAmount()).getValue());
        logicManager.deleteFromCart(data.getId(Data.VALID), product.getProductName(), product.getStoreName());
    }

    /**
     * part of use case 2.7.4 - check adding negative amount of product to cart
     */
    private void testAddProductToCartNullStore() {
        ProductData product = data.getProductData(Data.NULL_STORE);
        assertFalse(logicManager.addProductToCart(data.getId(Data.VALID), product.getProductName(), product.getStoreName(), product.getAmount()).getValue());
        logicManager.deleteFromCart(data.getId(Data.VALID), product.getProductName(), product.getStoreName());
    }

    /**
     * test use case 3.2 - Open Store
     */
    @Override
    public void testOpenStoreSucces() {
        super.testOpenStoreSucces();
        StoreData storeData = data.getStore(Data.VALID);
        Store store = stores.get(storeData.getName());
        //This test check if store added
        assertNotNull(store);
        assertEquals(storeData.getName(),store.getName());
        //This test check if can add store twiced
        assertFalse(logicManager.openStore(data.getId(Data.VALID), data.getStore(Data.VALID)).getValue());
    }

    /**
     * part of use case 3.3 - write review
     * empty for overriding in extended class
     */
    @Override
    protected void testWriteReviewValid() {

    }

    /**
     * getManagersOfStoreUserManaged tests
     */
    @Test
    public void getManagersOfStoreUserManagedNotExistStore(){
        setUpManagerAddedSubManagerAdded();
        assertNull(logicManager.getManagersOfStoreUserManaged(data.getId(Data.VALID),
                data.getSubscribe(Data.VALID).getName()).getValue());
    }


}
