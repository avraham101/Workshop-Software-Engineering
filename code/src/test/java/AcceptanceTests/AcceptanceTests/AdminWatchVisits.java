package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.DateTestData;
import AcceptanceTests.AcceptanceTestDataObjects.DayVisitData;
import AcceptanceTests.AcceptanceTestDataObjects.StoreTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AdminWatchVisits extends AcceptanceTests {

    private DateTestData from;
    private DateTestData to;

    @Before
    public void setUpBefore(){
        bridge.login(admin.getId(),admin.getUsername(),admin.getPassword());
        from = new DateTestData(11,6,2020);
        to = new DateTestData(15,6,2020);
    }

    @After
    public void tearDown(){
        bridge.logout(admin.getId());
    }

    @Test
    public void testWatchVisitsNotAdmin() {
        bridge.logout(admin.getId());
        registerAndLogin(users.get(1));
        assertNull(bridge.watchVisitsBetweenDates(1,from, to));
        bridge.logout(1);
    }

    @Test
    public void testWatchVisitsInvalidToDate() {
        to.setYear(from.getYear() - 1);
        assertNull(bridge.watchVisitsBetweenDates(1,from, to));
    }

    @Test
    public void testWatchVisitsInvalidFromDate() {
        from.setYear(to.getYear() + 1);
        assertNull(bridge.watchVisitsBetweenDates(1,from, to));
    }

    @Test
    public void testAdminVisit() {
        List<DayVisitData> visits = bridge.watchVisitsBetweenDates(admin.getId(), from, to);
        int adminVisits = 0;
        for (DayVisitData dayVisit: visits) {
            adminVisits += dayVisit.getAdminVisit();
        }
        assertEquals(1, adminVisits);
    }

    @Test
    public void testAdminVisitLogoutLogin() {
        bridge.logout(admin.getId());
        bridge.login(admin.getId(),admin.getUsername(),admin.getPassword());
        List<DayVisitData> visits = bridge.watchVisitsBetweenDates(admin.getId(), from, to);
        int adminVisits = 0;
        for (DayVisitData dayVisit: visits) {
            adminVisits += dayVisit.getAdminVisit();
        }
        assertEquals(2, adminVisits);
    }

    @Test
    public void testOneSubVisit() {
        registerAndLogin(users.get(1));
        List<DayVisitData> visits = bridge.watchVisitsBetweenDates(admin.getId(), from, to);
        int subVisits = 0;
        for (DayVisitData dayVisit: visits) {
            subVisits += dayVisit.getSubVisit();
        }
        assertEquals(1, subVisits);
        bridge.logout(users.get(1).getId());
    }

    @Test
    public void testOneGuestSubVisit() {
        bridge.connect();
        List<DayVisitData> visits = bridge.watchVisitsBetweenDates(admin.getId(), from, to);
        int guestVisits = 0;
        for (DayVisitData dayVisit: visits) {
            guestVisits += dayVisit.getGuestVisit();
        }
        assertEquals(1, guestVisits);
        bridge.logout(users.get(1).getId());
    }

    @Test
    public void testManyGuestVisit() {
        int connects = 10;
        for (int i=0; i <connects; i++)
            bridge.connect();
        List<DayVisitData> visits = bridge.watchVisitsBetweenDates(admin.getId(), from, to);
        int guestVisits = 0;
        for (DayVisitData dayVisit: visits) {
            guestVisits += dayVisit.getGuestVisit();
        }
        assertEquals(connects, guestVisits);
        bridge.logout(users.get(1).getId());
    }

    @Test
    public void testOneSubVisitLogoutLogin() {
        registerAndLogin(users.get(1));
        bridge.logout(users.get(1).getId());
        bridge.login(users.get(1).getId(), users.get(1).getUsername(), users.get(1).getPassword());
        List<DayVisitData> visits = bridge.watchVisitsBetweenDates(admin.getId(), from, to);
        int subVisits = 0;
        for (DayVisitData dayVisit: visits) {
            subVisits += dayVisit.getSubVisit();
        }
        assertEquals(2, subVisits);
        bridge.logout(users.get(1).getId());
    }

    @Test
    public void testManySubVisits() {
        int subs = 3;
        for (int i=1; i <subs + 1; i++)
            registerAndLogin(users.get(i));
        List<DayVisitData> visits = bridge.watchVisitsBetweenDates(admin.getId(), from, to);
        int subVisits = 0;
        for (DayVisitData dayVisit: visits) {
            subVisits += dayVisit.getSubVisit();
        }
        assertEquals(subs, subVisits);
        bridge.logout(users.get(1).getId());
    }

    @Test
    public void testManyVisits() {
        registerAndLogin(users.get(1));
        registerAndLogin(users.get(2));
        StoreTestData store = stores.get(0);
        bridge.appointManager(superUser.getId(),store.getStoreName(), users.get(1).getUsername());
        bridge.logout(users.get(1).getId());
        bridge.login(users.get(1).getId(),users.get(1).getUsername(),users.get(1).getPassword());
        List<DayVisitData> visits = bridge.watchVisitsBetweenDates(admin.getId(), from, to);
        int subVisits = 0;
        for (DayVisitData dayVisit: visits) {
            subVisits += dayVisit.getTotal();
        }
        assertEquals(3, subVisits);
        bridge.logout(users.get(1).getId());
    }

    @Test
    public void testManyVisitsMoreTests() {
        bridge.connect();
        bridge.connect();
        registerAndLogin(users.get(1));
        StoreTestData store = stores.get(0);
        bridge.appointManager(superUser.getId(),store.getStoreName(), users.get(1).getUsername());
        bridge.logout(users.get(1).getId());
        bridge.login(users.get(1).getId(),users.get(1).getUsername(),users.get(1).getPassword());
        List<DayVisitData> visits = bridge.watchVisitsBetweenDates(admin.getId(), from, to);
        int totalVisits = 0;
        for (DayVisitData dayVisit: visits) {
            totalVisits += dayVisit.getTotal();
        }
        assertEquals(4, totalVisits);
        bridge.logout(users.get(1).getId());
    }

    @Test
    public void testAll() {
        // subs todo move to setup
        int subs = 3;
        for (int i=1; i <subs + 1; i++)
            registerAndLogin(users.get(i));
        // guest todo move to setup
        int connects = 10;
        for (int i=0; i <connects; i++)
            bridge.connect();
        // managers todo move to setup
        StoreTestData store = stores.get(0);
        bridge.appointManager(superUser.getId(),store.getStoreName(), users.get(1).getUsername());
        bridge.logout(users.get(1).getId());
        bridge.login(users.get(1).getId(),users.get(1).getUsername(),users.get(1).getPassword());
        // owner // todo move to setup
        bridge.appointOwnerToStoreDirectly(superUser.getId(),users.get(1).getUsername(),users.get(1).getPassword());
        bridge.logout(users.get(1).getId());
        bridge.login(users.get(1).getId(),users.get(1).getUsername(),users.get(1).getPassword());
        List<DayVisitData> visits = bridge.watchVisitsBetweenDates(admin.getId(), from, to);
        int totalVisits = 0;
        for (DayVisitData dayVisit: visits) {
            totalVisits += dayVisit.getTotal();
        }
        assertEquals(connects + subs + 2, totalVisits); // 2 is for owner and manager
    }






}
