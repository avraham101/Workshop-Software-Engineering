package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.DiscountTestData;
import org.junit.Before;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AdminRevenue extends AcceptanceTests{

    UserTestData userToCheck;

    public void setUp(){
        userToCheck = users.get(1);
        addUserStoresAndProducts(userToCheck);
        bridge.addToUserCart(userToCheck.getId(),products.get(0),1);
        bridge.buyCart(userToCheck.getId(),validPayment,validDelivery);
        bridge.logout(userToCheck.getId());
        bridge.login(admin.getId(),admin.getUsername(),admin.getPassword());
    }

    public void setUpWithDicount(){
        addUserStoresAndProducts(admin);
        DiscountTestData discountTestData=new DiscountTestData(50,"appleTest");
        bridge.addDiscount(admin.getId(), discountTestData, "store0Test");
        bridge.addToUserCart(admin.getId(),products.get(0),1);
        bridge.buyCart(admin.getId(),validPayment,validDelivery);
        bridge.login(admin.getId(),admin.getUsername(),admin.getPassword());
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

}
