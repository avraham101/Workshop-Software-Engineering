package Domain;

public class ProductInCart {

    private String buyer;
    private String storeName;
    private String productName;
    private int amount;
    private double price;

    public ProductInCart(String buyer, String storeName, String productName, int amount, double price) {
        this.buyer = buyer;
        this.storeName = storeName;
        this.productName = productName;
        this.amount = amount;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
