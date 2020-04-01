package UserTests;

import Data.*;
import DataAPI.StoreData;
import Domain.*;
import Systems.PaymentSystem.PaymentSystem;
import Systems.PaymentSystem.ProxyPayment;
import Systems.SupplySystem.ProxySupply;
import Systems.SupplySystem.SupplySystem;
import org.junit.Before;

import static org.junit.Assert.*;

public class UserRealTest extends UserAllStubsTest{
    @Before
    public void setUp(){
        testData=new TestData();
        user=new User();
    }

    //don't need init cause the state is changing in login method
    @Override
    protected void initSubscribe(){
        return;
    }

    /**
     * test use case 2.3 - Login
     * user: niv shirazi
     */
    protected void testLoginGuest(){
        super.testLoginGuest();
        assertEquals(user.getPassword(),"shirazi");
        assertEquals(user.getUserName(),"niv");
    }

    @Override
    protected void testAddProductToStoreSubscribe() {
        super.testAddProductToStoreSubscribe();
        Product product=((Subscribe) user.getState()).getPermissions().get(testData.getStore(Data.VALID).getName()).
                getStore().getProducts().get(testData.getProduct(Data.VALID).getProductName());
        assertTrue(product.equal(testData.getProduct(Data.VALID)));
    }
}
