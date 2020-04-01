package Store;

import Data.Data;
import Data.TestData;
import DataAPI.ProductData;
import Domain.Category;
import Domain.Permission;
import Domain.Store;
import Stubs.ProductStub;
import Systems.PaymentSystem.ProxyPayment;
import Systems.SupplySystem.ProxySupply;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class StoreTestsAllStubs {
    Store store;
    TestData data;

    @Before
    public void setUp(){
        data=new TestData();
        store=new Store(data.getStore(Data.VALID).getName(), null, null
                , new Permission(data.getSubscribe(Data.VALID)), new ProxySupply(),new ProxyPayment());
    }

    @Test
    public void test(){
        testAddProduct();
        testRemoveProduct();
    }


    /**
     * use case 4.1.1 -add product
     */
    private void testAddProduct() {
        testAddProductSuccess();
        //add product stub
        testAddProductSameName();
        testAddProductHasCategory();
    }

    /**
     * test case that not need to add new category
     */
    private void testAddProductHasCategory() {
        ProductData p=data.getProduct(Data.VALID);
        store.getProducts().remove(p.getProductName());
        assertTrue(store.addProduct(data.getProduct(Data.VALID)));
        makeProductStub();
    }

    /**
     * insert stub product to the products map
     */
    protected void makeProductStub() {
        ProductData p=data.getProduct(Data.VALID);
        store.getProducts().put(p.getProductName(),new ProductStub(p,new Category(p.getCategory())));
    }

    /**
     * test product with duplicate name
     */

    private void testAddProductSameName() {
        assertFalse(store.addProduct(data.getProduct(Data.VALID)));
    }

    /**
     * test success add product
     */

    protected void testAddProductSuccess() {
        assertTrue(store.addProduct(data.getProduct(Data.VALID)));
    }

    /**
     * test use case 4.1.2 remove product from store
     */
    protected void testRemoveProduct() {
        testSuccessRemoveProduct();
        testFailRemoveProduct();
    }

    private void testSuccessRemoveProduct() {
        assertTrue(store.removeProduct(data.getProduct(Data.VALID).getProductName()));
    }

    private void testFailRemoveProduct() {
        assertFalse(store.removeProduct(data.getProduct(Data.VALID).getProductName()));
    }
}
