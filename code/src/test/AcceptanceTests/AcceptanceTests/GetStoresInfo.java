package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.ProductTestData;
import AcceptanceTests.AcceptanceTestDataObjects.StoreTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class GetStoresInfo extends AcceptanceTests {
    /*
    use case 2.4
     */
    @Before
    public void setUp(){
        super.setUp();
        bridge.addStores(stores);
        bridge.addProducts(products);
    }
    //use case 2.4.1
    @Test
    public void getStoresInfo(){
        Set <StoreTestData> storesInSystem = new HashSet<StoreTestData>( bridge.getStoresInfo());
        Set <StoreTestData> insertedStores = new HashSet<StoreTestData>(stores);
        assertEquals(storesInSystem,insertedStores);

    }
    //use case 2.4.2
    @Test
    public void getProductsOfStoreSuccess(){
        String targetStore = stores.get(0).getStoreName();
        Set<ProductTestData> productsInStoreSystem = new HashSet<ProductTestData>(bridge.getStoreProducts(targetStore));
        Set <ProductTestData> insertedProducts = new HashSet<>(stores.get(0).getProducts());
        assertEquals(productsInStoreSystem,insertedProducts);

    }

    @Test
    public void getProductOfStoreFail(){
        Set<ProductTestData> productsInStoreSystem = new HashSet<ProductTestData>(bridge.getStoreProducts(notExistingStore.getStoreName()));
        assertNull(productsInStoreSystem);


    }



    @After
    public void tearDown(){
        bridge.deleteStores(stores);
        bridge.deleteProducts(products);
    }

}
