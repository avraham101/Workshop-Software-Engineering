package AcceptanceTests.AcceptanceTestDataObjects;

public class UserTestData {
    private String username;
    private String password;
    private CartTestData cart;

    public UserTestData(String username, String password) {
        this.username = username;
        this.password = password;
        this.cart = new CartTestData();
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
}
