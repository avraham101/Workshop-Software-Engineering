package AcceptanceTests.AcceptanceTestDataObjects;

import java.util.ArrayList;
import java.util.List;

public class UserTestData {
    private String username;
    private String password;
    private CartTestData cart;
    private List<PurchaseTestData> purchases;

    public UserTestData(String username, String password) {
        this.username = username;
        this.password = password;
        this.cart = new CartTestData();
        this.purchases = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public CartTestData getCart() {
        return cart;
    }

    public List<PurchaseTestData> getPurchases() {
        return purchases;
    }
}
