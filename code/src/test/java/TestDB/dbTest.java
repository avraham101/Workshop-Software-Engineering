package TestDB;

import Data.Data;
import Data.TestData;
import Domain.Store;
import Domain.Subscribe;
import Persitent.CategoryDao;
import Persitent.ProductDao;
import Persitent.StoreDao;
import Persitent.SubscribeDao;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class dbTest {

    private StoreDao storeDao;
    private SubscribeDao subscribeDao;
    private ProductDao productDao;
    private TestData data;


    @Before
    public void setUp() {
        data = new TestData();
        subscribeDao = new SubscribeDao();
        storeDao = new StoreDao();
        productDao = new ProductDao(new CategoryDao());
    }

    @Test
    public void testAddSubscribeToDB() {
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        assertTrue(subscribeDao.addSubscribe(subscribe));
        assertNotNull(subscribeDao.find(subscribe.getName()));
        subscribeDao.remove(subscribe.getName());
        assertNull(subscribeDao.find(subscribe.getName()));
    }

    @Test
    public void testAddStoreToDB() {
        Store store = data.getRealStore(Data.VALID);
        assertTrue(storeDao.addStore(store));
        assertNotNull(storeDao.find(store.getName()));
        storeDao.removeStore(store.getName());
        assertNull(storeDao.find(store.getName()));
    }



}
