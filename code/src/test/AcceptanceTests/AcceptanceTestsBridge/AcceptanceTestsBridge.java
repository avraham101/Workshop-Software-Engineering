package AcceptanceTests.AcceptanceTestsBridge;

import AcceptanceTests.AcceptanceTestDataObjects.*;
import AcceptanceTests.AcceptanceTestDataObjects.FilterTestData.FilterTestData;

import java.util.List;

public interface AcceptanceTestsBridge {
    boolean initialStart(String username, String password);
    String getAdminUsername();
    void resetSystem();
    boolean register(String username, String password);
    void deleteUser(String username);
    String getCurrentLoggedInUser();
    boolean logout(String currUsername);
    boolean login(String username, String password);
    List<ProductTestData> filterProducts(List<ProductTestData> products, List<FilterTestData> filters);
    void deleteProducts(List<ProductTestData> products);
    void deleteStores(List<StoreTestData> stores);
    void addStores(List<StoreTestData> stores);
    void addProducts(List<ProductTestData> products);
    void addCartToUser(String username, CartTestData cart);
    CartTestData getCurrentUsersCart();
    boolean deleteFromCurrentUserCart(BasketTestData basketToDeleteFrom, ProductTestData productToDelete);
    boolean changeCurrentUserAmountOfProductInCart(BasketTestData basketToChangeAmountIn, ProductTestData productToChangeAmount, int newAmount);
    boolean addToCurrentUserCart(String storeName, ProductTestData productToAdd, int amount);
    void deleteCartOfUser(String username);
    void purchaseProducts(PurchaseTestData purchase);
    boolean writeReviewOnProduct(String storeName, ProductTestData product, ReviewTestData review);
    ReviewTestData getReviewByProductAndDate(String purchaseDate, ProductTestData product);
}
