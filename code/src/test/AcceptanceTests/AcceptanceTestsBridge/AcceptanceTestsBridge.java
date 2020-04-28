package AcceptanceTests.AcceptanceTestsBridge;

import AcceptanceTests.AcceptanceTestDataObjects.*;
import AcceptanceTests.AcceptanceTestDataObjects.FilterTestData.FilterTestData;
import AcceptanceTests.AcceptanceTestDataObjects.PurchasePolicyTestData;
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
    CartTestData getUsersCart(int id);
    boolean deleteFromUserCart(int id, ProductTestData productToDelete);
    boolean changeUserAmountOfProductInCart(int id, ProductTestData productToChangeAmount, int newAmount);
    boolean addToUserCart(int id,ProductTestData productToAdd, int amount);
    boolean writeReviewOnProduct(int id,ProductTestData product, ReviewTestData review);
    List<ReviewTestData> getProductsReviews(ProductTestData product);
    List<StoreTestData> getStoresInfo();
    List<ProductTestData> getStoreProducts(String storeName);
    boolean buyCart(int id,PaymentTestData paymentMethod , DeliveryDetailsTestData deliveryDetails);
    void changeAmountOfProductInStore(int id,ProductTestData product, int amount);
    StoreTestData openStore(int id,String storeName);
    boolean sendApplicationToStore(int id, String storeName, String message);
    boolean deleteProduct(int id,ProductTestData product);
    StoreTestData getStoreInfoByName(String storeName);
    boolean editProductInStore(int id,ProductTestData product);
    boolean appointManager(int id, String storeName, String username);
    boolean deleteManager(int id, String storeName,String username);
    List<PurchaseTestData> getStorePurchasesHistory(int id,String storeName);
    List<PurchaseTestData> getUserPurchaseHistory(int id,String username);
    List<PurchaseTestData> getCurrentUserPurchaseHistory(int id);
    boolean appointOwnerToStore(int id,String storeName, String username);
    boolean addPermissionToManager(int id,String storeName, String username, PermissionsTypeTestData productsInventory);
    boolean deletePermission(int id, String storeName, String username, PermissionsTypeTestData productsInventory);
    boolean writeReplyToApplication(int id, int requestId,String storeName, ApplicationToStoreTestData key, String value);
    HashSet<ApplicationToStoreTestData> viewApplicationToStore(int id, String storeName);
    HashMap<ApplicationToStoreTestData,String> getUserApplicationsAndReplies(int id, String username,String storeName);
    List<ApplicationToStoreTestData> getUserApplications(int id,String username, String storeName);
    List<PurchaseTestData> userGetStorePurchasesHistory(int id,String storeName);
    int connect();
    boolean addDiscount(int id,DiscountTestData discountTestData,String store);
    boolean deleteDiscount(int id, int discountId, String store);
    List<DiscountTestData> getDiscountsOfStore(String store);
    boolean updatePolicy(int id, PurchasePolicyTestData purchasePolicyData, String store);
    String viewPolicy(String storeName);
}
