package AcceptanceTests.AcceptanceTestDataObjects;

public class UserTestData {
    private String username;
    private String password;

    public UserTestData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
