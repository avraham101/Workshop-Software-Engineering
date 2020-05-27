package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.DateTestData;
import AcceptanceTests.AcceptanceTestDataObjects.DiscountTestData;
import org.junit.After;
import org.junit.Before;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class AdminRevenue extends AcceptanceTests{

    private UserTestData userToCheck;


    @Before
    public void setUpBefore(){
        userToCheck = users.get(1);
        addUserStoresAndProducts(userToCheck);
    }

    public void setUp(){
        bridge.addToUserCart(userToCheck.getId(),products.get(0),1);
        bridge.buyCart(userToCheck.getId(),validPayment,validDelivery);
        bridge.logout(userToCheck.getId());
        bridge.login(admin.getId(),admin.getUsername(),admin.getPassword());
    }

    public void setUpWithDicount(){
        //addUserStoresAndProducts(admin);
        DiscountTestData discountTestData=new DiscountTestData(50,"appleTest");
        boolean added = bridge.login(admin.getId(),admin.getUsername(),admin.getPassword());
        added = bridge.addDiscount(admin.getId(), discountTestData, "store0Test");
        added = bridge.addToUserCart(admin.getId(),products.get(0),1);
        added = bridge.buyCart(admin.getId(),validPayment,validDelivery);
    }

    @Test
    public void testTodayRevenue() {
        setUp();
        double revenue = bridge.getRevenueToday(admin.getId());
        assertEquals(1.5, revenue, 0.001);
    }

    @Test
    public void testTodayRevenueWithDiscount() {
        setUpWithDicount();
        double revenue = bridge.getRevenueToday(admin.getId());
        assertEquals(1.5 * 0.5, revenue, 0.001);
    }

    @Test
    public void testTodayRevenueNotAdmin() {
        setUp();
        double revenue = bridge.getRevenueToday(users.get(1).getId());
        assertEquals(0, revenue, 0.001);
    }

    @Test
    public void testTodayRevenueNotBuy() {
        double revenue = bridge.getRevenueToday(admin.getId());
        assertEquals(0, revenue, 0.001);
    }

    @Test
    public void testRevenueByDay() {
        setUp();
        DateTestData day = new DateTestData(LocalDate.now().getDayOfMonth(),
                LocalDate.now().getMonthValue(),
                LocalDate.now().getYear());
        double revenue = bridge.getRevenueByDay(admin.getId(), day);
        assertEquals(1.5, revenue, 0.001);
    }

    @Test
    public void testRevenueByDayWithDiscount() {
        setUpWithDicount();
        DateTestData day = new DateTestData(LocalDate.now().getDayOfMonth(),
                LocalDate.now().getMonthValue(),
                LocalDate.now().getYear());
        double revenue = bridge.getRevenueByDay(admin.getId(), day);
        assertEquals(1.5 * 0.5, revenue, 0.001);
    }

    @Test
    public void testRevenueByDayNotAdmin() {
        setUp();
        DateTestData day = new DateTestData(LocalDate.now().getDayOfMonth(),
                LocalDate.now().getMonthValue(),
                LocalDate.now().getYear());
        double revenue = bridge.getRevenueByDay(users.get(1).getId(), day);
        assertEquals(0, revenue, 0.001);
    }

    @Test
    public void testRevenueByDayNotBuy() {
        DateTestData day = new DateTestData(LocalDate.now().getDayOfMonth(),
                LocalDate.now().getMonthValue(),
                LocalDate.now().getYear());
        double revenue = bridge.getRevenueByDay(admin.getId(), day);
        assertEquals(0, revenue, 0.001);
    }

    @After
    public void tearDown(){
        bridge.removeRevenues();
        removeUserStoresAndProducts(userToCheck);
    }
}
