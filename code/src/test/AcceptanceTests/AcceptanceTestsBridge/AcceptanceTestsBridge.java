package AcceptanceTests.AcceptanceTestsBridge;

import AcceptanceTests.AcceptanceTestDataObjects.*;
import AcceptanceTests.AcceptanceTestDataObjects.FilterTestData.FilterTestData;
import Systems.PaymentSystem.PaymentSystem;
import Systems.SupplySystem.SupplySystem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public interface AcceptanceTestsBridge {
    boolean initialStart(String username , String password
            , PaymentSystem paymentSystem, SupplySystem deliverySystem);
    boolean initialStart(String username, String password);
    void resetSystem();
    boolean register(String username, String password);
    boolean logout(int id);
    boolean login(int id, String username, String password);
    HashSet<ProductTestData> filterProducts(List<FilterTestData> filters);
    void addProducts(List<ProductTestData> products);
    boolean addProduct(ProductTestData product);
    CartTestData getCurrentUsersCart();
    boolean deleteFromCurrentUserCart(ProductTestData productToDelete);
    boolean changeCurrentUserAmountOfProductInCart(ProductTestData productToChangeAmount, int newAmount);
    boolean addToCurrentUserCart( ProductTestData productToAdd, int amount);
    boolean writeReviewOnProduct(ProductTestData product, ReviewTestData review);
    List<ReviewTestData> getProductsReviews(ProductTestData product);
    List<StoreTestData> getStoresInfo();
    List<ProductTestData> getStoreProducts(String storeName);
    boolean buyCart(PaymentTestData paymentMethod , DeliveryDetailsTestData deliveryDetails);
    void changeAmountOfProductInStore(ProductTestData product, int amount);
    StoreTestData openStore(int id,String storeName);
    boolean sendApplicationToStore(String storeName, String message);
    boolean deleteProduct(ProductTestData product);
    StoreTestData getStoreInfoByName(String storeName);
    boolean editProductInStore(ProductTestData product);
    boolean appointManager(String storeName, String username);
    boolean deleteManager(String storeName,String username);
    List<PurchaseTestData> getStorePurchasesHistory(String storeName);
    List<PurchaseTestData> getUserPurchaseHistory(String username);
    List<PurchaseTestData> getCurrentUserPurchaseHistory();
    boolean appointOwnerToStore(String storeName, String username);
    boolean addPermissionToManager(String storeName, String username, PermissionsTypeTestData productsInventory);
    boolean deletePermission(String storeName, String username, PermissionsTypeTestData productsInventory);
    boolean writeReplyToApplication(int requestId,String storeName, ApplicationToStoreTestData key, String value);
    HashSet<ApplicationToStoreTestData> viewApplicationToStore(String storeName);
    HashMap<ApplicationToStoreTestData,String> getUserApplicationsAndReplies(String username,String storeName);
    List<ApplicationToStoreTestData> getUserApplications(String username, String storeName);
    List<PurchaseTestData> userGetStorePurchasesHistory(String storeName);
    int connect();

}
