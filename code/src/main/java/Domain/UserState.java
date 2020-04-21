package Domain;

import DataAPI.CartData;
import DataAPI.PaymentData;
import DataAPI.ProductData;
import DataAPI.StoreData;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public abstract class UserState {
    protected Cart cart;

    public UserState() {
        this.cart = new Cart();
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    /**
     * use case 2.3 - - Login
     * @param user - The user who using the system
     * @param subscribe - The user state who need to be set
     * @return true if the user changed his state, otherwise false
     */
    public abstract boolean login(User user,Subscribe subscribe);

    public abstract String getName();

    public abstract String getPassword();

    /**
     * use case 2.7.1 watch cart details
     * return the details about a cart
     * @return - the cart details
     */
    public CartData watchCartDetatils() {
        List<ProductData> products = new LinkedList<>();
        double priceAfterDiscount = 0;
        double priceBeforeDiscount = 0;
        for (Basket basket: cart.getBaskets().values()) {
            HashMap<Product, Integer> map = basket.getProducts();
            for (Product product: map.keySet()) {
                priceBeforeDiscount += product.getPrice();
                priceAfterDiscount += product.getDiscountPrice();
                ProductData productData = new ProductData(product, basket.getStore().getName());
                productData.setAmount(map.get(product));
                products.add(productData);
            }
        }
        return new CartData(priceBeforeDiscount,priceAfterDiscount,products);
    }

    /**
     * use case 2.7.2
     * delete product from the cart
     * @param productName - the product to remove
     * @param storeName - the store that sale this product
     * @return - true if the delete work, false if not
     */
    public boolean deleteFromCart(String productName,String storeName) {
        boolean result = false;
        Basket basket = cart.getBasket(storeName);
        if (basket != null) {
            result = basket.deleteProduct(productName);
        }
        return result;
    }

    /**
     * use case 2.7.3 edit amount of product
     * @param productName - the product to edit it's amount
     * @param storeName - the store of the product
     * @param newAmount - the new amount
     * @return - true if succeeded, false if not
     */
    public boolean editProductInCart(String productName, String storeName, int newAmount) {
        boolean result = false;
        Basket basket = cart.getBasket(storeName);
        if (basket != null && newAmount > 0) {
            result = basket.editAmount(productName, newAmount);
        }
        return result;
    }

    /**
     * use case 2.7.4
     * user add a product to his cart
     * @param store - the store of the product
     * @param product - the product to add
     * @param amount - the amount of the product
     * @return - true if add, false if not
     */
    public boolean addProductToCart(Store store, Product product, int amount) {
        return cart.addProduct(store, product,amount);
    }


    /**
     * use case - 2.8 buy cart
     * @param paymentData - the payment info
     * @param addresToDeliver  - the address to shift
     * @return true if the cart bought, otherwise false
     */
    public boolean buyCart(PaymentData paymentData, String addresToDeliver) {
        List<Purchase> recives = cart.buy(paymentData, addresToDeliver);
        if(recives!=null) {
            savePurchase(recives);
            return true;
        }
        return false;
    }

    /**
     * use case 2.8 buy cart
     * the function save the receives form the buy
     * @param receives - the recives
     */
    protected abstract void savePurchase(List<Purchase> receives);

    /**
     * use case 3.1 - Logout
     * @param user - the user who using the system
     * @return true if the user changed his state, otherwise false
     */
    public abstract boolean logout(User user);

    /**
     * use case 3.2 - Open Store
     * @param storeDetails - the details of the the store
     * @return The Store that we open, otherwise null;
     */
    public abstract Store openStore(StoreData storeDetails);

    /**
     * use case 3.3 add review
     * the function add a review
     * @param review - the review to add
     */
    public abstract boolean addReview(Review review);

    /**
     * use case 3.3 add review
     * the function return all the reviews
     * @return
     */
    public List<Review> getReviews() {
        return null;
    }


    /**
     * use case 3.3 remove review
     * @param review - the review to remove
     */
    public void removeReview(Review review) {
    }

    /**
     * use case 3.3 - write review
     * the function check if the product is purchased
     * @param storeName - the store name
     * @param productName - the product name
     * @return true if the product is purchased
     */
    public abstract boolean isItPurchased(String storeName, String productName);

    /**
     * use case 3.5
     *
     * @param requestId
     * @param storeName - The id of the store
     * @param content - The content of the request
     * @return request if success, null else
     */
    public abstract Request addRequest(int requestId, String storeName, String content);

    /**
     * use case 3.7 - watch purchase history
     * the function return the purchase list
     * @return the purchase list
     */
    public abstract List<Purchase> watchMyPurchaseHistory();


    /**
     * use case 4.1.1
     * @param productData
     * @return
     */
    public abstract boolean addProductToStore(ProductData productData);

    /**
     * use case 4.1.2
     * @param storeName
     * @param productName
     * @return
     */
    public abstract boolean removeProductFromStore(String storeName, String productName);

    /**
     * use case 4.1.3
     * @param productData
     * @return
     */
    public abstract boolean editProductFromStore(ProductData productData);


    /**
     * use case 4.3 - add manager
     * @param subscribe
     * @param storeName
     * @return
     */
    public abstract boolean addManager(Subscribe subscribe, String storeName);

    /**
     * use case 4.6.1 - add permissions
     * @param permissions
     * @param storeName
     * @param userName
     * @return
     */

    public abstract boolean addPermissions(List<PermissionType> permissions, String storeName, String userName);

    /**
     * use case 4.6.2 - remove permissions
     * @param permissions
     * @param storeName
     * @param userName
     * @return
     */
    public abstract boolean removePermissions(List<PermissionType> permissions, String storeName, String userName);

    /**
     * use case 4.7 - remove manager
     * @param userName
     * @param storeName
     * @return
     */
    public abstract boolean removeManager(String userName, String storeName);

    /**
     * use case 4.9.1
     * @param storeName
     */
    public abstract List<Request> viewRequest(String storeName);

    /**
     * use case 4.9.2 -replay to Request
     * @param storeName
     * @param requestID
     * @param content
     * @return true if replay, false else
     */
    public abstract Request replayToRequest(String storeName, int requestID, String content);


    /**
     * use case 4.10 - watch Store History by store owner
     * @param storeName - the store name to watch history
     * @return the purchase list
     */
    public abstract boolean canWatchStoreHistory(String storeName);

    /**
     * use case 6.4.1 - watch user history
     * @return
     */

    public abstract boolean canWatchUserHistory();
}
