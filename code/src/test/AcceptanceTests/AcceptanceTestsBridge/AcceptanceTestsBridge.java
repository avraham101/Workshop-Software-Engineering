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
    void addProducts(int id,List<ProductTestData> products);
    boolean addProduct(int id, ProductTestData product);
    CartTestData getCurrentUsersCart();
    boolean deleteFromCurrentUserCart(ProductTestData productToDelete);
    boolean changeCurrentUserAmountOfProductInCart(ProductTestData productToChangeAmount, int newAmount);
    boolean addToCurrentUserCart( ProductTestData productToAdd, int amount);
    boolean writeReviewOnProduct(ProductTestData product, ReviewTestData review);
    List<ReviewTestData> getProductsReviews(ProductTestData product);
    List<StoreTestData> getStoresInfo();
    List<ProductTestData> getStoreProducts(String storeName);
    boolean buyCart(PaymentTestData paymentMethod , DeliveryDetailsTestData deliveryDetails);
    void changeAmountOfProductInStore(int id,ProductTestData product, int amount);
    StoreTestData openStore(int id,String storeName);
    boolean sendApplicationToStore(int id, String storeName, String message);
    boolean deleteProduct(int id,ProductTestData product);
    StoreTestData getStoreInfoByName(String storeName);
    boolean editProductInStore(int id,ProductTestData product);
    boolean appointManager(int id, String storeName, String username);
    boolean deleteManager(String storeName,String username);
    List<PurchaseTestData> getStorePurchasesHistory(String storeName);
    List<PurchaseTestData> getUserPurchaseHistory(String username);
    List<PurchaseTestData> getCurrentUserPurchaseHistory();
    boolean appointOwnerToStore(int id,String storeName, String username);
    boolean addPermissionToManager(int id,String storeName, String username, PermissionsTypeTestData productsInventory);
    boolean deletePermission(int id, String storeName, String username, PermissionsTypeTestData productsInventory);
    boolean writeReplyToApplication(int requestId,String storeName, ApplicationToStoreTestData key, String value);
    HashSet<ApplicationToStoreTestData> viewApplicationToStore(String storeName);
    HashMap<ApplicationToStoreTestData,String> getUserApplicationsAndReplies(String username,String storeName);
    List<ApplicationToStoreTestData> getUserApplications(int id,String username, String storeName);
    List<PurchaseTestData> userGetStorePurchasesHistory(String storeName);
    int connect();

}
