package Stubs;

import DataAPI.*;
import Domain.Discount.Discount;
import Domain.*;
import Domain.PurchasePolicy.PurchasePolicy;

import java.util.LinkedList;
import java.util.List;

public class UserStub extends User {

    public UserStub(User user) {
        this.setState(user.getState());
    }

    public UserStub() {
        super();
    }

    @Override
    public UserState getState() {
        return new Subscribe("test","test");
    }

    /**
     * use case 2.3 - Login
     * @param subscribe - The new Subscribe State
     * @return
     */
    @Override
    public boolean login(Subscribe subscribe) {
        return true;
    }


    @Override
    public boolean reservedCart() {
        return true;
    }

    @Override
    public void savePurchase(String buyer) {
    }

    /**
     * use case 3.1 - Logout
     * @return true if the user state changed back to guest
     */
    @Override
    public boolean logout() {
        this.state=new Subscribe("hu","mu");
        return true;
    }

    /**
     * use case 3.2 - Open Store
     * @param storeDetails - the details of the the store
     * @return The Store that we open, otherwise null;
     */
    @Override
    public Store openStore(StoreData storeDetails) {
        return new Store(storeDetails.getName(),
                new Permission(new Subscribe("Yuval", this.getPassword())),"description");
    }

    /**
     * use case 4.1.1 - add product to store
     * @param productData
     * @return
     */
    @Override
    public Response<Boolean> addProductToStore(ProductData productData) {
        return new Response<>(true,OpCode.Success);
    }

    /**
     * use case 4.1.2 - remove product from store
     * @param storeName
     * @param productName
     * @return
     */
    @Override
    public Response<Boolean> removeProductFromStore(String storeName, String productName) {
        return new Response<>(true,OpCode.Success);
    }

    /**
     * use case 4.1.3 - edit product in store
     * @param productData
     * @return
     */
    @Override
    public Response<Boolean> editProductFromStore(ProductData productData) {
        return new Response<>(true,OpCode.Success);
    }

    /**
     * use case 4.5 - add manager
     * @param subscribe
     * @param storeName
     * @return
     */
    @Override
    public Response<Boolean> addManager(Subscribe subscribe, String storeName) {
        return new Response<>(true,OpCode.Success);
    }

    /**
     * use case 2.7.4 - add a product to his cart
     * @param store - the store of the product
     * @param product - the product to add
     * @param amount - the amount of the product
     * @return -
     */
    @Override
    public boolean addProductToCart(Store store, Product product, int amount) {
        return true;
    }

    /**
     * use case 3.3 - write review
     * the function check if a product is perchesed
     */
    @Override
    public boolean addReview(Review review) {
        return true;
    }

     /** check if product was purchased
      *  @param storeName   - the store name
     * @param productName
     * @return
     */
    @Override
    public boolean isItPurchased(String storeName, String productName) {
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

    @Override
    public Response<Boolean> updateStorePolicy(String storeName, PurchasePolicy policy) {
        return new Response<>(true,OpCode.Success);
    }

    /**
     * use case 4.6.1
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
     * use case 4.6.2
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
     * use case 4.10, 6.4.2 watch store history
     * @param storeName - the store name to watch history
     * @return
     */
    @Override
    public boolean canWatchStoreHistory(String storeName) {
        return true;
    }

    /**
     * use case 6.4.1 - watch user history
     * @return
     */
    @Override
    public boolean canWatchUserHistory() {
        return true;
    }

    /**
     * use case 3.5 - add request
     * @param storeName
     * @param content
     * @return
     */
    @Override
    public Request addRequest(String storeName, String content) {
        Request r=new Request(getUserName(), storeName, "temp");
        r.setId(-2);
        return r;
    }

    /**
     * use case 4.9.1 - view request
     * @param storeName
     */
    @Override
    public List<Request> viewRequest(Store storeName) {
        List<Request> requests = new LinkedList<>();
        requests.add(new Request(getUserName(), storeName.getName(), "temp", 10));
        return requests;
    }

    /**
     * use case 4.9.2 - replay to Request
     * @param storeName
     * @param requestID
     * @param content
     * @return
     */
    @Override
    public Response<Request> replayToRequest(String storeName, Integer requestID, String content) {
        Request r=new Request(getUserName(), storeName, "temp");
        r.setId(-2);
        return new Response<>(r,OpCode.Success);
    }

    /**
     * use case 3.7 - watch history purchases
     * @return
     */
    @Override
    public Response<List<Purchase>> watchMyPurchaseHistory() {
        return new Response<>(new LinkedList<>(),OpCode.Success);
    }


}
