package AcceptanceTests.AcceptanceTestsBridge;

import AcceptanceTests.AcceptanceTestDataObjects.*;
import AcceptanceTests.AcceptanceTestDataObjects.FilterTestData.FilterTestData;

import java.util.HashSet;
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
    HashSet<ProductTestData> filterProducts(List<ProductTestData> products, List<FilterTestData> filters);
    void deleteProducts(List<ProductTestData> products);
    void deleteStores(List<StoreTestData> stores);
    void addStores(List<StoreTestData> stores);
    void addProducts(List<ProductTestData> products);
    void addCartToUser(String username, CartTestData cart);
    CartTestData getCurrentUsersCart();
    boolean deleteFromCurrentUserCart(BasketTestData basketToDeleteFrom, ProductTestData productToDelete);
    boolean changeCurrentUserAmountOfProductInCart(BasketTestData basketToChangeAmountIn, ProductTestData productToChangeAmount, int newAmount);
    boolean addToCurrentUserCart( ProductTestData productToAdd, int amount);
    void deleteCartOfUser(String username);
    void purchaseProducts(PurchaseTestData purchase);
    boolean writeReviewOnProduct(String storeName, ProductTestData product, ReviewTestData review);
    ReviewTestData getReviewByProductAndDate(String purchaseDate, ProductTestData product);
    List<StoreTestData> getStoresInfo();
    List<ProductTestData> getStoreProducts(String storeName);
    PurchaseTestData buyCart(PaymentTestData paymentMethod , DeliveryDetailsTestData deliveryDetails);
    void changeAmountOfProductInStore(ProductTestData product, int amount);
    StoreTestData openStore(String storeName);
    boolean sendApplicationToStore(String storeName, String message);
    boolean addProduct(ProductTestData product);
    boolean deleteProduct(ProductTestData product);
    StoreTestData getStoreInfoByName(String storeName);

    boolean editProductInStore(ProductTestData product);
    boolean appointManager(String storeName, String username);

    boolean deleteManager(String storeName,String username);

    List<PurchaseTestData> getStorePurchasesHistory(String storeName);

    List<PurchaseTestData> getUserPurchaseHistory(String username);
    List<PurchaseTestData> getUserPurchases(String username);
}
