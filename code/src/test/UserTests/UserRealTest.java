package UserTests;

import Data.*;
import Domain.*;
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
                getStore().getProducts().get(testData.getProductData(Data.VALID).getProductName());
        assertTrue(product.equal(testData.getProductData(Data.VALID)));
    }
}
