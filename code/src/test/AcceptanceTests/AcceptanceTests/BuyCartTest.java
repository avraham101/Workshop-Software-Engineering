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
    int userId;
    public void setUp(PaymentSystem paymentSystem , SupplySystem deliverySystem){
       bridge.initialStart(admin.getUsername(), admin.getPassword(),paymentSystem,deliverySystem);
       setUpUsers();
       addStores(stores);
       addProducts(products);
       userId= bridge.connect();
    }

    public void positiveSetUp(){
        PaymentSystem paymentSystem= new PaymentSystemMockAllPositive();
        SupplySystem deliverySystem = new DeliverySystemMockAllPositive();
        setUp(paymentSystem,deliverySystem);
    }

    @Test
    public void buyCartSuccess(){
        positiveSetUp();
        addProductToCart();
        boolean approval = bridge.buyCart(userId,validPayment,validDelivery);
        assertTrue(approval);
        CartTestData currCart = bridge.getUsersCart(userId);
        assertTrue(currCart.isEmpty());
    }

    @Test
    public void buyCartFailEmptyCart(){
        positiveSetUp();
        assertFalse(bridge.buyCart(userId,validPayment,validDelivery));
    }
    @Test
    public void buyCartFailInvalidPayment(){
        positiveSetUp();
        addProductToCart();
        CartTestData expectedCart = bridge.getUsersCart(userId);
        assertFalse(bridge.buyCart(userId,invalidPayment,validDelivery));
        CartTestData actualCart = bridge.getUsersCart(userId);
        assertEquals(expectedCart,actualCart);
    }
    @Test
    public void buyCartFailInvalidDeliveryDetails(){
        positiveSetUp();
        addProductToCart();
        assertFalse(bridge.buyCart(userId,validPayment,invalidDelivery));
        assertFalse(bridge.getUsersCart(superUser.getId()).isEmpty());
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
        addProductToCart();
        changeAmountOfProductInStore(stores.get(0).getProducts().get(0),0);
        assertFalse(bridge.buyCart(userId,validPayment,validDelivery));


    }

    @Test
    public void buyCartFailPaymentSystemFail() {
        PaymentSystem paymentSystem= new PaymentSystemMockCantPay();
        SupplySystem deliverySystem = new DeliverySystemMockAllPositive();
        setUp(paymentSystem,deliverySystem);
        addProductToCart();
        assertFalse(bridge.buyCart(userId,validPayment,validDelivery));
    }
    @Test
    public void buyCartFailDeliverySystemFail() {
        PaymentSystem paymentSystem= new PaymentSystemMockAllPositive();
        SupplySystem deliverySystem = new DeliverySystemMockCantDeliver();
        setUp(paymentSystem,deliverySystem);
        addProductToCart();
        assertFalse(bridge.buyCart(userId,validPayment,validDelivery));
    }
    private void addProductToCart(){
        bridge.addToUserCart(userId,
                stores.get(0).getProducts().get(0),1);
    }


}
