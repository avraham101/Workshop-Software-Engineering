package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.*;
import AcceptanceTests.SystemMocks.*;
import Systems.PaymentSystem.PaymentSystem;
import Systems.SupplySystem.SupplySystem;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * use case 2.8 - purchase cart
 */

public class BuyCartTest extends AcceptanceTests {
    private int userId;
    public void setUp(PaymentSystem paymentSystem , SupplySystem deliverySystem){
        //bridge.initialStart(admin.getUsername(), admin.getPassword(),paymentSystem,deliverySystem);
        //setUpUsers();
        setExternalSystems(paymentSystem,deliverySystem);
        super.setUpAll();
        addStores(stores);
        addProducts(products);

        userId =bridge.connect() ;

    }

    public void positiveSetUp(){
        PaymentSystem paymentSystem = new PaymentSystemMockAllPositive();
        SupplySystem deliverySystem = new DeliverySystemMockAllPositive();
        setUp(paymentSystem,deliverySystem);
        logoutAndLogin(admin);
        logoutAndLogin(stores.get(0).getStoreOwner());
    }

    @Test
    public void buyCartSuccess(){
        positiveSetUp();

        PublisherMock publisherMock=new PublisherMock();
        bridge.setPublisher(publisherMock);

        addProductToCart(1);
        boolean approval = bridge.buyCart(userId,validPayment,validDelivery);
        assertTrue(approval);
        CartTestData currCart = bridge.getUsersCart(userId);
        assertTrue(currCart.isEmpty());
        //check notification
        assertFalse(publisherMock.getNotificationList().isEmpty());
    }

    @Test
    public void buyCartSuccessNotification(){
        positiveSetUp();
        PublisherMock publisherMock=new PublisherMock();
        bridge.setPublisher(publisherMock);
        bridge.login(superUser.getId(),superUser.getUsername(),superUser.getPassword());
        addProductToCart(1);
        boolean approval = bridge.buyCart(userId,validPayment,validDelivery);
        assertTrue(approval);
        CartTestData currCart = bridge.getUsersCart(userId);
        assertTrue(currCart.isEmpty());
        //check notification
         assertFalse(publisherMock.getNotificationList().isEmpty());
       //  removeUser(user.getUsername());
    }

    @Test
    public void buyCartFailEmptyCart(){
        buyCartSuccess();
        assertFalse(bridge.buyCart(userId,validPayment,validDelivery));
    }
    @Test
    public void buyCartFailInvalidPayment(){
        positiveSetUp();
        addProductToCart(1);
        CartTestData expectedCart = bridge.getUsersCart(userId);
        assertFalse(bridge.buyCart(userId,invalidPayment,validDelivery));
        CartTestData actualCart = bridge.getUsersCart(userId);
        assertEquals(expectedCart,actualCart);
    }
    @Test
    public void buyCartFailInvalidDeliveryDetails(){
        positiveSetUp();
        addProductToCart(1);
        assertFalse(bridge.buyCart(userId,validPayment,invalidDelivery));
        assertFalse(bridge.getUsersCart(userId).isEmpty());
    }

    @Test
    public void buyCartFailProductNotInStore(){
        positiveSetUp();
        StoreTestData store = stores.get(0);
        ProductTestData product = store.getProducts().get(0);
        bridge.addToUserCart(userId,product,1);
        deleteProductFromStore(product);
        assertFalse(bridge.buyCart(userId,validPayment,validDelivery));

    }
    @Test
    public void buyCartFailInvalidAmount(){
        positiveSetUp();
        addProductToCart(2);
            changeAmountOfProductInStore(stores.get(0).getProducts().get(0),1);
        assertFalse(bridge.buyCart(userId,validPayment,validDelivery));
    }

    @Test
    public void buyCartFailPaymentSystemFail() {
        PaymentSystem paymentSystem = new PaymentSystemMockCantPay();
        SupplySystem deliverySystem = new DeliverySystemMockAllPositive();
        setUp(paymentSystem,deliverySystem);
        addProductToCart(1);
        assertFalse(bridge.buyCart(userId,validPayment,validDelivery));
    }
    @Test
    public void buyCartFailDeliverySystemFail() {
        PaymentSystem paymentSystem= new PaymentSystemMockAllPositive();
        SupplySystem deliverySystem = new DeliverySystemMockCantDeliver();
        setUp(paymentSystem,deliverySystem);
        addProductToCart(1);
        assertFalse(bridge.buyCart(userId,validPayment,validDelivery));
    }
    private void addProductToCart(int amount){
        bridge.addToUserCart(userId,
                stores.get(0).getProducts().get(0),amount);
    }

    @Test
    public void buyCartNotStandsInPolicy(){
        positiveSetUp();
        addProductToCart(2);
        bridge.login(superUser.getId(),superUser.getUsername(),superUser.getPassword());
        PurchasePolicyTestData purchasePolicyTestData = new PurchasePolicyTestData(1);
        boolean updateApproval= bridge.updatePolicy(superUser.getId(),purchasePolicyTestData,"store0Test");
        assertTrue(updateApproval);
        boolean approval = bridge.buyCart(userId,validPayment,validDelivery);
        assertFalse(approval);
        CartTestData currCart = bridge.getUsersCart(userId);
        assertFalse(currCart.isEmpty());
    }
    @After
    public void tearDown(){

        removeProducts(products);
        removeStores(stores);
    }

}
