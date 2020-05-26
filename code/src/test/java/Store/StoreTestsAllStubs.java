package Store;

import Data.Data;
import Data.TestData;
import DataAPI.ProductData;
import Domain.Review;
import Domain.*;
import Domain.Discount.Discount;
import Drivers.LogicManagerDriver;
import Persitent.DaoHolders.DaoHolder;
import Stubs.ProductStub;
import Stubs.StoreStub;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class StoreTestsAllStubs {
    protected Store store;
    protected TestData data;
    protected LogicManagerDriver logicDriver;
    protected DaoHolder daoHolder;

    @Before
    public void setUp(){
        data=new TestData();
        store=new StoreStub(data.getStore(Data.VALID).getName(),
                new Permission(data.getSubscribe(Data.VALID)),"description");
    }

    /**--------------------------------set-ups-------------------------------------------------------------------*/


    /**
     * prepare a valid product in the store
     */
    protected void setUpProductAdded(){
        store.addProduct(data.getProductData(Data.VALID));
        ProductData p=data.getProductData(Data.VALID);
        store.getProducts().put(p.getProductName(),new ProductStub(p,new Category(p.getCategory())));
    }

    /**
     * prepare product wth discount at the store
     */
    protected void setUpDiscountAdded() {
        setUpProductAdded();
        Discount d=data.getDiscounts(Data.VALID).get(0);
        store.addDiscount(d);
    }

    /**--------------------------------set-ups-------------------------------------------------------------------*/

    /**
     * use case 2.4.2 view product in store
     */
    @Test
    public void testViewProductInStore(){
        List<ProductData> data = store.viewProductInStore();
        for(ProductData d : data) {
            assertTrue(store.getProducts().containsKey(d));
        }
    }


    /**
     * use case 2.8 - test amount too big:
     */
    @Test
    public void testReserveProductsLargeAmount() {
        setUpProductAdded();
        HashMap<String, ProductInCart> products = data.getCart(Data.LARGE_AMOUNT);
        assertFalse(this.store.reserveProducts(products.values()));
    }

    /**
     * use case 2.8 - test product not in store:
     */
    @Test
    public void testReserveProductsProductNotInStore() {
        HashMap<String, ProductInCart> products = data.getCart(Data.WRONG_PRODUCT);
        assertFalse(this.store.reserveProducts(products.values()));
    }

    /**
     * use case 2.8 - test product valid:
     */
    @Test
    public void testReserveProductsProduct() {
        setUpProductAdded();
        ProductData productData = data.getProductData(Data.VALID);
        HashMap<String,ProductInCart> products = data.getCart(Data.VALID);
        int amount = products.get(productData.getProductName()).getAmount();
        int amountInStore = store.getProduct(productData.getProductName()).getAmount();
        assertTrue(this.store.reserveProducts(products.values()));
        assertEquals(amountInStore - amount, store.getProduct(productData.getProductName()).getAmount());
    }

    /**
     * use case 2.8 - check restore products
     */
    @Test
    public void testRestoreAmount() {
        setUpProductAdded();
        ProductData p = data.getProductData(Data.VALID);
        Product product = store.getProduct(p.getProductName());
        int amount = product.getAmount();
        ProductInCart productInCart = new ProductInCart("buyyer",p.getStoreName(),product.getName(),
                5, product.getPrice());
        this.store.restoreAmount(productInCart);
        assertEquals(amount + 5,product.getAmount());
        product.setAmount(product.getAmount() - 5);
    }

    /**
     * use case 3.3 - add review
     */
    @Test
    public void testAddReview() {
        setUpProductAdded();
        Review review = data.getReview(Data.VALID);
        assertTrue(store.addReview(review));
        Product product= store.getProducts().get(review.getProductName());
        assertTrue(product.getReviews().contains(review));
        review = data.getReview(Data.WRONG_PRODUCT);
        assertFalse(store.addReview(review));
    }


    /**
     * use case 4.1.1 -add product
     */
    @Test
    public void testAddProduct() {
        testAddProductSuccess();
        testAddProductSameName();
        testAddProductHasCategory();
    }

    /**
     * test case that not need to add new category
     */
    private void testAddProductHasCategory() {
        ProductData p=data.getProductData(Data.VALID);
        store.getProducts().remove(p.getProductName());
        assertTrue(store.addProduct(data.getProductData(Data.VALID)).getValue());
    }

    /**
     * test product with duplicate name
     */
    private void testAddProductSameName() {
        assertFalse(store.addProduct(data.getProductData(Data.VALID)).getValue());
    }

    /**
     * test success add product
     */
    protected void testAddProductSuccess() {
        assertTrue(store.addProduct(data.getProductData(Data.VALID)).getValue());
    }

    /**
     * test use case 4.1.2 remove product from store
     */
    @Test
    public void testRemoveProduct() {
        setUpProductAdded();
        testSuccessRemoveProduct();
        testFailRemoveProduct();
    }

    /**
     * test use case 4.1.2 remove product from store
     */
    protected void testSuccessRemoveProduct() {
        assertTrue(store.removeProduct(data.getProductData(Data.VALID).getProductName()).getValue());
    }

    /**
     * test cant remove product twice
     */
    private void testFailRemoveProduct() {
        assertFalse(store.removeProduct(data.getProductData(Data.VALID).getProductName()).getValue());
    }

    /**
     * test use case 4.1.3 edit product
     */
    @Test
    public void testEditProduct(){
        setUpProductAdded();
        testFailEditProduct();
        testSuccessEditProduct();
    }

    /**
     * part of test use case 4.1.3
     * test product that not in the store
     */
    protected void testFailEditProduct() {
        ProductData p=data.getProductData(Data.WRONG_NAME);
        assertFalse(store.editProduct(p).getValue());
    }

    /**
     * part of test use case 4.1.3
     * test success
     */
    protected void testSuccessEditProduct(){
        assertTrue(store.editProduct(data.getProductData(Data.EDIT)).getValue());
    }

    /**
     * test use case 4.2.1.1 -add discount to store
     * success
     */
    @Test
    public void testAddDiscountToStoreSuccess(){
        setUpProductAdded();
        Discount d=data.getDiscounts(Data.VALID).get(0);
        assertTrue(store.addDiscount(d).getValue());
        assertEquals(store.getDiscount().get(0),d);
    }

    /**
     * test use case 4.2.1.1 -add discount to store
     */
    @Test
    public void testAddDiscountFailProductNotInStore(){
        assertFalse(store.addDiscount(data.getDiscounts(Data.VALID).get(0)).getValue());
        assertTrue(store.getDiscount().isEmpty());
    }

    /**
     * test use case 4.2.1.2 - delete discount from store
     */
    @Test
    public void testDeleteProductFromStoreSuccess(){
        setUpDiscountAdded();
        assertTrue(store.deleteDiscount(0).getValue());
        assertTrue(store.getDiscount().isEmpty());
    }

    /**
     * test use case 4.2.1.2 - delete discount from store
     */
    @Test
    public void testDeleteProductFromStoreDiscountNotExistInStore(){
        setUpProductAdded();
        assertFalse(store.deleteDiscount(0).getValue());
    }


}
