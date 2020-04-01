package AcceptanceTests.AcceptanceTestsBridge;

import AcceptanceTests.AcceptanceTestDataObjects.FilterTestData.FilterTestData;
import AcceptanceTests.AcceptanceTestDataObjects.ProductTestData;

import java.util.List;

public interface AcceptanceTestsBridge {
    boolean initialStart(String username, String password);
    String getAdminUsername();
    void resetSystem();
    boolean register(String username, String password);
    void deleteUser(String username);
    String getCurrentLoggedInUser();
    void logout(String currUsername);
    boolean login(String username, String password);
    List<ProductTestData> filterProducts(List<ProductTestData> products, List<FilterTestData> filters);
}
