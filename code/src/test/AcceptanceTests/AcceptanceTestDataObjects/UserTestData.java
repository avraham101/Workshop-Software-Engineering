package AcceptanceTests.AcceptanceTestDataObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserTestData {
    private int id;
    private String username;
    private String password;
    private CartTestData cart;
    private List<PurchaseTestData> purchases;
    private HashMap<ApplicationToStoreTestData,String> applicationAndReply;



    public UserTestData(int id,String username, String password) {
        this.id =id;
        this.username = username;
        this.password = password;
        this.cart = new CartTestData();
        this.purchases = new ArrayList<>();
        this.applicationAndReply = new HashMap<>();

    }

    public int getId() {
        return id;
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

    public HashMap<ApplicationToStoreTestData, String> getApplicationAndReply() {
        return applicationAndReply;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserTestData that = (UserTestData) o;

        return username != null ? username.equals(that.username) : that.username == null;
    }
}
