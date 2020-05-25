package LogicManagerTests;

import Data.Data;
import DataAPI.*;
import Domain.*;
import Domain.Discount.Discount;
import Domain.Discount.RegularDiscount;
import Domain.PurchasePolicy.PurchasePolicy;
import Domain.PurchasePolicy.UserPurchasePolicy;
import Domain.Notification.Notification;
import Persitent.Cache;
import Persitent.DaoHolders.DaoHolder;
import Publisher.SinglePublisher;
import Stubs.StubPublisher;
import Systems.PaymentSystem.ProxyPayment;
import Systems.SupplySystem.ProxySupply;
import Utils.InterfaceAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.transaction.Transactional;
import java.util.*;

import static org.junit.Assert.*;

//no stubs full integration
public class LogicManagerRealTest extends LogicManagerUserStubTest {


    private StubPublisher publisher;

    @BeforeClass
    public static void beforeClass() throws Exception {
        daos=new DaoHolder();
    }

    @Before
    public void setUp() {
        daos=new DaoHolder();
        supplySystem=new ProxySupply();
        paymentSystem=new ProxyPayment();
        cashe=new Cache();
        init();
        currUser=cashe.findUser(data.getId(Data.VALID));
        publisher=new StubPublisher();
        SinglePublisher.initPublisher(publisher);

    }

    /**---------------------set-ups-------------------------------------------*/

    /**
     * set up connect
     */
    @Override
    protected void setUpConnect(){
        logicManager.connectToSystem();
        logicManager.connectToSystem();
        logicManager.connectToSystem();
        //work with the regular user has current user
        currUser=cashe.findUser(data.getId(Data.VALID));
    }

    @Override
    protected void setUpProductAddedToCart() {
        super.setUpProductAddedToCart();
        ProductData product = data.getProductData(Data.VALID);
        logicManager.addProductToCart(data.getId(Data.VALID),product.getProductName(),
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
        logicManager.addManager(data.getId(Data.VALID),data.getSubscribe(Data.VALID2).getName(),data.getStore(Data.VALID).getName());
        currUser.setState(users.get(data.getSubscribe(Data.VALID).getName()));
    }
    /**----------------------set-ups------------------------------------------*/

    /**
     * set up for register a user
     */
    private void setUpRegisteredUser(){
        setUpConnect();
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        logicManager.register(subscribe.getName(),subscribe.getPassword());
    }

    /**
     * use case 2.2 Register
     */
    @Test
    @Override
    @Transactional
    public void testRegisterSuccess() {
        setUpConnect();
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        assertTrue(logicManager.register(subscribe.getName(),subscribe.getPassword()).getValue());
        daos.getSubscribeDao().remove(subscribe.getName());
        tearDownConnect();
    }


    /**
     * part of test use case 2.3 - Login
     */
    @Test
    public void testLoginFailAlreadySubscribeLogged() {
        setUpLogedInUser();
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        assertFalse(logicManager.login(data.getId(Data.VALID2), subscribe.getName(),
                subscribe.getPassword()).getValue());
        tearDownLogin();
    }



    /**
     * part of test use case 2.3 - Login
     */
    @Test
    public void testLoginFailAlreadyUserLogged() {
        setUpLogedInUser();
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        assertFalse(logicManager.login(data.getId(Data.VALID), subscribe.getName(),
                subscribe.getPassword()).getValue());
        tearDownLogin();
    }


    /**
     * use case 2.4.1 - view all stores details
     */
    @Override @Test
    @Transactional
    public void testViewDataStores() {
        setUpOpenedStore();
        List<StoreData> expected = new LinkedList<>();
        expected.add(data.getStore(Data.VALID));
        List<StoreData> storeDataList  = logicManager.viewStores().getValue();
        assertNotEquals(null, storeDataList);
        for (StoreData storeData : storeDataList) {
            boolean result = false;
            for(StoreData real: expected) {
                if(real.getName().equals(storeData.getName())) {
                    result = true;
                    break;
                }
            }
            assertTrue(result);
        }
        tearDownOpenStore();
    }

    /**
     * use case 2.4.2 - view the products in some store test
     */
    @Override @Test
    @Transactional
    public void testViewProductsInStore() {
        setUpProductAdded();
        String storeName = data.getStore(Data.VALID).getName();
        for (ProductData productData: logicManager.viewProductsInStore(storeName).getValue()) {
            assertTrue(daos.getStoreDao().find(storeName).getProducts().containsKey(productData.getProductName()));
        }
        tearDownOpenStore();
    }

    /**
     * part of use case 2.5 - view spesific products
     */
    @Override
    @Test
    @Transactional
    public void testViewSpecificProductFilterProductName() {
        setUpProductAdded();
        Filter filter = data.getFilter(Data.VALID);
        filter.setSearch(Search.PRODUCT_NAME);
        List<ProductData> products = logicManager.viewSpecificProducts(filter).getValue();
        assertNotNull(products);
        assertEquals(1,products.size());
        tearDownOpenStore();
    }


    /**
     * part of use case 2.5 - view spesific products
     */
    @Test
    @Override
    @Transactional
    public void testViewSpecificProductFilterKeyWord() {
        setUpProductAdded();
        Filter filter = data.getFilter(Data.VALID);
        filter.setSearch(Search.KEY_WORD);
        List<ProductData> products = logicManager.viewSpecificProducts(filter).getValue();
        assertNotNull(products);
        assertEquals(1,products.size());
        tearDownOpenStore();

    }

    /**
     * part of use case 2.5 - view spesific products
     */
    @Test
    @Override
    @Transactional
    public void testViewSpecificProductFilterNone() {
        setUpProductAdded();
        Filter filter = data.getFilter(Data.VALID);
        filter.setSearch(Search.NONE);
        List<ProductData> products = logicManager.viewSpecificProducts(filter).getValue();
        assertNotNull(products);
        assertEquals(1,products.size());
        tearDownOpenStore();
    }


    /**
     * part of use case 2.5 - view specific product
     */
    private void testViewSpecificProductSearch(Filter correct, int listSize, Filter wrong) {
        //SUCCESS
        List<ProductData> products = logicManager.viewSpecificProducts(correct).getValue();
        assertFalse(products.isEmpty()); //sepose to be 1 product valid
        assertEquals(listSize, products.size());
        ProductData result = products.get(0);
        ProductData expected = data.getProductData(Data.VALID);
        assertTrue(data.compareProductData(expected, result));
        //FAIL
        products = logicManager.viewSpecificProducts(wrong).getValue();
        assertTrue(products.isEmpty());
    }

    /**
     * part of use case 2.5 - view specific product
     */
    @Test
    @Transactional
    public void testViewSpecificProductSearchNone() {
        setUpProductAdded();
        //SUCCESS ALWAYS
        Filter filter = data.getFilter(Data.VALID);
        filter.setSearch(Search.NONE);
        List<ProductData> products = logicManager.viewSpecificProducts(filter).getValue();
        assertFalse(products.isEmpty()); //sepose to be 1 product valid
        assertEquals(1, products.size());
        ProductData result = products.get(0);
        ProductData expected = data.getProductData(Data.VALID);
        assertTrue(data.compareProductData(expected, result));
        tearDownOpenStore();
    }

    /**
     * part of use case 2.5 - view specific product
     */
    @Test
    @Transactional
    public void testViewSpecificProductSearchByName() {
        setUpProductAdded();
        Filter correct = data.getFilter(Data.VALID);
        correct.setSearch(Search.PRODUCT_NAME);
        Filter wrong = data.getFilter(Data.WRONG_NAME);
        testViewSpecificProductSearch(correct, 1,wrong);
        tearDownOpenStore();
    }

    /**
     * part of use case 2.5 - view specific product
     */
    @Test
    @Transactional
    public void testViewSpecificProductSearchByCategory() {
        setUpProductAdded();
        ProductData productData = data.getProductData(Data.VALID);
        Filter correct = data.getFilter(Data.VALID);
        correct.setSearch(Search.CATEGORY);
        correct.setValue(productData.getCategory());
        Filter wrong = data.getFilter(Data.WRONG_CATEGORY);
        testViewSpecificProductSearch(correct, 1,wrong);
        tearDownOpenStore();
    }

    /**
     * part of use case 2.5 - view specific product
     */
    @Test
    @Transactional
    public void testViewSpecificProductSearchByKeyWord() {
        setUpProductAdded();
        ProductData productData = data.getProductData(Data.VALID);
        Filter correct = data.getFilter(Data.VALID);
        correct.setSearch(Search.KEY_WORD);
        correct.setValue(productData.getProductName());
        Filter wrong = data.getFilter(Data.WRONG_KEY_WORD);
        testViewSpecificProductSearch(correct, 1,wrong);
        tearDownOpenStore();
    }

    /**
     * part of use case 2.5 - view specific product
     */
    private void testViewSpecificProductFillter(Filter correct, Filter wrong) {
        //SUCCESS
        List<ProductData> products = logicManager.viewSpecificProducts(correct).getValue();
        assertFalse(products.isEmpty());
        int size = 0;
        for(Store s: daos.getStoreDao().getAll()) {
            size+=s.getProducts().size();
        }
        assertEquals(size, products.size());
        //FAIL
        products = logicManager.viewSpecificProducts(wrong).getValue();
        assertTrue(products.isEmpty());
    }

    /**
     * part of use case 2.5 - view specific product
     */
    @Test
    @Transactional
    public void testViewSpecificProductFillterMin() {
        setUpProductAdded();
        Filter correct = data.getFilter(Data.FILTER_MIN);
        Filter wrong = data.getFilter(Data.NEGATIVE_MIN);
        testViewSpecificProductFillter(correct, wrong);
        tearDownOpenStore();
    }

    /**
     * part of use case 2.5 - view specific product
     */
    @Test
    @Transactional
    public void testViewSpecificProductFillterMax() {
        setUpProductAdded();
        Filter correct = data.getFilter(Data.FILTER_MAX);
        Filter wrong = data.getFilter(Data.NEGATIVE_MAX);
        testViewSpecificProductFillter(correct, wrong);
        tearDownOpenStore();
    }

    /**
     * part of use case 2.5 - view specific product
     */
    @Test
    @Transactional
    public void testViewSpecificProductFillterCategory() {
        setUpProductAdded();
        Filter correct = data.getFilter(Data.FILTER_ALL_CATEGORIES);
        Filter wrong = data.getFilter(Data.WRONG_CATEGORY);
        testViewSpecificProductFillter(correct, wrong);
        tearDownOpenStore();
    }


    /**
     * use case 2.7.1 - watch cart details
     * success test
     */
    @Override @Test
    public void testWatchCartDetails() {
        super.testWatchCartDetails();
        ProductData productData = data.getProductData(Data.VALID);
        CartData cartData = logicManager.watchCartDetails(data.getId(Data.VALID)).getValue();
        List<ProductData> list = cartData.getProducts();
        assertEquals(1, list.size());
        assertEquals(list.get(0).getProductName(), productData.getProductName());
        tearDownProductAddedToCart();
    }

    /**
     * use case 2.7.2 - delete product from cart
     * success test
     */
    @Override @Test
    @Transactional
    public void testDeleteProductFromCart() {
        setUpProductAddedToCart();
        ProductData productData = data.getProductData(Data.VALID);
        assertTrue(logicManager.deleteFromCart(data.getId(Data.VALID),productData.getProductName(),productData.getStoreName()).getValue());
        Subscribe sub =daos.getSubscribeDao().find(data.getSubscribe(Data.VALID).getName());
        Basket basket=sub.getCart().getBasket(productData.getStoreName());
        assertNull(basket.getProducts().get(productData.getProductName()));
        tearDownProductAddedToCart();
    }

    /**
     * use case 2.7.3 - edit amount of product in cart
     * success test
     */
    @Override @Test
    @Transactional
    public void testEditProductsInCart() {
        setUpProductAddedToCart();
        ProductData productData = data.getProductData(Data.VALID);
        assertTrue(logicManager.editProductInCart(data.getId(Data.VALID),productData.getProductName(),productData.getStoreName(),2).getValue());
        assertEquals(2, logicManager.watchCartDetails(data.getId(Data.VALID)).getValue().getProducts().get(0).getAmount());
        tearDownProductAddedToCart();
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
        tearDownProductAddedToCart();
    }


    /**
     * use case 2.7.4
     * test add product when every thing right
     */
    private void testAddProductToCartValid() {
        ProductData product = data.getProductData(Data.VALID);
        assertTrue(logicManager.addProductToCart(data.getId(Data.VALID),product.getProductName(),
                product.getStoreName(), product.getAmount()).getValue());
        assertEquals(1, logicManager.watchCartDetails(data.getId(Data.VALID)).getValue().getProducts().size());
        tearDownProductAddedToCart();
    }

    /**
     * use case 2.7.4
     * test add product when the basket is null
     */
    private void testAddProductToCartBasketNull() {
        ProductData product = data.getProductData(Data.WRONG_STORE);
        assertFalse(logicManager.addProductToCart(data.getId(Data.VALID),product.getProductName(),
                product.getStoreName(), product.getAmount()).getValue());
    }

    /**
     * use case 2.8 - test reserveCart Products
     * success tests
     * notification
     */
    @Override
    @Test
    @Transactional
    public void testSuccessBuyProducts() {
        setUpProductAddedToCart();
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        String address = data.getDeliveryData(Data.VALID).getAddress();
        String country = data.getDeliveryData(Data.VALID).getCountry();
        assertTrue(logicManager.purchaseCart(data.getId(Data.VALID), country, paymentData, address).getValue());
        //check notification
        HashMap<Integer, List<Notification>> notifications=publisher.getNotificationList();
        List<ProductPeristentData> productDataList= (List<ProductPeristentData>) notifications.get(data.getId(Data.VALID)).get(0).getValue();
        ProductData expected=data.getProductData(Data.VALID);
        ProductPeristentData actual=productDataList.get(0);
        assertEquals(actual.getProductName(),expected.getProductName());
        assertEquals(actual.getStore(),expected.getStoreName());
        assertEquals(actual.getCategory(),expected.getCategory());
        assertEquals(actual.getAmount(),expected.getAmount());
        assertEquals(actual.getPrice(),expected.getPrice(),0.001);
        tearDownProductAddedToCart();
    }


    /**
     * use case 2.8 - test buy Cart
     */
    @Test
    @Transactional
    public void testBuyCartInvalidCountry() {
        setUpProductAddedToCart();
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        String address = data.getDeliveryData(Data.VALID).getAddress();
        String country = data.getDeliveryData(Data.INVALID_COUNTRY).getCountry();
        List<String> contries=new ArrayList<>();
        contries.add("Israel");
        PurchasePolicy policy = new UserPurchasePolicy(contries);
        GsonBuilder builderPolicy = new GsonBuilder();
        builderPolicy.registerTypeAdapter(PurchasePolicy.class,new InterfaceAdapter());
        Gson policyGson = builderPolicy.create();
        String policyToAdd = policyGson.toJson(policy, PurchasePolicy.class);
        logicManager.updatePolicy(data.getId(Data.VALID),policyToAdd,data.getStore(Data.VALID).getName());
        assertFalse(logicManager.purchaseCart(data.getId(Data.VALID), country, paymentData, address).getValue());
        checkBuyDidntWork();
    }

    /**
     * use case 2.8 - buy Cart
     */
    @Test @Override
    @Transactional
    public void testBuyCartPaymentSystemCrashed() {
        super.testBuyCartPaymentSystemCrashed();
        checkBuyDidntWork();
        tearDownProductAddedToCart();
    }

    /**
     * use case 2.8 - buy Cart
     */
    @Test
    @Transactional
    public void testBuyCartSupplySystemCrashed() {
        super.testBuyCartSupplySystemCrashed();
        checkBuyDidntWork();
        tearDownProductAddedToCart();
    }

    /**
     * use case 2.8 - test buy Cart
     */
    @Test
    @Transactional
    public void testBuyCartSupplySystemCrashedAndPaymentCancel() {
        super.testBuyCartSupplySystemCrashedAndPaymentCancel();
        checkBuyDidntWork();
        tearDownProductAddedToCart();
    }

    /**
     * use case 2.8 - test buy Cart
     */
    @Test
    @Transactional
    public void testBuyCartNullPayment(){
        super.testBuyCartNullPayment();
        checkBuyDidntWork();
        tearDownProductAddedToCart();
    }

    /**
     * use case 2.8 - test buy Cart
     */
    @Test
    @Transactional
    public void testBuyCartNullAddressPayment() {
        super.testBuyCartNullAddressPayment();
        checkBuyDidntWork();
        tearDownProductAddedToCart();
    }

    /**
     * use case 2.8 - test buy Cart
     */
    @Test
    @Transactional
    public void testBuyCartEmptyAddressPayment() {
        super.testBuyCartEmptyAddressPayment();
        checkBuyDidntWork();
        tearDownProductAddedToCart();
    }

    /**
     * use case 2.8 - test buy Cart
     */
    @Test
    @Transactional
    public void testBuyCartEmptyPayment() {
        super.testBuyCartEmptyPayment();
        checkBuyDidntWork();
        tearDownProductAddedToCart();
    }

    /**
     * use case 2.8 - test buy Cart
     */
    @Test
    @Transactional
    public void testBuyCartPaymentNullName() {
        super.testBuyCartPaymentNullName();
        checkBuyDidntWork();
        tearDownProductAddedToCart();
    }

    /**
     * use case 2.8 - test buy Cart
     */
    @Test
    @Transactional
    public void testBuyCartPaymentEmptyName(){
        super.testBuyCartPaymentEmptyName();
        checkBuyDidntWork();
        tearDownProductAddedToCart();
    }

    /**
     * use case 2.8 - test buy Cart
     */
    @Test
    @Transactional
    public void testBuyCartNullAddress() {
        super.testBuyCartNullAddress();
        checkBuyDidntWork();
        tearDownProductAddedToCart();
    }

    /**
     * use case 2.8 - test buy Cart
     */
    @Test
    @Transactional
    public void testBuyCartEmptyAddress() {
        super.testBuyCartEmptyAddress();
        checkBuyDidntWork();
        tearDownProductAddedToCart();
    }

    /**
     * use case 2.8 - test buy Cart
     */
    @Test
    @Transactional
    public void testBuyCartEmptyCountry() {
        super.testBuyCartEmptyCountry();
        checkBuyDidntWork();
        tearDownProductAddedToCart();
    }

    /**
     * use case 2.8 - test buy Cart
     */
    @Test
    @Transactional
    public void testBuyCartNullCountry() {
        super.testBuyCartNullCountry();
        checkBuyDidntWork();
        tearDownProductAddedToCart();
    }

    /**
     * check cart didnt change
     * check products in store didnt change
     * check there are no notifications
     */
    private void checkBuyDidntWork() {
        Cart cart=daos.getCartDao().find(data.getSubscribe(Data.VALID).getName());
        assertEquals(cart.getBaskets().size(),1);
        Store store=daos.getStoreDao().find(data.getStore(Data.VALID).getName());
        Map<String,Basket> basketMap=new HashMap<>(cart.getBaskets());
        Basket basket=basketMap.get(store.getName());
        assertFalse(basket.getProducts().isEmpty());
        assertEquals(store.getProducts().get(data.getProductData(Data.VALID).getProductName()).getAmount(),1);
        //check no notifications
        assertTrue(((StubPublisher)SinglePublisher.getInstance()).getNotificationList().isEmpty());
    }

    /**
     * use case 2.8 - test reserveCart Products
     */
    @Test
    @Transactional
    public void testBuyCart() {
        setUpProductAddedToCart();
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        String address = data.getDeliveryData(Data.VALID).getAddress();
        String country = data.getDeliveryData(Data.VALID).getCountry();
        assertTrue(logicManager.purchaseCart(data.getId(Data.VALID),country, paymentData, address).getValue());
        List<Purchase> purchaseList = daos.getSubscribeDao().find(data.getSubscribe(Data.VALID).getName()).watchMyPurchaseHistory().getValue();
        for (Purchase purchase: purchaseList) {
            String storeName = purchase.getStoreName();
            Store store = daos.getStoreDao().find(storeName);
            assertEquals(store.getPurchases().get(0),purchase);
        }
        tearDownProductAddedToCart();
    }


    /**
     * logout twice from same user
     */
    @Test
    public void logoutTwice() {
        setUpLogedInUser();
        int id = data.getId(Data.VALID);
        assertTrue(logicManager.logout(id).getValue());
        assertFalse(logicManager.logout(id).getValue());
    }

    /**
     * part of test use case 3.2 - Open Store
     */
    @Test
    public void testOpenStoreSuccess(){
        setUpLogedInUser();
        StoreData storeData = data.getStore(Data.VALID);
        assertTrue(logicManager.openStore(data.getId(Data.VALID), storeData).getValue());
        daos.getStoreDao().removeStore(storeData.getName());
        tearDownLogin();
    }

    /**
     * part of test use case 3.2 - Open Store
     */
    @Test
    public void testOpenStoreReopen() {
        setUpLogedInUser();
        StoreData storeData = data.getStore(Data.VALID);
        assertTrue(logicManager.openStore(data.getId(Data.VALID), storeData).getValue());
        assertFalse(logicManager.openStore(data.getId(Data.VALID), storeData).getValue());
        this.daos.getStoreDao().removeStore(storeData.getName());
        tearDownLogin();
    }

    /**
     * part of test use case 3.2 - Open Store
     */
    @Test
    public void testOpenStorePurchesAndDiscontPolicy() {
        setUpLogedInUser();
        StoreData storeData = data.getStore(Data.VALID);
        Store store = daos.getStoreDao().find(storeData.getName());
        assertEquals(store.getDescription(),"description");
        tearDownLogin();
    }

    /**
     * part of test use case 3.2 - Open Store
     */
    @Test
    public void testOpenStoreUserPermissions() {
        setUpLogedInUser();
        StoreData storeData = data.getStore(Data.VALID);
        Subscribe subscribe = (Subscribe)currUser.getState();
        Permission permission = subscribe.getPermissions().get(storeData.getName());
        assertTrue(permission.getPermissionType().contains(PermissionType.OWNER));
        tearDownLogin();
    }

    /**
     * part of test use case 3.2 - Open Store
     */
    @Test
    public void testOpenStoreStorePermissions() {
        setUpLogedInUser();
        StoreData storeData = data.getStore(Data.VALID);
        Store store = stores.get(storeData.getName());
        assertTrue(store.getPermissions().containsKey(currUser.getUserName()));
        Permission p = store.getPermissions().get(currUser.getUserName());
        assertTrue(p.getPermissionType().contains(PermissionType.OWNER));
        tearDownLogin();
    }


    /**
     * use case 3.3 - write review
     */
    @Override
    @Test
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
        assertTrue(logicManager.addReview(data.getId(Data.VALID), review.getStore(),review.getProductName(),review.getContent()).getValue());
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
        assertFalse(logicManager.addReview(data.getId(Data.VALID), review.getStore(),review.getProductName(),review.getContent()).getValue());
    }

    /**
     * use case 3.5 -add request
     */
    //@Override
    //@Test
    public void testAddRequest(){
        setUpOpenedStore();
        testSubscribeAddRequestSuccess();
        //super.testAddRequest();
    }

    /**
     * part of use case 3.5 -add request
     */
    private void testSubscribeAddRequestSuccess() {
        Request request = data.getRequest(Data.VALID);
        assertTrue(logicManager.addRequest(data.getId(Data.VALID),request.getStoreName(), request.getContent()).getValue());

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

    /**
     * use case 3.7 - watch purchase history
     */
    @Override @Test
    public void testWatchPurchaseHistory() {
        setUpBoughtProduct();
        List<Purchase> purchases = logicManager.watchMyPurchaseHistory(data.getId(Data.VALID)).getValue();
        assertNotNull(purchases);
        assertEquals(1,purchases.size());
    }


    /**
     * use case 4.1.1
     * test adding product with name that is not unique
     */
    //TODO fix it
    @Test
    public void testAddProductWithSameName() {
        assertFalse(logicManager.addProductToStore(data.getId(Data.VALID),data.getProductData(Data.SAME_NAME)).getValue());
    }

    /**
     * use case 4.1.1
     * test that a product added to the store
     */
    @Test
    @Transactional
    public void testAddProductToStore() {
        setUpOpenedStore();
        assertTrue(logicManager.addProductToStore(data.getId(Data.VALID),data.getProductData(Data.VALID)).getValue());
        tearDownOpenStore();
    }

    /**
     * use case 4.1.1
     * test try adding product without being owner or manager of the store
     */
    @Test
    public void testAddProductNotManagerOfStore() {
         setUpOpenedStore();
        String validStoreName = data.getProductData(Data.VALID).getStoreName();
        Subscribe sub = ((Subscribe) currUser.getState());
        Permission permission = sub.getPermissions().get(validStoreName);
        Store store=permission.getStore();
        sub.getPermissions().clear();
        assertFalse(logicManager.addProductToStore(data.getId(Data.VALID),data.getProductData(Data.VALID)).getValue());
        sub.getPermissions().put(validStoreName,permission);
        assertFalse(store.getProducts().containsKey(data.getProductData(Data.VALID).getProductName()));
    }

    /**
     * use case 4.1.1
     * test that user that has no CRUD permission or owner permission cant add products to store
     */
    @Test
    public void testAddProductDontHavePermission() {
        setUpOpenedStore();
        String validStoreName = data.getProductData(Data.VALID).getStoreName();
        Subscribe sub = ((Subscribe) currUser.getState());
        Permission permission = sub.getPermissions().get(validStoreName);
        Store store=permission.getStore();
        permission.removeType(PermissionType.OWNER);
        assertFalse(logicManager.addProductToStore(data.getId(Data.VALID),data.getProductData(Data.VALID)).getValue());
        permission.addType(PermissionType.OWNER);
        assertFalse(store.getProducts().containsKey(data.getProductData(Data.VALID).getProductName()));
    }

    @Test
    @Override
    public void testRemoveProductFromStore(){
        super.testRemoveProductFromStore();
    }

    /**
     * use case 4.1.2 test
     */
    @Override
    protected void testRemoveProductSuccess() {
        super.testRemoveProductSuccess();
        Subscribe sub=daos.getSubscribeDao().find(data.getSubscribe(Data.VALID).getName());
        assertFalse(sub.getPermissions().containsKey(data.getProductData(Data.VALID).getProductName()));
    }

    /**
     * part of use case 4.1.2 test
     */
    @Test
    public void checkRemoveProductNotManager() {
        setUpProductAdded();
        String validStoreName = data.getProductData(Data.VALID).getStoreName();
        Subscribe sub = daos.getSubscribeDao().find(data.getSubscribe(Data.VALID).getName());
        Permission permission = sub.getPermissions().get(validStoreName);
        Store store=permission.getStore();
        sub.getPermissions().clear();
        assertFalse(sub.removeProductFromStore(data.getProductData(Data.VALID).getProductName(),validStoreName).getValue());
        sub.getPermissions().put(validStoreName,permission);
        assertTrue(store.getProducts().containsKey(data.getProductData(Data.VALID).getProductName()));
    }

    /**
     * part of use case 4.1.2 test
     */
    @Test
    public void checkRemoveProductHasNoPermission() {
        setUpProductAdded();
        String validStoreName = data.getProductData(Data.VALID).getStoreName();
        Subscribe sub = daos.getSubscribeDao().find(data.getSubscribe(Data.VALID).getName());
        Permission permission = sub.getPermissions().get(validStoreName);
        Store store=permission.getStore();
        permission.removeType(PermissionType.OWNER);
        assertFalse(sub.removeProductFromStore(data.getProductData(Data.VALID).getProductName(),validStoreName).getValue());
        permission.addType(PermissionType.OWNER);
        assertTrue(store.getProducts().containsKey(data.getProductData(Data.VALID).getProductName()));
    }

    /**
     * use case 4.1.3 edit product
     */
    @Override @Test
    public void testEditProductSuccess() {
        setUpProductAdded();
        assertTrue(logicManager.editProductFromStore(data.getId(Data.VALID),data.getProductData(Data.EDIT)).getValue());
        ProductData product=data.getProductData(Data.EDIT);
        Subscribe sub=daos.getSubscribeDao().find(data.getSubscribe(Data.VALID).getName());
        assertTrue(sub.getPermissions().get(product.getStoreName()).getStore()
                .getProducts().get(product.getProductName()).equal(product));
        tearDownOpenStore();
    }

    /**
     * edit product when not manager
     */
    @Test
    public void checkEditProductNotManager() {
        setUpProductAdded();
        String validStoreName = data.getProductData(Data.VALID).getStoreName();
        Subscribe sub = ((Subscribe) currUser.getState());
        Permission permission=sub.getPermissions().get(validStoreName);
        ProductData pData=data.getProductData(Data.EDIT);
        Store store=permission.getStore();
        sub.getPermissions().clear();
        assertFalse(sub.editProductFromStore(pData).getValue());
        assertFalse(store.getProducts().get(pData.getProductName()).equal(pData));
        sub.getPermissions().put(validStoreName,permission);
    }

    /**
     * edit product when not have crud products permission
     */
    @Test
    public void checkEditProductHasNoPermission() {
        setUpProductAdded();
        String validStoreName = data.getProductData(Data.VALID).getStoreName();
        Subscribe sub = ((Subscribe) currUser.getState());
        Permission permission=sub.getPermissions().get(validStoreName);
        ProductData pData=data.getProductData(Data.EDIT);
        Store store=permission.getStore();
        permission.removeType(PermissionType.OWNER);
        assertFalse(sub.editProductFromStore(data.getProductData(Data.VALID)).getValue());
        assertFalse(store.getProducts().get(pData.getProductName()).equal(pData));
        permission.addType(PermissionType.OWNER);
    }

    /**
     * use case 4.2.1.1 -add discount to store
     */
    @Override
    @Transactional
    public void testAddDiscountToStoreSuccessTest() {
        setUpProductAdded();
        super.testAddDiscountToStoreSuccessTest();
        Subscribe sub = daos.getSubscribeDao().find(data.getSubscribe(Data.VALID).getName());
        Store store=sub.getPermissions().get(data.getStore(Data.VALID).getName()).getStore();
        assertTrue(store.getDiscount().values().iterator().next() instanceof RegularDiscount);
        tearDownOpenStore();
    }

    /**
     * use case 4.2.1.2 -remove discount from store
     */
    @Test
    public void testDeleteDiscountFromStoreSuccessTest(){
        setUpDiscountAdded();
        int discountId=daos.getStoreDao().find(data.getStore(Data.VALID).getName()).getDiscount().values().iterator().next().getId();
        assertTrue(logicManager.deleteDiscountFromStore(data.getId(Data.VALID),discountId,
                    data.getStore(Data.VALID).getName()).getValue());
        Subscribe sub = daos.getSubscribeDao().find(data.getSubscribe(Data.VALID).getName());
        Store store=sub.getPermissions().get(data.getStore(Data.VALID).getName()).getStore();
        assertTrue(store.getDiscount().isEmpty());
        tearDownOpenStore();
    }

    /**
     * use case 4.2.1.3 -view discounts from store
     * test that the discount we added was added
     */
    @Test @Override
    public void testViewDiscountSuccess(){
        setUpDiscountAdded();
        String store=data.getStore(Data.VALID).getName();
        int discountId=daos.getStoreDao().find(store).getDiscount().values().iterator().next().getId();
        HashMap<Integer, String> discounts =logicManager.viewDiscounts(store).getValue();
        assertNotNull(discounts.get(discountId));
        try {
            GsonBuilder builderDiscount = new GsonBuilder();
            builderDiscount.registerTypeAdapter(Discount.class, new InterfaceAdapter());
            Gson discountGson = builderDiscount.create();
            RegularDiscount discount = (RegularDiscount) (discountGson.fromJson(discounts.get(discountId),Discount.class));
            assertEquals(discount.getProduct(),data.getProductData(Data.VALID).getProductName());
            assertEquals(discount.getPercantage(),10,0.001);
        }
        catch(Exception e){
            fail();
        }
        tearDownOpenStore();
    }

    /**
     * use case 4.2.2.1 - update policy for the store
     */
    @Override
    protected void testUpdatePolicyTest() {
        super.testUpdatePolicyTest();
        Subscribe subscribe = daos.getSubscribeDao().find(data.getSubscribe(Data.VALID).getName());
        Store store = subscribe.getPermissions().get(data.getStore(Data.VALID).getName()).getStore();
        assertNotNull(store.getPurchasePolicy());
    }

    @Override
    protected void testViewStorePolicyTest() {
        String store = data.getStore(Data.VALID).getName();
        String output = logicManager.viewPolicy(store).getValue();
        assertFalse(output.isEmpty());
    }

    @Test
    public void testViewStorePolicy(){
        super.testViewStorePolicy();
    }

    /**
     * use case 4.3 - manage owner - success
     */
    @Override
    protected void testManageOwnerSuccess() {
        super.testManageOwnerSuccess();
        checkPermissions(Data.VALID2);
    }

    /**
     * use case 4.3 - manage owner - fail
     */
    @Override
    protected void testManageOwnerFail() {
        super.testManageOwnerFail();
        String sName=data.getStore(Data.VALID).getName();
        assertFalse(logicManager.manageOwner(data.getId(Data.VALID),sName,sName).getValue());
        assertFalse(stores.get(sName).getPermissions().containsKey(sName));
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

    @Test
    @Override
    public void testAddManagerToStore(){
        super.testAddManagerToStore();
    }

    @Override
    protected void testAddManagerStoreSuccess() {
        super.testAddManagerStoreSuccess();
        checkPermissions(Data.ADMIN);
    }

    /**
     * use case 4.6.1 -add permission
     */
    @Test
    @Override
    public void testAddPermission(){
        super.testAddPermission();
    }

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
    @Test
    @Override
    public void testRemovePermission(){
        super.testRemovePermission();
    }
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
     * notification
     * remove Admin from being manager and check that niv was removed from being a manager recursively
     */

    @Override
    @Test
    public void testRemoveManager(){
        super.testRemoveManager();
    }
    @Override
    protected void testRemoveManagerSuccess() {
        Subscribe sub=(Subscribe) currUser.getState();
        Permission p=sub.getGivenByMePermissions().get(0);
        logicManager.login(data.getId(Data.ADMIN),data.getSubscribe(Data.ADMIN).getName(),data.getSubscribe(Data.ADMIN).getPassword());
        Subscribe niv=data.getSubscribe(Data.VALID2);
        logicManager.login(data.getId(Data.VALID2),niv.getName(),niv.getPassword());
        String storeName=p.getStore().getName();
        //add another manager
        p.getOwner().addManager(niv,storeName);
        super.testRemoveManagerSuccess();
        SinglePublisher.initPublisher(this.publisher);
        assertFalse(niv.getPermissions().containsKey(storeName));
        assertFalse(p.getOwner().getPermissions().containsKey(storeName));
        //check notifications
        HashMap<Integer, List<Notification>> notifications=publisher.getNotificationList();
        for(int i=0;i<=2;i+=2){
            assertEquals(data.getStore(Data.VALID).getName(),notifications.get(i).get(0).getValue());
        }
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

    /**
     * part of use case 4.9.1 - view request
     */
    private void testStoreViewRequestSuccess() {
        testAddRequest();
        StoreData storeData = data.getStore(Data.VALID);
        Store store = daos.getStoreDao().find(storeData.getName());
        List<Request> requests=new LinkedList<>(store.getRequests().values());
        List<RequestData> excepted = new LinkedList<>();
        for(Request r:requests)
            excepted.add(new RequestData(r));
        List<RequestData> actual = logicManager.viewStoreRequest(data.getId(Data.VALID), storeData.getName()).getValue();
        for(int i=0;i<=1;i++){
            assertEquals(actual.get(i).getStoreName(),excepted.get(i).getStoreName());
            assertEquals(actual.get(i).getComment(),excepted.get(i).getComment());
            assertEquals(actual.get(i).getContent(),excepted.get(i).getContent());
            assertEquals(actual.get(i).getId(),excepted.get(i).getId());
            assertEquals(actual.get(i).getSenderName(),excepted.get(i).getSenderName());
        }
    }

    /**
     * part of use case 4.9.1 - view request
     */
    private void testStoreViewRequestFail() {
        assertTrue(logicManager.viewStoreRequest(data.getId(Data.VALID), data.getStore(Data.NULL_NAME).getName()).getValue().isEmpty());
    }

    /**
     * test use case 4.9.2
     */
    @Override @Test
    public void testReplayRequest() {
        testReplayRequestSuccess();
        testReplayRequestFail();
    }

    /**
     * part of test use case 4.9.2
     * notification
     */
    private void testReplayRequestSuccess() {
        testAddRequest();
        Request request = data.getRequest(Data.VALID);
        //check the comment savePurchases
        StoreData storeData = data.getStore(Data.VALID);
        RequestData actual = logicManager.replayRequest(data.getId(Data.VALID),request.getStoreName(), request.getId(),
                "The milk is there, open your eyes!").getValue();
        Request excepted = stores.get(storeData.getName()).getRequests().get(request.getId());
        assertEquals(excepted.getId(),actual.getId());
        assertEquals(excepted.getComment(),actual.getComment());
        //check notifications
        HashMap<Integer, List<Notification>> notifications=publisher.getNotificationList();
        Request notificationRequest= (Request) notifications.get(data.getId(Data.VALID)).get((0)).getValue();
        assertEquals(data.getStore(Data.VALID).getName(),notificationRequest.getStoreName());
        assertEquals(actual.getContent(),notificationRequest.getContent());
        assertEquals(actual.getComment(),notificationRequest.getComment());
        assertEquals(actual.getSenderName(),notificationRequest.getSenderName());
    }

    /**
     * part of test use case 4.9.2
     */
    private void testReplayRequestFail() {
        Request request = data.getRequest(Data.WRONG_ID);
        assertNull(logicManager.replayRequest(data.getId(Data.VALID), request.getStoreName(), request.getId(), request.getContent()).getValue());
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
        checkValidPurchase(logicManager.watchStorePurchasesHistory(data.getId(Data.VALID), data.getStore(Data.VALID).getName()).getValue());
    }

    /**
     * test use case 6.4.1 - watch user history
     */
    @Override
    protected void testWatchUserHistorySuccess() {
        checkValidPurchase(logicManager.watchUserPurchasesHistory(data.getId(Data.ADMIN), data.getSubscribe(Data.VALID).getName()).getValue());
    }

    /**
     * test use case 6.4.2 user watch history when admin
     */
    protected void testWatchStoreHistorySuccessWhenAdmin() {
        checkValidPurchase(logicManager.watchStorePurchasesHistory(data.getId(Data.ADMIN), data.getStore(Data.VALID).getName()).getValue());
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

    /**
     * tests for GetStoresManagedByUsers
     */

    @Test
    public void testGetStoresManagedByUsersOwnerSuccess(){
        setUpOpenedStore();
        Response<List<StoreData>> response= logicManager.getStoresManagedByUser(data.getId(Data.VALID));
        assertNotNull(response.getValue());
        assertEquals(response.getReason(),OpCode.Success);


    }

    @Test
    public void testGetStoresManagedByUsersGuestFail(){
        setUpConnect();
        Response<List<StoreData>> response= logicManager.getStoresManagedByUser(data.getId(Data.VALID));
        assertNull(response.getValue());
        assertEquals(response.getReason(),OpCode.No_Stores_To_Manage);

    }

    @Test
    public void testGetStoresManagedByUsersNotManagerFail(){
        setUpLogedInUser();
        Response<List<StoreData>> response= logicManager.getStoresManagedByUser(data.getId(Data.VALID));
        assertNull(response.getValue());
        assertEquals(response.getReason(),OpCode.No_Stores_To_Manage);


    }

    /**
     *  tests for getPermissionsForStore
     */

    @Test
    public void  testGetPermissionsForStoreOwnerSuccess(){
        setUpOpenedStore();
        StoreData storeData = data.getStore(Data.VALID);
        Response<Set<StorePermissionType>> response=
                logicManager.getPermissionsForStore(data.getId(Data.VALID),
                        storeData.getName());
        assertTrue(response.getValue().contains(StorePermissionType.OWNER));
        assertEquals(response.getReason(),OpCode.Success);

    }
    @Test
    public void testGetPermissionsForStoreFailUserNotExist(){
        StoreData storeData = data.getStore(Data.VALID);
        Response<Set<StorePermissionType>> response=
                logicManager.getPermissionsForStore(-1,
                        storeData.getName());
        assertNull(response.getValue());
        assertEquals(response.getReason(),OpCode.Dont_Have_Permission);
    }

    @Test
    public void testGetPermissionsForStoreFailStoreNotExist(){
        Response<Set<StorePermissionType>> response=
                logicManager.getPermissionsForStore(data.getId(Data.VALID),
                        "invalidStore");
        assertNull(response.getValue());
        assertEquals(response.getReason(),OpCode.Dont_Have_Permission);

    }

    /**
     * tests for getManagersOfStore
     * success
     */
    @Test
    public void  testGetManagersOfStoreSuccess(){
        setUpManagerAddedSubManagerAdded();
        StoreData storeData = data.getStore(Data.VALID);
        List<String> expectedManager=new ArrayList<>();
        expectedManager.add(data.getSubscribe(Data.VALID).getName());
        expectedManager.add(data.getSubscribe(Data.VALID2).getName());
        expectedManager.add(data.getSubscribe(Data.ADMIN).getName());
        List<String> mangers=logicManager.getManagersOfStore(storeData.getName()).getValue();
        assertEquals(expectedManager,mangers);
    }

    /**
     * getManagersOfStoreUserManaged tests
     */
    @Test
    public void getManagersOfStoreUserManagedSuccess(){
        setUpManagerAddedSubManagerAdded();
        List<String> managers=logicManager.getManagersOfStoreUserManaged(data.getId(Data.VALID),
                data.getStore(Data.VALID).getName()).getValue();
        List<String> expectedManagers=new LinkedList<>();
        expectedManagers.add(data.getSubscribe(Data.ADMIN).getName());
        assertEquals(managers,expectedManagers);
    }

    /**
     * get all the users for the admin
     */
    @Test
    public void testGetAllUsersNotAnAdmin() {
        setUpRegisteredUser();
        List<String> users = logicManager.getAllUsers(data.getId(Data.VALID)).getValue();
        assertTrue(users.isEmpty());
    }

    @Test @Override
    public void testGetAllUsers(){
        super.testGetAllUsers();
    }


    /**
     * tear down
     */

    private void tearDownProductAddedToCart() {
        daos.getSubscribeDao().remove(data.getSubscribe(Data.VALID).getName());
        tearDownOpenStore();
    }

//    private void tearDownProductBought() {
//        tearDownProductAddedToCart();
//    }
}

