package Domain;

import DataAPI.*;
import Domain.Discount.Discount;
import Domain.PurchasePolicy.PurchasePolicy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class User {
    private UserState state;

    public User() {
        state=new Guest();
    }

    public User(UserState userState) {
        state = userState;
    }

    // ============================ getters & setters ============================ //

    public UserState getState() {
        return state;
    }

    /**
     * The function update the user state.
     * use case 2.3 - Login
     * @param state the new State to update
     */
    public void setState(UserState state) {
        this.state = state;
    }

    public String getUserName() {
        return this.state.getName();
    }

    public String getPassword() {
        return this.state.getPassword();
    }

    // ============================ getters & setters ============================ //

    /**
     * use case 2.3 - Login
     * @param subscribe - The new Subscribe State
     * @return true if succeed to change state, otherwise false.
     */
    public boolean login(Subscribe subscribe) {
        return state.login(this, subscribe);
    }

    /**
     * use case 2.7.1 watch cart details
     * return the details about a cart
     * @return - the cart details
     */
    public CartData watchCartDetatils() {
        return state.watchCartDetatils();
    }

    /**
     * use case 2.7.2 - delete product from the cart
     * @param productName - the product to remove
     * @param storeName - the store that sale this product
     * @return - true if the delete work, false if not
     */
    public boolean deleteFromCart(String productName,String storeName) {
        return state.deleteFromCart(productName, storeName);
    }

    /**
     * use case 2.7.3 - edit amount of product
     * @param productName - the product to edit it's amount
     * @param storeName - the store of the product
     * @param newAmount - the new amount
     * @return - true if succeeded, false if not
     */
    public boolean editProductInCart(String productName,String storeName,int newAmount) {
        return state.editProductInCart(productName, storeName, newAmount);
    }

    /**
     * use case 2.7.4 - add a product to his cart
     * @param store - the store of the product
     * @param product - the product to add
     * @param amount - the amount of the product
     * @return - true if add, false if not
     */
    public boolean addProductToCart(Store store, Product product, int amount) {
        return state.addProductToCart(store, product, amount);
    }


    /**
     * use case 2.8 - purchase cart
     * the function reserved Products from cart
     * @return true if the cart bought, otherwise false
     */
    public boolean reservedCart() {
        return state.reserveCart();
    }

    /**
     * use case 2.8 - purchase cart
     * the function updated Delivery Data and Payment Data
     * @param paymentData - the payment data
     * @param deliveryData - the delivery data
     */
    public boolean buyCart(PaymentData paymentData, DeliveryData deliveryData) {
        return state.buyCart(paymentData, deliveryData);
    }

    /**
     * use case 2.8 - purchase cart
     * the function cancel the cart
     */
    public void cancelCart() {
        state.cancelCart();
    }

    /**
     * use case 2.8 - purchase cart
     * the function savePurchases the purchase
     * @param buyer - the buyer
     */
    public void savePurchase(String buyer) {
        state.savePurchase(buyer);
    }

    /**
     * use case 3.1 - Logout
     * @return true if the user state changed back to guest
     */
    public boolean logout() {
        return state.logout(this);
    }

    /**
     * use case 3.2 - Open Store
     * @param storeDetails - the details of the the store
     * @return The Store that we open, otherwise null;
     */
    public Store openStore(StoreData storeDetails) {
        return state.openStore(storeDetails);
    }

    /**
     * use case 3.3 - write review
     * the function check if a product is perchesed
     * @param storeName - the store name
     * @param productName
     * @return
     */
    public boolean isItPurchased(String storeName, String productName) {
        return state.isItPurchased(storeName, productName);
    }

    /**
     * use case 3.3 - add review
     * the function add the review to the user
     * @param review - the review to add
     */
    public boolean addReview(Review review) {
        if(!isItPurchased(review.getStore(),review.getProductName())) //Product purchased
            return false;
        return state.addReview(review);
    }

    /**
     * use case 3.3 - remove review
     * @param review - the review to remove
     */
    public void removeReview(Review review) {
        state.removeReview(review);
    }

    /**
     * use case 3.5 - add request
     * @param storeName
     * @param content
     * @return
     */
    public Request addRequest(int requestId, String storeName, String content) {
        return state.addRequest(requestId,storeName, content); }

    /**
     * use case 3.7 - watch purchase history
     * the function return the purchase list
     * @return the purchase list
     */
    public Response<List<Purchase>> watchMyPurchaseHistory() {
        return state.watchMyPurchaseHistory();
    }

    /**
     * use case 4.1.1 - add product to store
     * @param productData
     * @return
     */
    public Response<Boolean> addProductToStore(ProductData productData) {
        return state.addProductToStore(productData);
    }

    /**
     * use case 4.1.2 - remove product from store
     * @param storeName
     * @param productName
     * @return
     */
    public Response<Boolean> removeProductFromStore(String storeName, String productName) {
        return state.removeProductFromStore(storeName,productName);
    }

    /**
     * use case 4.1.3 - edit product in store
     * @param productData
     * @return
     */
    public Response<Boolean> editProductFromStore(ProductData productData) {
        return state.editProductFromStore(productData);
    }

    /**
     * 4.2.1.2 - remove discount
     * @param discountId - id of the discount ro delete
     * @param storeName - name of the store to remove the discount from
     */
    public Response<Boolean> deleteDiscountFromStore(int discountId, String storeName) {
        return state.deleteDiscountFromStore(discountId,storeName);
    }

    /**
     * use case 4.5 - add manager
     * @param subscribe
     * @param storeName
     * @return
     */
    public Response<Boolean> addManager(Subscribe subscribe, String storeName) {
        return state.addManager(subscribe,storeName);
    }

    /**
     * use case 4.6.1 - add permissions
     * @param permissions
     * @param storeName
     * @param userName
     * @return
     */
    public Response<Boolean> addPermissions(List<PermissionType> permissions, String storeName, String userName) {
        return state.addPermissions(permissions,storeName,userName);
    }

    /**
     * use case 4.6.2 -remove permissions
     * @param permissions
     * @param storeName
     * @param userName
     * @return
     */
    public Response<Boolean> removePermissions(List<PermissionType> permissions, String storeName, String userName) {
        return state.removePermissions(permissions,storeName,userName);
    }

    /**
     * user case 4.7 - remove manager
     * @param userName
     * @param storeName
     * @return
     */
    public Response<Boolean> removeManager(String userName, String storeName) {
        return state.removeManager(userName,storeName);
    }

    /**
     * use case 4.9.1 - view request
     * @param storeName
     */
    public List<Request> viewRequest(String storeName) {
        return state.viewRequest(storeName);
    }

    /**
     * use case 4.9.2 - replay to Request
     * @param storeName
     * @param requestID
     * @param content
     * @return request if replay, null else
     */
    public Response<Request> replayToRequest(String storeName, int requestID, String content) {
        return state.replayToRequest(storeName, requestID, content);
    }

    /**
     * use case 4.10 - watch Store History by store owner
     * @param storeName - the store name to watch history
     * @return the purchase list
     */
    public boolean canWatchStoreHistory(String storeName) {
        return state.canWatchStoreHistory(storeName);
    }

    /**
     * use case 6.4.1 - watch user history
     * @return
     */
    public boolean canWatchUserHistory() {
        return state.canWatchUserHistory();
    }

    public List<Store> getMyManagedStores(){
       return state.getMyManagedStores();
    }

    public Set<StorePermissionType> getPermissionsForStore(String storeName) {
        return state.getPermissionsForStore(storeName);
    }

    public Response<Boolean> addDiscountToStore(String storeName, Discount discount) {
        return state.addDiscountToStore(storeName,discount);
    }

    public Response<Boolean> updateStorePolicy(String storeName, PurchasePolicy policy) {
        return state.updateStorePolicy(storeName, policy);
    }
}
