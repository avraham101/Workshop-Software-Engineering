package AcceptanceTests.AcceptanceTestsBridge;

import AcceptanceTests.AcceptanceTestDataObjects.*;
import AcceptanceTests.AcceptanceTestDataObjects.FilterTestData.FilterTestData;
import Systems.PaymentSystem.PaymentSystem;
import Systems.SupplySystem.SupplySystem;

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
    public boolean initialStart(String username, String password, PaymentSystem paymentSystem, SupplySystem deliverySystem) {
        if(realBridge!=null)
            return realBridge.initialStart(username,password,paymentSystem,deliverySystem);
        return false;
    }

    @Override
    public boolean initialStart(String username, String password) {
        if(realBridge!=null)
            return realBridge.initialStart(username,password);
        return false;
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
    public boolean logout(int id) {
        if(realBridge!=null)
            return realBridge.logout(id);
        return false;
    }

    @Override
    public boolean login(int id, String username, String password) {
        if(realBridge!=null)
            return realBridge.login(id,username,password);
        return false;
    }

    @Override
    public HashSet<ProductTestData> filterProducts(List<FilterTestData> filters) {
        if(realBridge!=null)
            return realBridge.filterProducts(filters);
        return null;
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
    public CartTestData getCurrentUsersCart() {
        if(realBridge!=null)
            return realBridge.getCurrentUsersCart();
        return null;
    }

    @Override
    public boolean deleteFromCurrentUserCart(ProductTestData productToDelete) {
        if(realBridge!=null)
            return realBridge.deleteFromCurrentUserCart(productToDelete);
        return false;
    }

    @Override
    public boolean changeCurrentUserAmountOfProductInCart(ProductTestData productToChangeAmount, int newAmount) {
        if(realBridge!=null)
            return realBridge.changeCurrentUserAmountOfProductInCart(productToChangeAmount,newAmount);
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
    public List<ReviewTestData> getProductsReviews(ProductTestData product) {
        if(realBridge!=null)
            return realBridge.getProductsReviews(product);
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
    public boolean buyCart(PaymentTestData paymentMethod, DeliveryDetailsTestData deliveryDetails) {
        if(realBridge!=null)
            return realBridge.buyCart(paymentMethod,deliveryDetails);
        return true;
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
            return realBridge.deleteProduct(product);
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
    public List<PurchaseTestData> getCurrentUserPurchaseHistory() {
        if(realBridge!=null)
            return realBridge.getCurrentUserPurchaseHistory();
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
    public boolean writeReplyToApplication(int requestId,String storeName, ApplicationToStoreTestData application, String reply) {
        if(realBridge!=null)
            return realBridge.writeReplyToApplication(requestId,storeName,application,reply);
        return false;
    }

    @Override
    public HashSet<ApplicationToStoreTestData> viewApplicationToStore(String storeName) {
        if(realBridge!=null)
            return realBridge.viewApplicationToStore(storeName);
        return null;
    }

    @Override
    public HashMap<ApplicationToStoreTestData, String> getUserApplicationsAndReplies(String username, String storeName) {
        if(realBridge!=null)
            return realBridge.getUserApplicationsAndReplies(username,storeName);
        return null;
    }

    @Override
    public List<ApplicationToStoreTestData> getUserApplications(String username, String storeName) {
        if(realBridge!=null)
            return realBridge.getUserApplications(username,storeName);
        return null;
    }

    @Override
    public List<PurchaseTestData> userGetStorePurchasesHistory(String storeName) {
        if(realBridge!=null)
            return realBridge.userGetStorePurchasesHistory(storeName);
        return null;
    }

    @Override
    public int connect() {
        if(realBridge!=null)
            return realBridge.connect();
        return -1;
    }
}
