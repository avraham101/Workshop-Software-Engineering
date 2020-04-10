package Admin;

import Data.Data;
import Data.TestData;
import Domain.Admin;
import Domain.Subscribe;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdminTest  {

    private Admin admin;
    private TestData data;

    @Before
    public void setUp()  {
        data=new TestData();
        Subscribe s=data.getSubscribe(Data.ADMIN);
        admin=new Admin(s.getName(),s.getPassword());
    }

    /**
     * test use case 6.4.1 - watch user history
     */
    @Test
    public void testCanWatchUserHistory(){
        assertTrue(admin.canWatchUserHistory());
    }

    /**
     * test use case 6.4.2 and 4.10 - watch store history
     */
    @Test
    public void testStoreHistory(){
        assertTrue(admin.canWatchStoreHistory(data.getStore(Data.VALID).getName()));
    }
}
