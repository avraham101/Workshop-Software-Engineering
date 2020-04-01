package AcceptanceTests.AcceptanceTestsBridge;

public interface AcceptanceTestsBridge {
    boolean initialStart(String username, String password);
    String getAdminUsername();
    void resetSystem();
    boolean register(String username, String password);
    void deleteUser(String username);
    String getCurrentLoggedInUser();
    void logout(String currUsername);
    boolean login(String username, String password);
}
