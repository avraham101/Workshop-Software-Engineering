package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.ProductTestData;
import AcceptanceTests.AcceptanceTestDataObjects.StoreTestData;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class GetStoresInfo extends AcceptanceTests {
    /**
    use case 2.4
     */
    @Before
    public void setUp(){
        addStores(stores);
        addProducts(products);
    }

    /***
     * use 2.4.1 - show the details about every store
     */
    @Test
    public void getStoresInfo(){
        Set<StoreTestData> storesInSystem = new HashSet<>(bridge.getStoresInfo());
        Set<StoreTestData> insertedStores = new HashSet<>(stores);
        assertEquals(storesInSystem,insertedStores);
    }

    /**
     * use case 2.4.2 - show the products of a given store
     */
    @Test
    public void getProductsOfStoreSuccess(){
        String targetStore = stores.get(0).getStoreName();
        Set<ProductTestData> productsInStoreSystem = new HashSet<>(bridge.getStoreProducts(targetStore));
        Set<ProductTestData> insertedProducts = new HashSet<>(stores.get(0).getProducts());
        assertEquals(productsInStoreSystem,insertedProducts);
    }
    @Test
    public void getProductOfStoreFail(){
        List<ProductTestData> productsInStore = bridge.getStoreProducts(notExistingStore.getStoreName());
        assertNull(productsInStore);
    }

}
