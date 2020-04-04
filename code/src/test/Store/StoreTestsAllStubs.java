package Store;

import Data.Data;
import Data.TestData;
import DataAPI.ProductData;
import Domain.*;
import Stubs.ProductStub;
import Systems.PaymentSystem.ProxyPayment;
import Systems.SupplySystem.ProxySupply;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class StoreTestsAllStubs {
    protected Store store;
    protected TestData data;

    @Before
    public void setUp(){
        data=new TestData();
        store=new Store(data.getStore(Data.VALID).getName(), null, null,
                new Permission(data.getSubscribe(Data.VALID)), new ProxySupply(),new ProxyPayment());
    }

    @Test
    public void test(){
        testAddProduct();
        testEditProduct();
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
        testAddReview();
    }

    /**
     * test case that not need to add new category
     */
    private void testAddProductHasCategory() {
        ProductData p=data.getProductData(Data.VALID);
        store.getProducts().remove(p.getProductName());
        assertTrue(store.addProduct(data.getProductData(Data.VALID)));
        makeProductStub();
    }

    /**
     * insert stub product to the products map
     */
    protected void makeProductStub() {
        ProductData p=data.getProductData(Data.VALID);
        store.getProducts().put(p.getProductName(),new ProductStub(p,new Category(p.getCategory())));
    }

    /**
     * test product with duplicate name
     */
    private void testAddProductSameName() {
        assertFalse(store.addProduct(data.getProductData(Data.VALID)));
    }

    /**
     * test success add product
     */

    protected void testAddProductSuccess() {
        assertTrue(store.addProduct(data.getProductData(Data.VALID)));
    }

    /**
     * test use case 4.1.2 remove product from store
     */
    protected void testRemoveProduct() {
        testSuccessRemoveProduct();
        testFailRemoveProduct();
    }

    private void testSuccessRemoveProduct() {
        assertTrue(store.removeProduct(data.getProductData(Data.VALID).getProductName()));
    }

    private void testFailRemoveProduct() {
        assertFalse(store.removeProduct(data.getProductData(Data.VALID).getProductName()));
    }

    /**
     * test use case 4.1.3 edit product
     */
    protected void testEditProduct(){
        testFailEditProduct();
        testSuccessEditProduct();
    }

    /**
     * test product that not in the store
     */
    protected void testFailEditProduct() {
        ProductData p=data.getProductData(Data.WRONG_NAME);
        assertFalse(store.editProduct(p));
    }

    protected void testSuccessEditProduct(){
        assertTrue(store.editProduct(data.getProductData(Data.EDIT)));
    }

    /**
     * use case 3.3 - add re
     */
    private void testAddReview() {
        Review review = data.getReview(Data.VALID);
        assertTrue(store.addReview(review));
        Product product= store.getProducts().get(review.getProductName());
        assertTrue(product.getReviews().contains(review));

        review = data.getReview(Data.WRONG_PRODUCT);
        assertFalse(store.addReview(review));

    }
}
