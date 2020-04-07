package AcceptanceTests.AcceptanceTestsBridge;

import AcceptanceTests.AcceptanceTestDataObjects.*;
import AcceptanceTests.AcceptanceTestDataObjects.FilterTestData.FilterTestData;
import Service.ServiceAPI;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class AcceptanceTestsRealBridge implements AcceptanceTestsBridge {
    private ServiceAPI serviceAPI;

    public AcceptanceTestsRealBridge(){
        this.serviceAPI = new ServiceAPI();
    }

    @Override
    public boolean initialStart(String username, String password) {
        return false;
    }

    @Override
    public String getAdminUsername() {
        return null;
    }

    @Override
    public void resetSystem() {

    }

    @Override
    public boolean register(String username, String password) {
        return false;
    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public String getCurrentLoggedInUser() {
        return null;
    }

    @Override
    public boolean logout() {
        return false;
    }

    @Override
    public boolean login(String username, String password) {
        return false;
    }

    @Override
    public HashSet<ProductTestData> filterProducts(List<ProductTestData> products, List<FilterTestData> filters) {
        return null;
    }

    @Override
    public void deleteProducts(List<ProductTestData> products) {

    }

    @Override
    public void deleteStores(List<StoreTestData> stores) {

    }

    @Override
    public void addStores(List<StoreTestData> stores) {

    }

    @Override
    public void addProducts(List<ProductTestData> products) {

    }

    @Override
    public void addCartToUser(String username, CartTestData cart) {

    }

    @Override
    public CartTestData getCurrentUsersCart() {
        return null;
    }

    @Override
    public boolean deleteFromCurrentUserCart(BasketTestData basketToDeleteFrom, ProductTestData productToDelete) {
        return false;
    }

    @Override
    public boolean changeCurrentUserAmountOfProductInCart(BasketTestData basketToChangeAmountIn, ProductTestData productToChangeAmount, int newAmount) {
        return false;
    }

    @Override
    public boolean addToCurrentUserCart(ProductTestData productToAdd, int amount) {
        return false;
    }

    @Override
    public boolean writeReviewOnProduct(ProductTestData product, ReviewTestData review) {
        return false;
    }

    @Override
    public ReviewTestData getReviewByProductAndDate(String purchaseDate, ProductTestData product) {
        return null;
    }

    @Override
    public List<StoreTestData> getStoresInfo() {
        return null;
    }

    @Override
    public List<ProductTestData> getStoreProducts(String storeName) {
        return null;
    }

    @Override
    public PurchaseTestData buyCart(PaymentTestData paymentMethod, DeliveryDetailsTestData deliveryDetails) {
        return null;
    }

    @Override
    public void changeAmountOfProductInStore(ProductTestData product, int amount) {

    }

    @Override
    public StoreTestData openStore(String storeName) {
        return null;
    }

    @Override
    public boolean sendApplicationToStore(String storeName, String message) {
        return false;
    }

    @Override
    public boolean addProduct(ProductTestData product) {
        return false;
    }

    @Override
    public boolean deleteProduct(ProductTestData product) {
        return false;
    }

    @Override
    public StoreTestData getStoreInfoByName(String storeName) {
        return null;
    }

    @Override
    public boolean editProductInStore(ProductTestData product) {
        return false;
    }

    @Override
    public boolean appointManager(String storeName, String username) {
        return false;
    }

    @Override
    public boolean deleteManager(String storeName, String username) {
        return false;
    }

    @Override
    public List<PurchaseTestData> getStorePurchasesHistory(String storeName) {
        return null;
    }

    @Override
    public List<PurchaseTestData> getUserPurchaseHistory(String username) {
        return null;
    }

    @Override
    public List<PurchaseTestData> getUserPurchases(String username) {
        return null;
    }

    @Override
    public boolean appointOwnerToStore(String storeName, String username) {
        return false;
    }

    @Override
    public boolean addPermissionToManager(String storeName, String username, PermissionsTypeTestData productsInventory) {
        return false;
    }

    @Override
    public boolean deletePermission(String storeName, String username, PermissionsTypeTestData productsInventory) {
        return false;
    }

    @Override
    public HashMap<ApplicationToStoreTestData, String> getUserApplicationsAndReplies(String username) {
        return null;
    }

    @Override
    public boolean writeReplyToApplication(String storeName, ApplicationToStoreTestData key, String value) {
        return false;
    }

    @Override
    public HashSet<ApplicationToStoreTestData> viewApplicationToStore(String storeName) {
        return null;
    }

    @Override
    public boolean deleteStore(String storeName) {
        return false;
    }
}
