package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.CartTestData;
import AcceptanceTests.AcceptanceTestDataObjects.ProductTestData;
import AcceptanceTests.AcceptanceTestDataObjects.StoreTestData;
import AcceptanceTests.SystemMocks.*;
import Systems.PaymentSystem.PaymentSystem;
import Systems.SupplySystem.SupplySystem;
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
        userId = bridge.connect();
    }

    public void positiveSetUp(){
        PaymentSystem paymentSystem = new PaymentSystemMockAllPositive();
        SupplySystem deliverySystem = new DeliverySystemMockAllPositive();
        setUp(paymentSystem,deliverySystem);
    }

    @Test
    public void buyCartSuccess(){
        positiveSetUp();
        addProductToCart(1);
        boolean approval = bridge.buyCart(userId,validPayment,validDelivery);
        assertTrue(approval);
        //TODO: won't pass because logic won't empty cart
        CartTestData currCart = bridge.getUsersCart(userId);
        assertTrue(currCart.isEmpty());
    }

    @Test
    public void buyCartFailEmptyCart(){
        buyCartSuccess();
        //TODO: won't pass because logic won't empty cart
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

}
