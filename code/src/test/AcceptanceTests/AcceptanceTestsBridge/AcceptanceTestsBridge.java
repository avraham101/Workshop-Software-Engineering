package AcceptanceTests.AcceptanceTestsBridge;

import AcceptanceTests.AcceptanceTestDataObjects.BasketTestData;
import AcceptanceTests.AcceptanceTestDataObjects.CartTestData;
import AcceptanceTests.AcceptanceTestDataObjects.FilterTestData.FilterTestData;
import AcceptanceTests.AcceptanceTestDataObjects.ProductTestData;
import AcceptanceTests.AcceptanceTestDataObjects.StoreTestData;

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
    void deleteProducts(List<ProductTestData> products);
    void deleteStores(List<StoreTestData> stores);
    void addStores(List<StoreTestData> stores);
    void addProducts(List<ProductTestData> products);
    void addCartToUser(String username, CartTestData cart0);
    CartTestData getCurrentUsersCart();
    void deleteFromCurrentUserCart(BasketTestData basketToDeleteFrom, ProductTestData productToDelete);
}
