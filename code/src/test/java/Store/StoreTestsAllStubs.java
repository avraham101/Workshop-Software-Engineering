package Store;

import Data.Data;
import Data.TestData;
import DataAPI.ProductData;
import Domain.*;
import Domain.Discount.Discount;
import Drivers.LogicManagerDriver;
import Persitent.DaoHolders.DaoHolder;
import Stubs.ProductStub;
import Stubs.StoreStub;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class StoreTestsAllStubs {
    protected Store store;
    protected TestData data;
    protected LogicManagerDriver logicDriver;
    protected DaoHolder daoHolder;

    @Before
    public void setUp(){
        Utils.Utils.TestMode();
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
        //assertEquals(amountInStore - amount, store.getProduct(productData.getProductName()).getAmount());
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
     * test success add product
     */
    @Test
    public void testAddProductSuccess() {
        assertTrue(store.addProduct(data.getProductData(Data.VALID)).getValue());
    }


    /**
     * test use case 4.1.2 remove product from store
     */
    @Test
    public void testSuccessRemoveProduct() {
        assertTrue(store.removeProduct(data.getProductData(Data.VALID).getProductName()).getValue());
    }



    /**
     * part of test use case 4.1.3
     * test success
     */
    @Test
    public void testSuccessEditProduct(){
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
    }








}
