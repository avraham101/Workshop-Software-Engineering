package Domain;

public class ProductInCart {

    private String buyer;
    private String storeName;
    private String productName;
    private int amount;

    public ProductInCart(String buyer, String storeName, String productName, int amount) {
        this.buyer = buyer;
        this.storeName = storeName;
        this.productName = productName;
        this.amount = amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getProductName() {
        return productName;
    }

    public int getAmount() {
        return amount;
    }
}
