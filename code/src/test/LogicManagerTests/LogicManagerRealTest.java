package LogicManagerTests;

import DataAPI.ProductData;
import Data.Data;
import DataAPI.StoreData;
import Domain.*;
import Systems.HashSystem;
import org.junit.Before;

import javax.sql.DataSource;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

//no stubs full integration
public class LogicManagerRealTest extends LogicManagerUserStubTest {

    @Before
    public void setUp() {
        currUser = new User();
        init();
    }


    /**
     * test use case 2.3 - Login
     */
    @Override
    public void testLogin() {
        super.testLogin();
        testLoginFailAlreadyUserLogged();
    }

    public void testLoginFailAlreadyUserLogged() {
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        assertFalse(logicManager.login(subscribe.getName(),subscribe.getPassword()));
    }

    /**
     * part of test use case 2.3 - Login
     */
    @Override
    protected void testLoginSuccess() {
        super.testLoginSuccess();
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        assertEquals(currUser.getUserName(),subscribe.getName());
        try {
            HashSystem hashSystem = new HashSystem();
            String password = hashSystem.encrypt(subscribe.getPassword());
            assertEquals(password, currUser.getPassword());
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * use case 2.4.1 - view all stores details
     */
    @Override
    protected void testViewDataStores() {
        for (StoreData storeData : logicManager.viewStores()) {
            assertTrue(stores.containsKey(storeData.getName()));
        }
    }

    /**
     * use case 2.4.2 - view the products in some store test
     */
    protected void testViewProductsInStore() {
        String storeName = data.getStore(Data.VALID).getName();
        for (ProductData productData: logicManager.viewProductsInStore(storeName)) {
            assertTrue(stores.get(storeName).getProducts().containsKey(productData.getProductName()));
        }
    }



    /**
     * test: use case 3.1 - Logout
     */
    @Override
    public void testLogout() {
        super.testLogout();
        //test while in Guest Mode
        assertFalse(currUser.logout());
    }

    /**
     * test use case 3.2 - Open Store
     */
    @Override
    protected void testOpenStore() {
        super.testOpenStore();
        testOpenStorePurchesAndDiscontPolicy();
        testOpenStoreUserPermissions();
        testOpenStoreStorePermissions();
    }



    /**
     * part of test use case 3.2 - Open Store
     */
    private void testOpenStorePurchesAndDiscontPolicy() {
        StoreData storeData = data.getStore(Data.VALID);
        Store store = stores.get(storeData.getName());
        assertEquals(storeData.getPurchesPolicy(), store.getPurchesPolicy());
        assertEquals(storeData.getDiscountPolicy(), store.getDiscount());
    }

    /**
     * part of test use case 3.2 - Open Store
     */
    private void testOpenStoreUserPermissions() {
        StoreData storeData = data.getStore(Data.VALID);
        Subscribe subscribe = (Subscribe)currUser.getState();
        Permission permission = subscribe.getPermissions().get(storeData.getName());
        assertTrue(permission.getPermissionType().contains(PermissionType.OWNER));
    }

    /**
     * part of test use case 3.2 - Open Store
     */
    private void testOpenStoreStorePermissions() {
        StoreData storeData = data.getStore(Data.VALID);
        Store store = stores.get(storeData.getName());
        assertTrue(store.getPermissions().containsKey(currUser.getUserName()));
        Permission p = store.getPermissions().get(currUser.getUserName());
        assertTrue(p.getPermissionType().contains(PermissionType.OWNER));
    }


    /**
     * use case 3.5 -add request
     */
    @Override
    public void testAddRequest(){
        testSubscribeAddRequestSuccess();
        testSubscribeAddRequestFail();
    }

    public void testSubscribeAddRequestSuccess() {
        Request request = data.getRequest(Data.VALID);
        assertTrue(logicManager.addRequest(request.getStoreName(), request.getContent()));

        // check request saved in the store and user.
        StoreData storeData = data.getStore(Data.VALID);

        Store store = stores.get(storeData.getName());
        assertEquals(store.getRequests().get(request.getId()).getSenderName(), request.getSenderName());
        assertEquals(store.getRequests().get(request.getId()).getStoreName(), request.getStoreName());
        assertEquals(store.getRequests().get(request.getId()).getContent(), request.getContent());
        assertEquals(store.getRequests().get(request.getId()).getComment(), request.getComment());

        Subscribe subscribe = users.get(currUser.getUserName());
        assertEquals(subscribe.getRequests().get(0).getSenderName(), request.getSenderName());
        assertEquals(subscribe.getRequests().get(0).getStoreName(), request.getStoreName());
        assertEquals(subscribe.getRequests().get(0).getContent(), request.getContent());
        assertEquals(subscribe.getRequests().get(0).getComment(), request.getComment());
    }

    public void testSubscribeAddRequestFail() {
        Request request1 = data.getRequest(Data.NULL_NAME);
        Request request2 = data.getRequest(Data.NULL);
        assertFalse(logicManager.addRequest(request1.getStoreName(), request1.getContent()));
        assertFalse(logicManager.addRequest(request2.getStoreName(), request2.getContent()));
    }

    /**
     * use case 4.9.1 - view request
     */
    @Override
    public void testStoreViewRequest(){
        super.testStoreViewRequest();
        testStoreViewRequestSuccess();
        testStoreViewRequestFail();
    }

    private void testStoreViewRequestSuccess() {
        testAddRequest();
        StoreData storeData = data.getStore(Data.VALID);
        List<Request> excepted = new LinkedList<>(stores.get(storeData.getName()).getRequests().values());
        List<Request> actual = logicManager.viewStoreRequest(storeData.getName());
        assertTrue(excepted.containsAll(actual));
    }

    private void testStoreViewRequestFail() {
        assertTrue(logicManager.viewStoreRequest(data.getStore(Data.NULL_NAME).getName()).isEmpty());
    }

    /**
     * test use case 4.9.2
     */
    @Override
    public void testReplayRequest() {
        testReplayRequestSuccess();
        testReplayRequestFail();

    }

    private void testReplayRequestSuccess() {
        testAddRequest();
        Request request = data.getRequest(Data.VALID);
        //check the comment save
        StoreData storeData = data.getStore(Data.VALID);
        Request actual = currUser.replayToRequest(request.getStoreName(), request.getId(), "The milk is there, open your eyes!");
        Request excepted = stores.get(storeData.getName()).getRequests().get(request.getId());
        assertEquals(excepted.getId(),actual.getId());
        assertEquals(excepted.getComment(),actual.getComment());
    }

    private void testReplayRequestFail() {
        Request request = data.getRequest(Data.WRONG_ID);
        assertNull(logicManager.replayRequest(request.getStoreName(), request.getId(), request.getContent()));
    }

    /**
     * test use case 4.1.1 -add product to store
     */

    @Override
    protected void testAddProduct() {
        super.testAddProduct();
        testAddProductWithSameName();
    }

    @Override
    protected void testAddProductFail() {
        super.testAddProductFail();
        testAddProductNotManagerOfStore();
        testAddProductDontHavePermission();
    }

    /**
     * test adding product with name that is not unique
     */

    private void testAddProductWithSameName() {
        assertFalse(logicManager.addProductToStore(data.getProduct(Data.SAME_NAME)));
    }

    /**
     * test try adding product without being owner or manager of the store
     */
    private void testAddProductNotManagerOfStore() {
        String validStoreName = data.getProduct(Data.VALID).getStoreName();
        Subscribe sub = ((Subscribe) currUser.getState());
        Permission permission = sub.getPermissions().get(validStoreName);
        sub.getPermissions().clear();
        assertFalse(logicManager.addProductToStore(data.getProduct(Data.VALID)));
        sub.getPermissions().put(validStoreName,permission);
    }

    /**
     * test that user that has no CRUD permission or owner permission cant add products to store
     */
    private void testAddProductDontHavePermission() {
        String validStoreName = data.getProduct(Data.VALID).getStoreName();
        Subscribe sub = ((Subscribe) currUser.getState());
        Permission permission = sub.getPermissions().get(validStoreName);
        permission.removeType(PermissionType.OWNER);
        assertFalse(logicManager.addProductToStore(data.getProduct(Data.VALID)));
        permission.addType(PermissionType.OWNER);
    }

    /**
     * use case 4.1.2 test
     */
    @Override
    protected void testRemoveProductSuccess() {
        super.testRemoveProductSuccess();
        Subscribe sub=(Subscribe)currUser.getState();
        assertFalse(sub.getPermissions().containsKey(data.getProduct(Data.VALID).getProductName()));
    }


    /**
     * use case 4.1.3 edit product
     */

    @Override
    protected void testEditProductSuccess() {
        super.testEditProductSuccess();
        ProductData product=data.getProduct(Data.EDIT);
        Subscribe sub=(Subscribe) currUser.getState();
        assertTrue(sub.getPermissions().get(product.getStoreName()).getStore()
                .getProducts().get(product.getProductName()).equal(product));
    }

    /**
     * use case 4.5 - add manager
     */

    @Override
    protected void testAddManagerStoreSuccess() {
        super.testAddManagerStoreSuccess();
        Subscribe sub=(Subscribe) currUser.getState();
        assertTrue(sub.getGivenByMePermissions().get(0).getStore().getPermissions()
                .containsKey(data.getSubscribe(Data.ADMIN).getName()));
    }

    /**
     * use case 2.5 - view specific product
     */
    @Override
    protected void testViewSpecificProduct() {
        testViewSpecificProductWrongSearch();
        testViewSpecificProductWrongFilter();
        testViewSpecificProductSearchNone();
        testViewSpecificProductSearchByName();
        testViewSpecificProductSearchByCategory();
        testViewSpecificProductSearchByKeyWord();
        testViewSpecificProductFillterMin();
        testViewSpecificProductFillterMax();
        testViewSpecificProductFillterCategory();
    }

    /**
     * part of use case 2.5 - view specific product
     */
    private void testViewSpecificProductSearch(Filter correct, int listSize, Filter wrong) {
        //SUCCESS
        List<ProductData> products = logicManager.viewSpecificProducts(correct);
        assertFalse(products.isEmpty()); //sepose to be 1 product valid
        assertEquals(listSize, products.size());
        ProductData result = products.get(0);
        ProductData expected = data.getProduct(Data.VALID);
        assertTrue(data.compareProductData(expected, result));
        //FAIL
        products = logicManager.viewSpecificProducts(wrong);
        assertTrue(products.isEmpty());
    }

    /**
     * part of use case 2.5 - view specific product
     */
    private void testViewSpecificProductSearchNone() {
        //SUCCESS ALWAYS
        Filter filter = data.getFilter(Data.VALID);
        filter.setSearch(Search.NONE);
        List<ProductData> products = logicManager.viewSpecificProducts(filter);
        assertFalse(products.isEmpty()); //sepose to be 1 product valid
        assertEquals(1, products.size());
        ProductData result = products.get(0);
        ProductData expected = data.getProduct(Data.VALID);
        assertTrue(data.compareProductData(expected, result));
    }

    /**
     * part of use case 2.5 - view specific product
     */
    protected void testViewSpecificProductSearchByName() {
        Filter correct = data.getFilter(Data.VALID);
        correct.setSearch(Search.PRODUCT_NAME);
        Filter wrong = data.getFilter(Data.WRONG_NAME);
        testViewSpecificProductSearch(correct, 1,wrong);
    }

    /**
     * part of use case 2.5 - view specific product
     */
    private void testViewSpecificProductSearchByCategory() {
        ProductData productData = data.getProduct(Data.VALID);
        Filter correct = data.getFilter(Data.VALID);
        correct.setSearch(Search.CATEGORY);
        correct.setValue(productData.getCategory());
        Filter wrong = data.getFilter(Data.WRONG_CATEGORY);
        testViewSpecificProductSearch(correct, 1,wrong);
    }

    /**
     * part of use case 2.5 - view specific product
     */
    private void testViewSpecificProductSearchByKeyWord() {
        ProductData productData = data.getProduct(Data.VALID);
        Filter correct = data.getFilter(Data.VALID);
        correct.setSearch(Search.KEY_WORD);
        correct.setValue(productData.getProductName());
        Filter wrong = data.getFilter(Data.WRONG_KEY_WORD);
        testViewSpecificProductSearch(correct, 1,wrong);
    }

    /**
     * part of use case 2.5 - view specific product
     */
    private void testViewSpecificProductFillter(Filter correct, Filter wrong) {
        //SUCCESS
        List<ProductData> products = logicManager.viewSpecificProducts(correct);
        assertFalse(products.isEmpty());
        int size = 0;
        for(Store s: stores.values()) {
            size+=s.getProducts().size();
        }
        assertEquals(size, products.size());
        //FAIL
        products = logicManager.viewSpecificProducts(wrong);
        assertTrue(products.isEmpty());
    }

    /**
     * part of use case 2.5 - view specific product
     */
    private void testViewSpecificProductFillterMin() {
        Filter correct = data.getFilter(Data.FILTER_MIN);
        Filter wrong = data.getFilter(Data.NEGATIVE_MIN);
        testViewSpecificProductFillter(correct, wrong);
    }

    /**
     * part of use case 2.5 - view specific product
     */
    private void testViewSpecificProductFillterMax() {
        Filter correct = data.getFilter(Data.FILTER_MAX);
        Filter wrong = data.getFilter(Data.NEGATIVE_MAX);
        testViewSpecificProductFillter(correct, wrong);
    }

    /**
     * part of use case 2.5 - view specific product
     */
    private void testViewSpecificProductFillterCategory() {
        Filter correct = data.getFilter(Data.FILTER_ALL_CATEGORIES);
        Filter wrong = data.getFilter(Data.WRONG_CATEGORY);
        testViewSpecificProductFillter(correct, wrong);
    }

}

