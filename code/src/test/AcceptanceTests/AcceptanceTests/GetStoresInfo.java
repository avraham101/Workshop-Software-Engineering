package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.StoreTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class GetStoresInfo extends AcceptanceTests {

    @Before
    public void setUp(){
        super.setUp();
        bridge.addStores(stores);
        bridge.addProducts(products);
    }

    @Test
    public void getStoresInfo(){
        Set <StoreTestData> storesInSystem = new HashSet<StoreTestData>( bridge.getStoresInfo());
        Set <StoreTestData> insertedStores = new HashSet<StoreTestData>(stores);
        assertEquals(storesInSystem,insertedStores);

    }

    @Test
    public void getProductsOfStoreSuccess(){
        
    }

    @Test
    public void getProductOfStoreFail(){

    }



    @After
    public void tearDown(){
        bridge.deleteStores(stores);
        bridge.deleteProducts(products);
    }

}
