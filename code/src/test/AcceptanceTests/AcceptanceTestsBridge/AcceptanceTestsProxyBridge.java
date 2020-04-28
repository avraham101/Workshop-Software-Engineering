package AcceptanceTests.AcceptanceTestsBridge;

import AcceptanceTests.AcceptanceTestDataObjects.*;
import AcceptanceTests.AcceptanceTestDataObjects.FilterTestData.FilterTestData;
import AcceptanceTests.AcceptanceTestDataObjects.FilterTestData.PurchasePolicyTestData;
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
    public void addProducts(int id,List<ProductTestData> products) {
        if(realBridge!=null)
            realBridge.addProducts(id,products);
    }


    @Override
    public CartTestData getUsersCart(int id) {
        if(realBridge!=null)
            return realBridge.getUsersCart(id);
        return null;
    }

    @Override
    public boolean deleteFromUserCart(int id, ProductTestData productToDelete) {
        if(realBridge!=null)
            return realBridge.deleteFromUserCart(id, productToDelete);
        return false;
    }

    @Override
    public boolean changeUserAmountOfProductInCart(int id, ProductTestData productToChangeAmount, int newAmount) {
        if(realBridge!=null)
            return realBridge.changeUserAmountOfProductInCart(id,productToChangeAmount,newAmount);
        return false;
    }

    @Override
    public boolean addToUserCart(int id, ProductTestData productToAdd, int amount) {
        if(realBridge!=null)
            return realBridge.addToUserCart(id,productToAdd,amount);
        return false;
    }

    @Override
    public boolean writeReviewOnProduct(int id,ProductTestData product, ReviewTestData review) {
        if(realBridge!=null)
            return realBridge.writeReviewOnProduct(id,product,review);
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
    public boolean buyCart(int id,PaymentTestData paymentMethod, DeliveryDetailsTestData deliveryDetails) {
        if(realBridge!=null)
            return realBridge.buyCart(id,paymentMethod,deliveryDetails);
        return true;
    }

    @Override
    public void changeAmountOfProductInStore(int id,ProductTestData product, int amount) {
        if(realBridge!=null)
            realBridge.changeAmountOfProductInStore(id,product,amount);
    }

    @Override
    public StoreTestData openStore(int id,String storeName) {
        if(realBridge!=null)
            return realBridge.openStore(id,storeName);
        return null;
    }

    @Override
    public boolean sendApplicationToStore(int id, String storeName, String message) {
        if(realBridge!=null)
            return realBridge.sendApplicationToStore(id,storeName,message);
        return false;
    }

    @Override
    public boolean addProduct(int id,ProductTestData product) {
        if(realBridge!=null)
            return realBridge.addProduct(id,product);
        return false;
    }

    @Override
    public boolean deleteProduct(int id,ProductTestData product) {
        if(realBridge!=null)
            return realBridge.deleteProduct(id,product);
        return false;
    }

    @Override
    public StoreTestData getStoreInfoByName(String storeName) {
        if(realBridge!=null)
            return realBridge.getStoreInfoByName(storeName);
        return null;
    }

    @Override
    public boolean editProductInStore(int id,ProductTestData product) {
        if(realBridge!=null)
            return realBridge.editProductInStore(id,product);
        return false;
    }

    @Override
    public boolean appointManager(int id, String storeName, String username) {
        if(realBridge!=null)
            return realBridge.appointManager(id,storeName,username);
        return false;
    }

    @Override
    public boolean deleteManager(int id, String storeName, String username) {
        if(realBridge!=null)
            return realBridge.deleteManager(id,storeName,username);
        return false;
    }

    @Override
    public List<PurchaseTestData> getStorePurchasesHistory(int id,String storeName) {
        if(realBridge!=null)
            return realBridge.getStorePurchasesHistory(id,storeName);
        return null;
    }

    @Override
    public List<PurchaseTestData> getUserPurchaseHistory(int id,String username) {
        if(realBridge!=null)
            return realBridge.getUserPurchaseHistory(id,username);
        return null;
    }

    @Override
    public List<PurchaseTestData> getCurrentUserPurchaseHistory(int id) {
        if(realBridge!=null)
            return realBridge.getCurrentUserPurchaseHistory(id);
        return null;
    }

    @Override
    public boolean appointOwnerToStore(int id,String storeName, String username) {
        if(realBridge!=null)
            return realBridge.appointOwnerToStore(id,storeName,username);
        return false;
    }

    @Override
    public boolean addPermissionToManager(int id,String storeName, String username, PermissionsTypeTestData permissionType) {
        if(realBridge!=null)
            return realBridge.addPermissionToManager(id,storeName,username,permissionType);
        return false;
    }

    @Override
    public boolean deletePermission(int id,String storeName, String username, PermissionsTypeTestData permissionType) {
        if(realBridge!=null)
            return realBridge.deletePermission(id,storeName,username,permissionType);
        return false;
    }

    @Override
    public boolean writeReplyToApplication(int id, int requestId,String storeName, ApplicationToStoreTestData application, String reply) {
        if(realBridge!=null)
            return realBridge.writeReplyToApplication(id,requestId,storeName,application,reply);
        return false;
    }

    @Override
    public HashSet<ApplicationToStoreTestData> viewApplicationToStore(int id, String storeName) {
        if(realBridge!=null)
            return realBridge.viewApplicationToStore(id, storeName);
        return null;
    }

    @Override
    public HashMap<ApplicationToStoreTestData, String> getUserApplicationsAndReplies(int id, String username, String storeName) {
        if(realBridge!=null)
            return realBridge.getUserApplicationsAndReplies(id,username,storeName);
        return null;
    }

    @Override
    public List<ApplicationToStoreTestData> getUserApplications(int id, String username, String storeName) {
        if(realBridge!=null)
            return realBridge.getUserApplications(id,username,storeName);
        return null;
    }

    @Override
    public List<PurchaseTestData> userGetStorePurchasesHistory(int id,String storeName) {
        if(realBridge!=null)
            return realBridge.userGetStorePurchasesHistory(id,storeName);
        return null;
    }

    @Override
    public int connect() {
        if(realBridge!=null)
            return realBridge.connect();
        return -1;
    }

    @Override
    public boolean addDiscount(int id,DiscountTestData discountTestData,String store) {
        if(realBridge!=null)
            return realBridge.addDiscount(id,discountTestData,store);
        return false;
    }

    @Override
    public boolean deleteDiscount(int id, int discountId, String store) {
        if(realBridge!=null)
            return realBridge.deleteDiscount(id,discountId,store);
        return false;
    }

    @Override
    public List<DiscountTestData> getDiscountsOfStore(String store) {
        if(realBridge!=null)
            return realBridge.getDiscountsOfStore(store);
        return null;
    }

    @Override
    public boolean updatePolicy(int id, PurchasePolicyTestData purchasePolicyData, String store) {
        if(realBridge != null)
            return realBridge.updatePolicy(id,purchasePolicyData,store);
        return false;
    }
}
