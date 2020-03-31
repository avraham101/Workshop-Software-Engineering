package AcceptanceTests.AcceptanceTestsBridge;

public interface AcceptanceTestsBridge {
    boolean initialStart(String username, String password);
    String getAdminUsername();
    void resetSystem();
}
