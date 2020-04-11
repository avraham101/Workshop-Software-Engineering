package LogicManagerTests;

import DataAPI.*;
import Data.Data;
import Domain.*;
import Systems.HashSystem;
import org.junit.Before;
import org.junit.Test;

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

    /**---------------------set-ups-------------------------------------------*/
    @Override
    protected void setUpProductAddedToCart() {
        super.setUpProductAddedToCart();
        ProductData product = data.getProductData(Data.VALID);
        logicManager.addProductToCart(product.getProductName(),
                product.getStoreName(), product.getAmount());
    }

    /**
     * set up manager added and make the new manager adding another manager
     * set up to check use case 4.7 that the mangers will be removed and all the managers he managed
     * will be removed as well
     */
    @Override
    protected void setUpManagerAddedSubManagerAdded(){
        setUpPermissionsAdded();
        currUser.setState(users.get(data.getSubscribe(Data.ADMIN).getName()));
        logicManager.addManager(data.getSubscribe(Data.VALID2).getName(),data.getStore(Data.VALID).getName());
        currUser.setState(users.get(data.getSubscribe(Data.VALID).getName()));
    }
    /**----------------------set-ups------------------------------------------*/

    /**
     * test use case 2.3 - Login
     */
    @Override @Test
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
    @Override @Test
    public void testViewDataStores() {
        super.testViewDataStores();
        for (StoreData storeData : logicManager.viewStores()) {
            assertTrue(stores.containsKey(storeData.getName()));
        }
    }

    /**
     * use case 2.4.2 - view the products in some store test
     */
    @Override @Test
    public void testViewProductsInStore() {
        setUpProductAdded();
        String storeName = data.getStore(Data.VALID).getName();
        for (ProductData productData: logicManager.viewProductsInStore(storeName)) {
            assertTrue(stores.get(storeName).getProducts().containsKey(productData.getProductName()));
        }
    }

    /**
     * use case 2.5 - view specific product
     */
    @Override @Test
    public void testViewSpecificProduct() {
        setUpProductAdded();
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
        ProductData expected = data.getProductData(Data.VALID);
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
        ProductData expected = data.getProductData(Data.VALID);
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
        ProductData productData = data.getProductData(Data.VALID);
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
        ProductData productData = data.getProductData(Data.VALID);
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


    /**
     * use case 2.7.1 - watch cart details
     * success test
     */
    @Override @Test
    public void testWatchCartDetails() {
        super.testWatchCartDetails();
        ProductData productData = data.getProductData(Data.VALID);
        CartData cartData = logicManager.watchCartDetatils();
        List<ProductData> list = cartData.getProducts();
        assertEquals(1, list.size());
        assertEquals(list.get(0).getProductName(), productData.getProductName());
    }

    /**
     * use case 2.7.2 - delete product from cart
     * success test
     */
    @Override @Test
    public void testDeleteProductFromCart() {
        setUpProductAddedToCart();
        ProductData productData = data.getProductData(Data.VALID);
        assertTrue(logicManager.deleteFromCart(productData.getProductName(),productData.getStoreName()));

    }

    /**
     * use case 2.7.3 - edit amount of product in cart
     * success test
     */
    @Override @Test
    public void testEditProductsInCart() {
        setUpProductAddedToCart();
        ProductData productData = data.getProductData(Data.VALID);
        assertTrue(logicManager.editProductInCart(productData.getProductName(),productData.getStoreName(),1));
    }

    /**
     *  use case 2.7.4 - add product to cart
     *  success test
     */
    @Override @Test
    public void testAddProductToCart() {
        super.testAddProductToCart();
        testAddProductToCartBasketNull();
        testAddProductToCartValid();
    }

    /**
     * use case 2.7.4
     * test add product when every thing right
     */
    private void testAddProductToCartValid() {
        ProductData product = data.getProductData(Data.VALID);
        assertTrue(logicManager.addProductToCart(product.getProductName(),
                product.getStoreName(), product.getAmount()));
    }

    /**
     * use case 2.7.4
     * test add product when the basket is null
     */
    private void testAddProductToCartBasketNull() {
        ProductData product = data.getProductData(Data.WRONG_STORE);
        assertFalse(logicManager.addProductToCart(product.getProductName(),
                product.getStoreName(), product.getAmount()));
    }

    /**
     * use case 2.8 - test buy Products
     */
    @Override @Test
    public void testBuyProducts() {
        super.testBuyProducts();
        List<Purchase> purchaseList = this.currUser.getState().watchMyPurchaseHistory();
        for (Purchase purchase: purchaseList) {
            String storeName = purchase.getStoreName();
            Store store = this.stores.get(storeName);
            assertTrue(store.getPurchases().contains(purchase));
        }
    }

    /**
     * test: use case 3.1 - Logout
     */
    @Override @Test
    public void testLogout() {
        super.testLogout();
        //test while in Guest Mode
        assertFalse(currUser.logout());
    }

    /**
     * test use case 3.2 - Open Store
     */
    @Override @Test
    public void testOpenStore() {
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
        assertEquals(storeData.getPurchasePolicy(), store.getPurchasePolicy());
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
     * use case 3.3 - write review
     */
    @Override @Test
    public void testWriteReview() {
        super.testWriteReview();
        testWriteReviewSuccess();
        testWriteReviewProductDidntPurchased();
    }

    /**
     * part of use case 3.3 - write review
     */
    private void testWriteReviewSuccess() {
        Review review = data.getReview(Data.VALID);
        //check if the review is in store
        Store store = stores.get(review.getStore());
        Product p = store.getProduct(review.getProductName());
        //check if the review is in the product
        List<Review> reviewList = p.getReviews();
        assertEquals(1,reviewList.size());
        Review result = reviewList.get(0);
        assertEquals(review.getContent(), result.getContent());
        assertEquals(review.getWriter(), result.getWriter());
    }

    /**
     * part of use case 3.3 - write review
     */
    @Override
    protected void testWriteReviewValid() {
        Review review = data.getReview(Data.VALID);
        assertTrue(logicManager.addReview(review.getStore(),review.getProductName(),review.getContent()));
        List<Review> reviews = currUser.getState().getReviews();
        assertNotNull(reviews);
        assertEquals(1, reviews.size());
        assertEquals(review.getContent(), reviews.get(0).getContent());
    }

    /**
     * part of use case 3.3 - write review
     */
    private void testWriteReviewProductDidntPurchased() {
        Review review = data.getReview(Data.WRONG_PRODUCT);
        assertFalse(logicManager.addReview(review.getStore(),review.getProductName(),review.getContent()));
    }

    /**
     * use case 3.5 -add request
     */
    @Override @Test
    public void testAddRequest(){
        setUpProductAdded();
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
     * use case 3.7 - watch purchase history
     */
    @Override @Test
    public void testWatchPurchaseHistory() {
        setUpBoughtProduct();
        List<Purchase> purchases = logicManager.watchMyPurchaseHistory();
        assertNotNull(purchases);
        assertEquals(1,purchases.size());
    }

    /**
     * test use case 4.1.1 -add product to store
     */
    @Override @Test
    public void testAddProduct() {
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
        assertFalse(logicManager.addProductToStore(data.getProductData(Data.SAME_NAME)));
    }

    /**
     * test try adding product without being owner or manager of the store
     */
    private void testAddProductNotManagerOfStore() {
        String validStoreName = data.getProductData(Data.VALID).getStoreName();
        Subscribe sub = ((Subscribe) currUser.getState());
        Permission permission = sub.getPermissions().get(validStoreName);
        sub.getPermissions().clear();
        assertFalse(logicManager.addProductToStore(data.getProductData(Data.VALID)));
        sub.getPermissions().put(validStoreName,permission);
    }

    /**
     * test that user that has no CRUD permission or owner permission cant add products to store
     */
    private void testAddProductDontHavePermission() {
        String validStoreName = data.getProductData(Data.VALID).getStoreName();
        Subscribe sub = ((Subscribe) currUser.getState());
        Permission permission = sub.getPermissions().get(validStoreName);
        permission.removeType(PermissionType.OWNER);
        assertFalse(logicManager.addProductToStore(data.getProductData(Data.VALID)));
        permission.addType(PermissionType.OWNER);
    }

    /**
     * use case 4.1.2 test
     */
    @Override
    protected void testRemoveProductSuccess() {
        super.testRemoveProductSuccess();
        Subscribe sub=(Subscribe)currUser.getState();
        assertFalse(sub.getPermissions().containsKey(data.getProductData(Data.VALID).getProductName()));
    }

    /**
     * use case 4.1.3 edit product
     */
    @Override
    protected void testEditProductSuccess() {
        super.testEditProductSuccess();
        ProductData product=data.getProductData(Data.EDIT);
        Subscribe sub=(Subscribe) currUser.getState();
        assertTrue(sub.getPermissions().get(product.getStoreName()).getStore()
                .getProducts().get(product.getProductName()).equal(product));
    }

    /**
     * use case 4.3 - manage owner
     */
    @Override
    protected void testManageOwnerSuccess() {
        super.testManageOwnerSuccess();
        checkPermissions(Data.VALID2);
    }

    /**
     * generic function for check when adding new permission that it was added to store and user correctly
     * @param d - the data of the user to check the permission of
     */
    private void checkPermissions(Data d){
        Subscribe sub=(Subscribe) currUser.getState();
        Store store=sub.getGivenByMePermissions().get(0).getStore();
        Subscribe newManager=data.getSubscribe(d);
        Permission p=store.getPermissions().get(newManager.getName());
        assertNotNull(p);
        assertEquals(p.getStore().getName(),store.getName());
        newManager=p.getOwner();
        assertNotNull(newManager);
        assertTrue(newManager.getPermissions().containsKey(store.getName()));
    }
    /**
     * use case 4.5 - add manager
     */

    @Override
    protected void testAddManagerStoreSuccess() {
        super.testAddManagerStoreSuccess();
        checkPermissions(Data.ADMIN);
    }

    /**
     * use case 4.6.1 -add permission
     */

    @Override
    protected void testAddPermissionSuccess() {
        super.testAddPermissionSuccess();
        Subscribe sub=(Subscribe) currUser.getState();
        assertTrue(sub.getGivenByMePermissions().get(0).getPermissionType()
                .containsAll(data.getPermissionTypeList()));
    }

    /**
     * use case 4.6.2 - remove permission
     */
    @Override
    protected void testRemovePermissionSuccess() {
        super.testRemovePermissionSuccess();
        Subscribe sub=(Subscribe) currUser.getState();
        assertTrue(sub.getGivenByMePermissions().get(0).getPermissionType().
                isEmpty());
    }

    /**
     * test use case 4.7 - remove manager
     * make user admin manage user niv(VALID2)
     * remove Admin from being manager and check that niv was removed from being a manager recursively
     */
    @Override
    protected void testRemoveManagerSuccess() {
        Subscribe sub=(Subscribe) currUser.getState();
        Permission p=sub.getGivenByMePermissions().get(0);
        Subscribe niv=data.getSubscribe(Data.VALID2);
        String storeName=p.getStore().getName();
        //add another manager
        p.getOwner().addManager(niv,storeName);
        super.testRemoveManagerSuccess();
        assertFalse(niv.getPermissions().containsKey(storeName));
        assertFalse(p.getOwner().getPermissions().containsKey(storeName));
    }

    /**
     * use case 4.9.1 - view request
     */
    @Override @Test
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
    @Override @Test
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
     * test use case 4.10 and 6.4.2 -watch store history
     */
    @Override
    protected void testWatchStoreHistorySuccess() {
        testWatchStoreHistorySuccessNotAdmin();
        testWatchStoreHistorySuccessWhenAdmin();
    }

    /**
     * test use case 4.10 - watch store history from the owner of the store
     */
    protected void testWatchStoreHistorySuccessNotAdmin(){
        checkValidPurchase(logicManager.watchStorePurchasesHistory(data.getStore(Data.VALID).getName()));
    }

    /**
     * test use case 6.4.1 - watch user history
     */
    @Override
    protected void testWatchUserHistorySuccess() {
        checkValidPurchase(logicManager.watchUserPurchasesHistory(data.getSubscribe(Data.VALID).getName()));
    }

    /**
     * test use case 6.4.2 user watch history when admin
     */
    protected void testWatchStoreHistorySuccessWhenAdmin() {
        checkValidPurchase(logicManager.watchStorePurchasesHistory(data.getStore(Data.VALID).getName()));
    }

    /**
     * check the purchase is with the details from buying the product
     * @param purchaseList the list with the products
     */
    private void checkValidPurchase(List<Purchase> purchaseList) {
        Purchase purchase=purchaseList.get(0);
        assertEquals(purchase.getStoreName(),data.getStore(Data.VALID).getName());
        assertEquals(purchase.getBuyer(),data.getSubscribe(Data.VALID).getName());
        assertEquals(purchase.getProduct().get(0).getProductName(),
                data.getProductData(Data.VALID).getProductName());
    }


}

