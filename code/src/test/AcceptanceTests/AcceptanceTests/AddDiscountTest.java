package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.DiscountTestData;
import Domain.Discount.Discount;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * acceptance test for use case 4.2.1.1
 */
public class AddDiscountTest extends AcceptanceTests {

    private DiscountTestData discountTestData;

    @Before
    public void setUp(){
        addUserStoresAndProducts(superUser);
        discountTestData=new DiscountTestData(10,"cocacolaTest");
    }

    @Test
    public void addDiscountSuccess(){
        assertTrue(bridge.addDiscount(superUser.getId(),discountTestData,"store1Test"));
    }

    @Test
    public void addDiscountNullProduct(){
        discountTestData.setProduct(null);
        assertFalse(bridge.addDiscount(superUser.getId(),discountTestData,"store1Test"));
    }

    @Test
    public void addDiscountOver100Percantage(){
        discountTestData.setPercentage(101);
        assertFalse(bridge.addDiscount(superUser.getId(),discountTestData,"store1Test"));
    }

    @Test
    public void addDiscount0Percentage(){
        discountTestData.setPercentage(0);
        assertFalse(bridge.addDiscount(superUser.getId(),discountTestData,"store1Test"));
    }

    @Test
    public void addDiscountNotExistStore(){
        assertFalse(bridge.addDiscount(superUser.getId(),discountTestData,"store12Test"));
    }

    @Test
    public void addDiscountNotMyStore(){
        assertFalse(bridge.addDiscount(users.get(1).getId(),discountTestData,"store1Test"));
    }

    @Test
    public void addDiscountProductNotInStore(){
        discountTestData.setProduct("appleTest");
        assertFalse(bridge.addDiscount(superUser.getId(),discountTestData,"store1Test"));
    }



}
