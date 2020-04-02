package LogicManagerTests;

import Data.Data;
import DataAPI.StoreData;
import Domain.Store;
import org.junit.Before;

import static org.junit.Assert.*;

public class
LogicManagerUserStubTest extends LogicManagerUserAndStoresStubs {

    @Before
    public void setUp() {
        super.setUp();
    }

    /**
     * test use case 3.2 - Open Store
     */
    @Override
    protected void testOpenStore() {
        super.testOpenStore();
        StoreData storeData = data.getStore(Data.VALID);
        Store store = stores.get(storeData.getName());
        //This test check if store added
        assertNotNull(store);
        assertEquals(storeData.getName(),store.getName());
        //This test check if can add store twiced
        assertFalse(logicManager.openStore(data.getStore(Data.VALID)));
    }

    /**
     * part of test use case 3.2 - Open Store
     */
    @Override
    protected void testOpenStoreSucces(){
        StoreData storeData = data.getStore(Data.VALID);
        assertTrue(logicManager.openStore(storeData));
    }

}
