package Stubs;

import DataAPI.*;
import Domain.Discount.Discount;
import Domain.*;

import java.util.*;

public class SubscribeStub extends Subscribe{
    private Store store;

    public SubscribeStub(String userName, String password) {
            super(userName, password);
        }

    /**
     * use case 2.3 - Login
     * @param user - The user who using the system
     * @param subscribe - The user state who need to be set
     * @return
     */
    @Override
    public boolean login(User user, Subscribe subscribe) {
        return false;
    }

    @Override
    public boolean addProductToCart(Store store, Product product, int amount) {
        return true;
    }

    /**
     * use case 3.1 - Logout
     * @param user - the user who using the system
     * @return
     */
    @Override
    public boolean logout(User user){
        return true;
    }

    /**
     * use case 3.2 - open store
     * @param storeDetails - the details of the store
     * @return The store that we opened.
     */
    @Override
    public Store openStore(StoreData storeDetails) {
        store=new Store(storeDetails.getName(),new Permission(this),storeDetails.getDescription());
        return store;
    }

    /**
     * use case 4.1.1 -add product
     * @param productData
     * @return false for guest
     */
    @Override
    public Response<Boolean> addProductToStore(ProductData productData){
        return new Response<>(true,OpCode.Success);
    }

    /**
     * use case 4.1.2  - remove product
     * @param storeName
     * @param productName
     * @return false always
     */
    @Override
    public Response<Boolean> removeProductFromStore(String storeName, String productName) {
        return new Response<>(true,OpCode.Success);
    }

    /**
     * use case 4.1.3 - edit product details
     * @param productData
     * @return
     */
    @Override
    public Response<Boolean> editProductFromStore(ProductData productData) {
        return new Response<>(true,OpCode.Success);
    }

    /**
     * use case 4.2.1.1 -add product to store
     */
    @Override
    public Response<Boolean> addDiscountToStore(String storeName, Discount discount) {
        return new Response<>(true,OpCode.Success);
    }

    /**
     * use case 4.2.1.2 -remove product from store
     */
    @Override
    public Response<Boolean> deleteDiscountFromStore(int discountId, String storeName) {
        return new Response<>(true,OpCode.Success);
    }

    /**
     * use case 4.5 - add manager to store
     * @param youngOwner user to be owner
     * @param storeName
     * @return
     */
    @Override
    public Response<Boolean> addManager(Subscribe youngOwner, String storeName) {
        return new Response<>(true,OpCode.Success);
    }

    /**
     * use case 4.6.1 - add permissions
     * @param permissions
     * @param storeName
     * @param userName
     * @return
     */
    @Override
    public Response<Boolean> addPermissions(List<PermissionType> permissions, String storeName, String userName) {
        return new Response<>(true,OpCode.Success);
    }

    /**
     * use case 4.6.2 - remove permissions
     * @param permissions
     * @param storeName
     * @param userName
     * @return
     */
    @Override
    public Response<Boolean> removePermissions(List<PermissionType> permissions, String storeName, String userName) {
        return new Response<>(true,OpCode.Success);
    }

    /**
     * use case 4.7 - remove manager
     * @param userName
     * @param storeName
     * @return
     */
    @Override
    public Response<Boolean> removeManager(Subscribe userName, String storeName) {
        return new Response<>(true,OpCode.Success);
    }

    /**
     * use case 6.4.1 -watch user history
     * @return
     */
    @Override
    public boolean canWatchUserHistory() {
        return false;
    }

    /**
     * use case 4.10, 6.4.2 - watch store history
     * @param storeName - the store name to watch history
     * @return
     */
    @Override
    public boolean canWatchStoreHistory(String storeName) {
        return true;
    }

    /**
     * use case 3.3 - write review
     * the function check if the product is purchased
     * @param storeName - the store name
     * @param productName - the product name
     * @return
     */
    @Override
    public boolean isItPurchased(String storeName, String productName){
        return true;
    }

    /**
     * use case 2.8 - purchase cart
     * @param paymentData - the payment details
     * @param addresToDeliver - the address to shift
     * @return
     */
    @Override
    public Response<Boolean> buyCart(PaymentData paymentData, DeliveryData addresToDeliver) {
        return new Response<>(true, OpCode.Success);
    }


    /**
     * reserve cart
     * @return cart
     */
    @Override
    public boolean reserveCart() {
        return true;
    }

    /**
     * use case 3.7 - watch purchase history
     * the function return the purchase list
     * @return
     */
    @Override
    public Response<List<Purchase>> watchMyPurchaseHistory() {
        return new Response<>(new LinkedList<>(),OpCode.Success);
    }

    /**
     * use case 3.3 - add review
     * @param review - the review to add
     */
    @Override
    public boolean addReview(Review review) {
        return true;
    }

    @Override
    public List<Store> getMyManagedStores(){
        return new ArrayList<>();

    }

    @Override
    public Request addRequest(String storeName, String content) {
        return new Request(getName(),storeName,content);
    }

    @Override
    public Set<StorePermissionType> getPermissionsForStore(String storeName) {
      return new HashSet<>();
    }
}
