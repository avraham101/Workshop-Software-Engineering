package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.DiscountTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ViewDeleteDiscountTest extends AcceptanceTests{

    private DiscountTestData discountTestData;
    private String store;

    @Before
    public void setUp(){
        addUserStoresAndProducts(superUser);
        discountTestData=new DiscountTestData(10,"cocacolaTest");
        store="store1Test";
        bridge.addDiscount(superUser.getId(),discountTestData,store);
    }

    @Test
    public void deleteDiscountSuccess(){
       List<DiscountTestData> discounts= bridge.getDiscountsOfStore(store);
       int discountId = discounts.get(0).getId() ;
       assertTrue(bridge.deleteDiscount(superUser.getId(),discountId,store));
        deleteDiscountNotExist();
    }

    private void deleteDiscountNotExist(){
        assertFalse(bridge.deleteDiscount(superUser.getId(),0,store));
    }

    @Test
    public void deleteDiscountInvalidStore(){
        assertFalse(bridge.deleteDiscount(superUser.getId(),0,store+1));
    }

    @Test
    public void deleteDiscountNotMyStore(){
        assertFalse(bridge.deleteDiscount(users.get(1).getId(),0,store));
    }

    @Test
    public void viewDiscountsSuccess(){
        List<DiscountTestData> discountTestDataList=bridge.getDiscountsOfStore(store);
        assertNotNull(discountTestDataList);
        assertEquals(discountTestDataList.get(0),discountTestData);

    }

    @Test
    public void viewDiscountEmptyDiscountSuccess(){
        List<DiscountTestData>  discounts= bridge.getDiscountsOfStore(store);
        int discountId = discounts.get(0).getId();
        bridge.deleteDiscount(superUser.getId(),discountId,store);
        List<DiscountTestData> discountTestDataList=bridge.getDiscountsOfStore(store);
        assertNotNull(discountTestDataList);
        assertTrue(discountTestDataList.isEmpty());
    }

    @Test
    public void viewDiscountInvalidStore(){
        List<DiscountTestData> discountTestDataList=bridge.getDiscountsOfStore(store+1);
        assertNull(discountTestDataList);
    }

    @After
    public void tearDown(){
        removeProducts(products);
        removeStores(stores);
    }


}
