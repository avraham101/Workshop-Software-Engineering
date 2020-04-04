package Domain;

import DataAPI.CartData;
import DataAPI.PaymentData;
import DataAPI.ProductData;
import DataAPI.StoreData;
import Systems.PaymentSystem.PaymentSystem;
import Systems.SupplySystem.SupplySystem;

import java.util.LinkedList;
import java.util.List;

public abstract class UserState {
    private Cart cart;

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
     * use case 3.1 - Logout
     * @param user - the user who using the system
     * @return true if the user changed his state, otherwise false
     */
    public abstract boolean logout(User user);

    /**
     * use case 3.2 - Open Store
     * @param storeDetails - the details of the the store
     * @param paymentSystem - the external payment system.
     * @param supplySystem - the external supply system.
     * @return The Store that we open, otherwise null;
     */
    public abstract Store openStore(StoreData storeDetails, PaymentSystem paymentSystem, SupplySystem supplySystem);

    /**
     * use case 3.5
     * @param storeName - The id of the store
     * @param content - The content of the request
     * @return request if success, null else
     */
    public abstract Request addRequest(String storeName, String content);

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
     * use case 3.3 - write review
     * the function check if the product is purchased
     * @param storeName - the store name
     * @param productName - the product name
     * @return true if the product is purchased
     */
    public abstract boolean isItPurchased(String storeName, String productName);

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
            for (Product product: basket.getProducts().keySet()) {
                priceBeforeDiscount += product.getPrice();
                priceAfterDiscount += product.getDiscountPrice();
                ProductData productData = new ProductData(product, basket.getStore().getName());
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
        Basket basket = this.cart.getBasket(store.getName());
        boolean result;
        if (basket != null) {
            result = basket.addProduct(product, amount);
        }
        else {
            Basket basket1 = new Basket(store);
            cart.getBaskets().put(store.getName(),basket1);
            result = basket1.addProduct(product, amount);
        }
        return result;
    }


    /**
     * use case 4.3
     * @param subscribe
     * @param storeName
     * @return
     */
    public abstract boolean addManager(Subscribe subscribe, String storeName);

    /**
     * use case - 2.8 buy cart
     * @param paymentData - the payment info
     * @param addresToDeliver  - the address to shift
     * @return ture if the cart bought, otherwise false
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
     * use case 3.3 add review
     * the function add a review
     * @param review - the review to add
     */
    public abstract void addReview(Review review);

    /**
     * use case 3.3 add review
     * the function return all the reviews
     * @return
     */
    public List<Review> getReviews() {
        return null;
    }

    /**
     * use case 3.7 - watch purchase history
     * the function return the purchase list
     * @return the purchase list
     */
    public abstract List<Purchase> watchMyPurchaseHistory();

    /**
     * use case 4.10 - watch Store History by store owner
     * @param storeName - the store name to watch history
     * @return the purchase list
     */
    public abstract List<Purchase> watchStoreHistory(String storeName);

    /**
     * use case 6.4.1 - admin watch history purchases of some user
     * @param userName - the user that own the purchases
     * @return - list of purchases that of the user
     */
    public abstract List<Purchase> AdminatchUserPurchasesHistory(String userName);

}
