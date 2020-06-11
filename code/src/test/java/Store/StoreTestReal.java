package Store;

import Data.Data;
import DataAPI.ProductData;
import DataAPI.Response;
import DataAPI.StoreData;
import Data.TestData;
import Domain.*;
import Domain.Discount.Discount;
import Drivers.LogicManagerDriver;
import Persitent.DaoHolders.DaoHolder;
import Persitent.DaoInterfaces.ICartDao;
import Persitent.DaoInterfaces.IStoreDao;
import Persitent.DaoInterfaces.ISubscribeDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class StoreTestReal extends StoreTestsAllStubs {


    @Before
    public void setUp() {
        Utils.Utils.TestMode();
        try {
            logicDriver = new LogicManagerDriver();
        }
        catch (Exception e) {
            fail();
        }
        data = new TestData();
        daoHolder = new DaoHolder();
        setUpStore();
        store = daoHolder.getStoreDao().find(data.getRealStore(Data.VALID).getName());
    }

    private void setUpStore() {
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        ProductData productData = data.getProductData(Data.VALID);
        StoreData storeData = data.getStore(Data.VALID);
        int id = logicDriver.connectToSystem();
        Response<Boolean> response = logicDriver.register(subscribe.getName(), subscribe.getPassword());
        if(response.getValue()) {
            response = logicDriver.login(id, subscribe.getName(), subscribe.getPassword());
            if(response.getValue()) {
                response = logicDriver.openStore(id, storeData);
                if(response.getValue()) {
                    response = logicDriver.addProductToStore(id, productData);
                }
                else
                    fail();
            }
            else
                fail();
        }
        else
            fail();
    }

    @After
    public void tearDownStore() {
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        ISubscribeDao subscribeDao = daoHolder.getSubscribeDao();
        subscribeDao.remove(subscribe.getName());
        Subscribe admin = data.getSubscribe(Data.ADMIN);
        subscribeDao.remove(admin.getName());
        StoreData storeData = data.getStore(Data.VALID);
        IStoreDao storeDao = daoHolder.getStoreDao();
        storeDao.removeStore(storeData.getName());
        ICartDao cartDao = daoHolder.getCartDao();
        Cart cart = cartDao.find(subscribe.getName());
        if(cart!=null)
            cartDao.remove(cart);
    }

    /**
     * prepare product wth discount at the store
     */
    protected void setUpDiscountAdded() {
        Discount d=data.getDiscounts(Data.VALID).get(0);
        store.addDiscount(d);
    }

    /**
     * use case 2.4.2 view product in store
     */
    @Test
    public void testViewProductInStore(){
        store = daoHolder.getStoreDao().find(store.getName());
        List<ProductData> data = store.viewProductInStore();
        for(ProductData d : data) {
            assertTrue(store.getProducts().containsKey(d.getProductName()));
        }
    }

//TODO

    /**
     * part of 2.8 - buy cart
     */
    @Test
    public void calculatePrice(){
        setUpDiscountAdded();
        Subscribe sub = daoHolder.getSubscribeDao().find("Yuval");
        Product p = daoHolder.getProductDao().find(data.getRealProduct(Data.VALID));
        assertEquals(store.getProducts().get(p.getName()).getName(), p.getName());
        sub.addProductToCart(store, p, 1);
        Cart cart = daoHolder.getCartDao().find(sub.getName());
        Map<String, ProductInCart> productAmount = cart.getBasket(store.getName()).getProducts();
        double expected = 0;
        double discount = 1;
        for(ProductInCart productInCart: productAmount.values()) {
            expected += productInCart.getAmount() * productInCart.getPrice() - discount;
        }
        double price = store.calculatePrice(productAmount);
        assertEquals(price, expected,0.001);
    }

    /**
     * use case 2.8 - test product valid:
     */
    @Test
    public void testReserveProductsProduct() {
        ProductData productData = data.getProductData(Data.VALID);
        HashMap<String,ProductInCart> products = data.getCart(Data.VALID);
        int amount = products.get(productData.getProductName()).getAmount();
        int amountInStore = store.getProduct(productData.getProductName()).getAmount();
        assertTrue(this.store.reserveProducts(products.values()));
        assertEquals(amountInStore - amount, store.getProduct(productData.getProductName()).getAmount());
    }

    /**
     * part of test use case 4.1.3
     * test product that not in the store
     */
    @Test
    public void testFailEditProduct() {
        ProductData p=data.getProductData(Data.WRONG_NAME);
        assertFalse(store.editProduct(p).getValue());
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
     * use case 3.3 - add review
     */
    @Test
    public void testAddReview() {
        Review review = data.getReview(Data.VALID);
        assertTrue(store.addReview(review));
        Product product= store.getProducts().get(review.getProductName());
        assertTrue(product.getReviews().contains(review));
        review = data.getReview(Data.WRONG_PRODUCT);
        assertFalse(store.addReview(review));
    }

    /**
     * use case 4.1.1
     */
    @Test
    public void testAddProductSuccess() {
        Product p = daoHolder.getProductDao().find(data.getRealProduct(Data.VALID));
        assertEquals(store.getProducts().get(p.getName()).getName(), p.getName());
        assertEquals(store.getProducts().get(p.getName()).getStore(), p.getStore());
    }

    /**
     * test product with duplicate name
     */
    @Test
    public void testAddProductSameName() {
        assertFalse(store.addProduct(data.getProductData(Data.VALID)).getValue());
    }

    /**
     * test case that not need to add new category
     */
    @Test
    public void testAddProductHasCategory() {
        ProductData p=data.getProductData(Data.VALID);
        store.removeProduct(p.getProductName());
        daoHolder.getStoreDao().update(store);
        assertTrue(store.addProduct(data.getProductData(Data.VALID)).getValue());
    }

    /**
     * test cant remove product twice
     */
    @Test
    public void testFailRemoveProduct() {
        assertTrue(store.removeProduct(data.getProductData(Data.VALID).getProductName()).getValue());
        assertFalse(store.removeProduct(data.getProductData(Data.VALID).getProductName()).getValue());
    }

    /**
     * test use case 4.1.2
     */
    @Override
    @Test
    public void testSuccessRemoveProduct() {
        Product productToRemove = data.getRealProduct(Data.VALID);
        store.removeProduct(productToRemove.getName());
        daoHolder.getStoreDao().update(store);
        store = daoHolder.getStoreDao().find(store.getName());
        assertFalse(store.getProducts().containsKey(data.getProductData(Data.VALID).getProductName()));
    }

    /**
     * use case 4.1.3
     */
    @Override
    @Test
    public void testSuccessEditProduct() {
        Product product = daoHolder.getProductDao().find(data.getRealProduct(Data.EDIT));
        int oldAmount = product.getAmount();
        product.setAmount(oldAmount + 1);
        daoHolder.getProductDao().updateProduct(product);
        product = daoHolder.getProductDao().find(data.getRealProduct(Data.EDIT));
        assertEquals(oldAmount + 1, product.getAmount());
        assertEquals(store.getProducts().get(product.getName()).getName(), product.getName());
        assertEquals(store.getProducts().get(product.getName()).getStore(), product.getStore());
    }

    /**
     * test use case 4.2.1.1 -add discount to store
     * success
     */
    @Test
    public void testAddDiscountToStoreSuccess(){
        Discount d = data.getDiscounts(Data.VALID).get(0);
        assertTrue(store.addDiscount(d).getValue());
        daoHolder.getStoreDao().update(store);
        store = daoHolder.getStoreDao().find(store.getName());
        int id = store.getDiscount().get(d.getId()).getId();
        assertEquals(id, (int) d.getId());
    }


    /**
     * test use case 4.2.1.1 -add discount to store
     */
    @Test
    public void testAddDiscountFailProductNotInStore(){
        Product productToRemove = data.getRealProduct(Data.VALID);
        Product productDB = daoHolder.getProductDao().find(productToRemove);
        assertTrue(daoHolder.getProductDao().removeProduct(productDB));
        store = daoHolder.getStoreDao().find(store.getName());
        assertFalse(store.addDiscount(data.getDiscounts(Data.VALID).get(0)).getValue());
        assertTrue(store.getDiscount().isEmpty());
    }

    /**
     * test use case 4.2.1.2 - delete discount from store
     */
    @Test
    public void testDeleteProductFromStoreDiscountNotExistInStore(){
        assertFalse(store.deleteDiscount(0).getValue());
    }


    /**
     * test use case 4.2.1.2 - delete discount from store
     */
    @Test
    public void testDeleteDiscountFromStoreSuccess(){
        setUpDiscountAdded();
        Discount d = this.store.getDiscount().values().iterator().next();
        int id = d.getId();
        assertTrue(store.deleteDiscount(id).getValue());
        assertTrue(store.getDiscount().isEmpty());
    }

    /**
     * use case 4.3.1 - manage owner - success
     */
    @Test
    public void testAddOwnerFailAddMySelf(){
        assertFalse(store.addOwner(data.getStore(Data.VALID).getName(),data.getSubscribe(Data.VALID).getName()).getValue());
        tearDownStore();
    }

    @Test
    public void testAddOwnerSuccess(){
        assertTrue(store.addOwner(data.getStore(Data.VALID).getName(),data.getSubscribe(Data.VALID2).getName()).getValue());
        tearDownStore();
    }


    /**
     * use case 4.3.2 - approveManager - success
     */
    @Test
    public void approveOwnerSuccess(){
        String givenBy=data.getSubscribe(Data.VALID).getName();
        String owner=data.getSubscribe(Data.VALID2).getName();
        OwnerAgreement ownerAgreement=new OwnerAgreement(new HashSet<>(),givenBy,owner,store.getName());
        store.getAgreementMap().put(owner,ownerAgreement);
        assertTrue(store.approveAgreement(givenBy,owner).getValue());
        tearDownStore();
    }


    /**
     * use case 2.8 - test amount too big:
     */
    @Test
    public void testReserveProductsLargeAmount() {
        HashMap<String, ProductInCart> products = data.getCart(Data.LARGE_AMOUNT);
        assertFalse(this.store.reserveProducts(products.values()));
    }


}
