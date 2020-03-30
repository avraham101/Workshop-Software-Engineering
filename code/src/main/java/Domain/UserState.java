package Domain;

abstract class UserState {
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

    public abstract boolean login(User user,Subscribe subscribe);
}
