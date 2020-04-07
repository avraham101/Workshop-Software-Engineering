package AcceptanceTests.AcceptanceTestsBridge;

import AcceptanceTests.AcceptanceTestDataObjects.*;
import AcceptanceTests.AcceptanceTestDataObjects.FilterTestData.FilterTestData;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class AcceptanceTestsProxyBridge implements AcceptanceTestsBridge {
    private AcceptanceTestsBridge realBridge;

    public AcceptanceTestsProxyBridge(){
        this.realBridge = null;
    }

    public void setRealBridge(AcceptanceTestsBridge bridge){
        this.realBridge = bridge;
    }

    @Override
    public boolean initialStart(String username, String password) {
        if(realBridge!=null)
            return realBridge.initialStart(username,password);
        return false;
    }

    @Override
    public String getAdminUsername() {
        if(realBridge!=null)
            return realBridge.getAdminUsername();
        return null;
    }

    @Override
    public void resetSystem() {
        if(realBridge!=null)
            realBridge.resetSystem();
    }

    @Override
    public boolean register(String username, String password) {
        if(realBridge!=null)
            return realBridge.register(username,password);
        return false;
    }

    @Override
    public void deleteUser(String username) {
        if(realBridge!=null)
            realBridge.deleteUser(username);
    }

    @Override
    public String getCurrentLoggedInUser() {
        if(realBridge!=null)
            return realBridge.getCurrentLoggedInUser();
        return null;
    }

    @Override
    public boolean logout() {
        if(realBridge!=null)
            return realBridge.logout();
        return false;
    }

    @Override
    public boolean login(String username, String password) {
        if(realBridge!=null)
            return realBridge.login(username,password);
        return false;
    }

    @Override
    public HashSet<ProductTestData> filterProducts(List<ProductTestData> products, List<FilterTestData> filters) {
        if(realBridge!=null)
            return realBridge.filterProducts(products,filters);
        return null;
    }

    @Override
    public void deleteProducts(List<ProductTestData> products) {
        if(realBridge!=null)
            deleteProducts(products);
    }

    @Override
    public void deleteStores(List<StoreTestData> stores) {
        if(realBridge!=null)
            realBridge.deleteStores(stores);
    }

    @Override
    public void addStores(List<StoreTestData> stores) {
        if(realBridge!=null)
            realBridge.addStores(stores);
    }

    @Override
    public void addProducts(List<ProductTestData> products) {
        if(realBridge!=null)
            realBridge.addProducts(products);
    }

    @Override
    public void addCartToUser(String username, CartTestData cart) {
        if(realBridge!=null)
            realBridge.addCartToUser(username,cart);
    }

    @Override
    public CartTestData getCurrentUsersCart() {
        if(realBridge!=null)
            return realBridge.getCurrentUsersCart();
        return null;
    }

    @Override
    public boolean deleteFromCurrentUserCart(BasketTestData basketToDeleteFrom, ProductTestData productToDelete) {
        if(realBridge!=null)
            return deleteFromCurrentUserCart(basketToDeleteFrom,productToDelete);
        return false;
    }

    @Override
    public boolean changeCurrentUserAmountOfProductInCart(BasketTestData basketToChangeAmountIn, ProductTestData productToChangeAmount, int newAmount) {
        if(realBridge!=null)
            return realBridge.changeCurrentUserAmountOfProductInCart(basketToChangeAmountIn,productToChangeAmount,newAmount);
        return false;
    }

    @Override
    public boolean addToCurrentUserCart(ProductTestData productToAdd, int amount) {
        if(realBridge!=null)
            return realBridge.addToCurrentUserCart(productToAdd,amount);
        return false;
    }

    @Override
    public boolean writeReviewOnProduct(ProductTestData product, ReviewTestData review) {
        if(realBridge!=null)
            return realBridge.writeReviewOnProduct(product,review);
        return false;
    }

    @Override
    public ReviewTestData getReviewByProductAndDate(String purchaseDate, ProductTestData product) {
        if(realBridge!=null)
            return realBridge.getReviewByProductAndDate(purchaseDate,product);
        return null;
    }

    @Override
    public List<StoreTestData> getStoresInfo() {
        if(realBridge!=null)
            return realBridge.getStoresInfo();
        return null;
    }

    @Override
    public List<ProductTestData> getStoreProducts(String storeName) {
        if(realBridge!=null)
            return realBridge.getStoreProducts(storeName);
        return null;
    }

    @Override
    public PurchaseTestData buyCart(PaymentTestData paymentMethod, DeliveryDetailsTestData deliveryDetails) {
        if(realBridge!=null)
            return realBridge.buyCart(paymentMethod,deliveryDetails);
        return null;
    }

    @Override
    public void changeAmountOfProductInStore(ProductTestData product, int amount) {
        if(realBridge!=null)
            realBridge.changeAmountOfProductInStore(product,amount);
    }

    @Override
    public StoreTestData openStore(String storeName) {
        if(realBridge!=null)
            return realBridge.openStore(storeName);
        return null;
    }

    @Override
    public boolean sendApplicationToStore(String storeName, String message) {
        if(realBridge!=null)
            return realBridge.sendApplicationToStore(storeName,message);
        return false;
    }

    @Override
    public boolean addProduct(ProductTestData product) {
        if(realBridge!=null)
            return realBridge.addProduct(product);
        return false;
    }

    @Override
    public boolean deleteProduct(ProductTestData product) {
        if(realBridge!=null)
            return deleteProduct(product);
        return false;
    }

    @Override
    public StoreTestData getStoreInfoByName(String storeName) {
        if(realBridge!=null)
            return realBridge.getStoreInfoByName(storeName);
        return null;
    }

    @Override
    public boolean editProductInStore(ProductTestData product) {
        if(realBridge!=null)
            return realBridge.editProductInStore(product);
        return false;
    }

    @Override
    public boolean appointManager(String storeName, String username) {
        if(realBridge!=null)
            return realBridge.appointManager(storeName,username);
        return false;
    }

    @Override
    public boolean deleteManager(String storeName, String username) {
        if(realBridge!=null)
            return realBridge.deleteManager(storeName,username);
        return false;
    }

    @Override
    public List<PurchaseTestData> getStorePurchasesHistory(String storeName) {
        if(realBridge!=null)
            return realBridge.getStorePurchasesHistory(storeName);
        return null;
    }

    @Override
    public List<PurchaseTestData> getUserPurchaseHistory(String username) {
        if(realBridge!=null)
            return realBridge.getUserPurchaseHistory(username);
        return null;
    }

    @Override
    public List<PurchaseTestData> getUserPurchases(String username) {
        if(realBridge!=null)
            return realBridge.getUserPurchases(username);
        return null;
    }

    @Override
    public boolean appointOwnerToStore(String storeName, String username) {
        if(realBridge!=null)
            return realBridge.appointOwnerToStore(storeName,username);
        return false;
    }

    @Override
    public boolean addPermissionToManager(String storeName, String username, PermissionsTypeTestData permissionType) {
        if(realBridge!=null)
            return realBridge.addPermissionToManager(storeName,username,permissionType);
        return false;
    }

    @Override
    public boolean deletePermission(String storeName, String username, PermissionsTypeTestData permissionType) {
        if(realBridge!=null)
            return realBridge.deletePermission(storeName,username,permissionType);
        return false;
    }

    @Override
    public HashMap<ApplicationToStoreTestData, String> getUserApplicationsAndReplies(String username) {
        if(realBridge!=null)
            return realBridge.getUserApplicationsAndReplies(username);
        return null;
    }

    @Override
    public boolean writeReplyToApplication(String storeName, ApplicationToStoreTestData application, String reply) {
        if(realBridge!=null)
            return realBridge.writeReplyToApplication(storeName,application,reply);
        return false;
    }

    @Override
    public HashSet<ApplicationToStoreTestData> viewApplicationToStore(String storeName) {
        if(realBridge!=null)
            return realBridge.viewApplicationToStore(storeName);
        return null;
    }

    @Override
    public boolean deleteStore(String storeName) {
        if(realBridge!=null)
            return realBridge.deleteStore(storeName);
        return false;
    }
}
