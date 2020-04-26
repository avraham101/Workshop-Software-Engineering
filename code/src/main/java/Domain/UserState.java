package Domain;

import DataAPI.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public abstract class UserState {
    protected Cart cart;

    public UserState() {
        this.cart = new Cart();
    }

    // ============================ getters & setters ============================ //

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public abstract String getName();

    public abstract String getPassword();

    // ============================ getters & setters ============================ //

    /**
     * use case 2.3 - Login
     * @param user - The user who using the system
     * @param subscribe - The user state who need to be set
     * @return true if the user changed his state, otherwise false
     */
    public abstract boolean login(User user,Subscribe subscribe);

    /**
     * use case 2.7.1 - watch cart details
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
                //TODO get price after discount
                ProductData productData = new ProductData(product, basket.getStore().getName());
                productData.setAmount(map.get(product));
                products.add(productData);
            }
        }
        return new CartData(priceBeforeDiscount,priceAfterDiscount,products);
    }

    /**
     * use case 2.7.2 - delete product from the cart
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
     * use case 2.7.3 - edit amount of product
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
     * use case 2.7.4 - add a product to his cart
     * @param store - the store of the product
     * @param product - the product to add
     * @param amount - the amount of the product
     * @return - true if add, false if not
     */
    public boolean addProductToCart(Store store, Product product, int amount) {
        return cart.addProduct(store, product,amount);
    }

    /**
     * use case - 2.8 reserveCart cart
     * @return true if the cart bought, otherwise false
     */
    public boolean reserveCart() {
        return cart.reserveCart();
    }

    /**
     * use case 2.8 - buy cart
     * the function updated Delivery Data and Payment Data
     * @param paymentData the payment data
     * @param deliveryData the delivery data
     */
    public void buyCart(PaymentData paymentData, DeliveryData deliveryData) {
        cart.buy(paymentData,deliveryData);
    }

    /**
     * use case 2.8 - use case 2.8 - buy cart
     * the function Cancel Cart
     */
    public void cancelCart() {
        cart.cancel();
    }

    /**
     * use case 2.8 reserveCart cart
     * the function savePurchases the basket
     */
    public abstract void savePurchase(String buyer);

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
     * use case 3.3 - add review
     * the function add a review
     * @param review - the review to add
     */
    public abstract boolean addReview(Review review);

    /**
     * use case 3.3 - add review
     * the function return all the reviews
     * @return
     */
    public List<Review> getReviews() {
        return null;
    }


    /**
     * use case 3.3 - remove review
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
     * use case 3.5 - add request
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
     * use case 4.1.1 - add product
     * @param productData
     * @return
     */
    public abstract boolean addProductToStore(ProductData productData);

    /**
     * use case 4.1.2 - remove product
     * @param storeName
     * @param productName
     * @return
     */
    public abstract boolean removeProductFromStore(String storeName, String productName);

    /**
     * use case 4.1.3 - edit product
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
     * use case 4.9.1 - view request
     * @param storeName
     */
    public abstract List<Request> viewRequest(String storeName);

    /**
     * use case 4.9.2 - replay to Request
     * @param storeName
     * @param requestID
     * @param content
     * @return Request if replay, null else
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
