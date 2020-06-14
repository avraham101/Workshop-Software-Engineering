package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.DateTestData;
import AcceptanceTests.AcceptanceTestDataObjects.DayVisitData;
import AcceptanceTests.AcceptanceTestDataObjects.StoreTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import AcceptanceTests.SystemMocks.PublisherMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class AdminWatchVisits extends AcceptanceTests {

    private DateTestData from;
    private DateTestData to;
    private PublisherMock publisherMock;

    @Before
    public void setUpBefore(){
        bridge.login(admin.getId(),admin.getUsername(),admin.getPassword());
        LocalDate now=LocalDate.now();
        LocalDate prev=now.minusDays(3);
        from = new DateTestData(prev.getDayOfMonth(),prev.getMonthValue(),prev.getYear());
        to = new DateTestData(now.getDayOfMonth(),now.getMonthValue(),now.getYear());
        publisherMock=new PublisherMock();
        bridge.setPublisher(publisherMock);
    }

    @After
    public void tearDown(){
        bridge.logout(admin.getId());
        removeUser("testUser2");
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
        assertFalse(publisherMock.getNotificationList().isEmpty());
    }

    @Test
    public void testOneMoreSubVisit() {
        registerAndLogin(users.get(1));
        List<DayVisitData> visits = bridge.watchVisitsBetweenDates(admin.getId(), from, to);
        int subVisits = 0;
        for (DayVisitData dayVisit: visits) {
            subVisits += dayVisit.getSubVisit();
        }
        assertEquals(2, subVisits);
        bridge.logout(users.get(1).getId());
        assertFalse(publisherMock.getNotificationList().isEmpty());
    }

    @Test
    public void testOneMoreGuestSubVisit() {
        bridge.connect();
        List<DayVisitData> visits = bridge.watchVisitsBetweenDates(admin.getId(), from, to);
        int guestVisits = 0;
        for (DayVisitData dayVisit: visits) {
            guestVisits += dayVisit.getGuestVisit();
        }
        assertEquals(6, guestVisits);
        bridge.logout(users.get(1).getId());
        assertFalse(publisherMock.getNotificationList().isEmpty());
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
        assertEquals(connects+5, guestVisits);
        bridge.logout(users.get(1).getId());
        assertFalse(publisherMock.getNotificationList().isEmpty());
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
        assertEquals(3, subVisits);
        bridge.logout(users.get(1).getId());
        assertFalse(publisherMock.getNotificationList().isEmpty());
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
        assertEquals(subs+1, subVisits);
        bridge.logout(users.get(1).getId());
        assertFalse(publisherMock.getNotificationList().isEmpty());
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
        assertEquals(10, subVisits);
        bridge.logout(users.get(1).getId());
        assertFalse(publisherMock.getNotificationList().isEmpty());
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
        assertEquals(11, totalVisits);
        bridge.logout(users.get(1).getId());
        assertFalse(publisherMock.getNotificationList().isEmpty());
    }

    @Test
    public void testAll() {
        int subs = 3;
        int connects = 10;
        setUpTestAll(subs,connects);
        // subs todo move to setup

        List<DayVisitData> visits = bridge.watchVisitsBetweenDates(admin.getId(), from, to);
        int totalVisits = 0;
        for (DayVisitData dayVisit: visits) {
            totalVisits += dayVisit.getTotal();
        }
        assertEquals(connects + subs + 2+7, totalVisits); // 2 is for owner and manager
        // 7 is for the system init (5 guests, 1 admin, 1 subscribe)
        assertFalse(publisherMock.getNotificationList().isEmpty());
    }

    private void setUpTestAll(int subs, int connects) {
        for (int i=1; i <subs + 1; i++)
            registerAndLogin(users.get(i));
        // guest

        for (int i=0; i <connects; i++)
            bridge.connect();
        // managers
        StoreTestData store = stores.get(0);
        bridge.appointManager(superUser.getId(),store.getStoreName(), users.get(1).getUsername());
        bridge.logout(users.get(1).getId());
        bridge.login(users.get(1).getId(),users.get(1).getUsername(),users.get(1).getPassword());
        // owner //
        bridge.appointOwnerToStoreDirectly(superUser.getId(),users.get(1).getUsername(),users.get(1).getPassword());
        bridge.logout(users.get(1).getId());
        bridge.login(users.get(1).getId(),users.get(1).getUsername(),users.get(1).getPassword());
    }


}
