package Stubs;

import DataAPI.*;
import Domain.Discount.Discount;
import Domain.*;

import java.util.List;
import java.util.Set;

public class GuestStub extends Guest {

    /**
     * use case 2.3 - Login
     * @param user - The user who using the system
     * @param subscribe - The user state who need to be set
     * @return always true. The user state changed from Guest to Subscribe
     */
    @Override
    public boolean login(User user, Subscribe subscribe) {
        return true;
    }

    /**
     * use case 3.1 - Logout
     * @param user - the user who using the system
     * @return
     */
    @Override
    public boolean logout(User user){
        return false;
    }

    /**
     * use case 3.2 - open store
     * @param storeDetails - the details of the store
     * @return always null. guest cant open store.
     */
    @Override
    public Store openStore(StoreData storeDetails) {
        return null;
    }

    /**
     * use case 4.1.2  - remove product
     * @param storeName
     * @param productName
     * @return false always
     */
    @Override
    public Response<Boolean> removeProductFromStore(String storeName, String productName) {
        return new Response<>(false,OpCode.Not_Login);
    }

    /**
     * use case 4.1.1 -add product
     * @param productData
     * @return false for guest
     */
    @Override
    public Response<Boolean> addProductToStore(ProductData productData){
        return new Response<>(false,OpCode.Not_Login);
    }

    /**
     * use case 4.2.1.1 -add product to store
     */
    @Override
    public Response<Boolean> addDiscountToStore(String storeName, Discount discount) {
        return new Response<>(false,OpCode.Not_Login);
    }

    /**
     * use case 4.2.1.2 -remove product from store
     */
    @Override
    public Response<Boolean> deleteDiscountFromStore(int discountId, String storeName) {
        return new Response<>(false,OpCode.Not_Login);
    }

    /**
     * use case 4.1.3 - edit product details
     * @param productData
     * @return
     */
    @Override
    public Response<Boolean> editProductFromStore(ProductData productData) {
        return super.editProductFromStore(productData);
    }

    /**
     * use case 4.5 - add manager to store
     * @param youngOwner user to be owner
     * @param storeName
     * @return
     */
    @Override
    public Response<Boolean> addManager(Subscribe youngOwner, String storeName) {
        return new Response<>(false,OpCode.Not_Login);
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
        return new Response<>(false,OpCode.Not_Login);
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
        return new Response<>(false,OpCode.Not_Login);
    }

    /**
     * use case 4.7 - remove manager
     * @param userName
     * @param storeName
     * @return
     */
    @Override
    public Response<Boolean> removeManager(String userName, String storeName) {
        return new Response<>(false,OpCode.Not_Login);
    }

    /**
     * use case 2.8 - purchase cart
     * @param paymentData - the payment details
     * @param addresToDeliver - the address to shift
     * @return
     */
    @Override
    public boolean buyCart(PaymentData paymentData, DeliveryData addresToDeliver) {
        return true;
    }

    /**
     * ues case 3.7 - watch my purchase history
     */
    @Override
    public Response<List<Purchase>> watchMyPurchaseHistory() {
        return new Response<>(null,OpCode.Not_Login);
    }

    @Override
    public List<Store> getMyManagedStores(){
        return null;
    }

    @Override
    public Set<StorePermissionType> getPermissionsForStore(String storeName) {
        return null;

    }
}
