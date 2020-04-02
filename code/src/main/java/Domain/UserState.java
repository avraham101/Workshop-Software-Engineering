package Domain;

import DataAPI.ProductData;
import DataAPI.StoreData;
import Systems.PaymentSystem.PaymentSystem;
import Systems.SupplySystem.SupplySystem;

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

    public abstract boolean addProductToStore(ProductData productData);

    public abstract boolean removeProductFromStore(String storeName, String productName);

    public abstract boolean editProductFromStore(ProductData productData);

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
        boolean result = false;
        if (basket != null) {
            result = basket.addProduct(product, amount);
        }
        else {
            Basket basket1 = new Basket(store);
            result = basket1.addProduct(product, amount);
        }
        return result;
    }
}
