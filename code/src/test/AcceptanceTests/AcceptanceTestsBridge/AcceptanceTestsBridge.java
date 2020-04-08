package AcceptanceTests.AcceptanceTestsBridge;

import AcceptanceTests.AcceptanceTestDataObjects.*;
import AcceptanceTests.AcceptanceTestDataObjects.FilterTestData.FilterTestData;

import java.util.HashSet;
import java.util.List;

public interface AcceptanceTestsBridge {
    boolean initialStart(String username, String password);
    String getAdminUsername(); //?
    void resetSystem();
    boolean register(String username, String password);
    String getCurrentLoggedInUser();
    boolean logout();
    boolean login(String username, String password);
    HashSet<ProductTestData> filterProducts(List<FilterTestData> filters);
    void addStores(List<StoreTestData> stores);
    void addProducts(List<ProductTestData> products);
    boolean addProduct(ProductTestData product);
    CartTestData getCurrentUsersCart();
    boolean deleteFromCurrentUserCart(BasketTestData basketToDeleteFrom, ProductTestData productToDelete);
    boolean changeCurrentUserAmountOfProductInCart(BasketTestData basketToChangeAmountIn, ProductTestData productToChangeAmount, int newAmount);
    boolean addToCurrentUserCart( ProductTestData productToAdd, int amount);
    boolean writeReviewOnProduct(ProductTestData product, ReviewTestData review);
    ReviewTestData getReviewByProductAndDate(String purchaseDate, ProductTestData product);
    List<StoreTestData> getStoresInfo();
    List<ProductTestData> getStoreProducts(String storeName);
    PurchaseTestData buyCart(PaymentTestData paymentMethod , DeliveryDetailsTestData deliveryDetails);
    void changeAmountOfProductInStore(ProductTestData product, int amount);
    StoreTestData openStore(String storeName);
    boolean sendApplicationToStore(String storeName, String message);
    boolean deleteProduct(ProductTestData product);
    StoreTestData getStoreInfoByName(String storeName);
    boolean editProductInStore(ProductTestData product);
    boolean appointManager(String storeName, String username);
    boolean deleteManager(String storeName,String username);
    List<PurchaseTestData> getStorePurchasesHistory(String storeName);
    List<PurchaseTestData> getUserPurchaseHistory(String username);
    boolean appointOwnerToStore(String storeName, String username);
    boolean addPermissionToManager(String storeName, String username, PermissionsTypeTestData productsInventory);
    boolean deletePermission(String storeName, String username, PermissionsTypeTestData productsInventory);
    boolean writeReplyToApplication(String storeName, ApplicationToStoreTestData key, String value);
    HashSet<ApplicationToStoreTestData> viewApplicationToStore(String storeName);
}
