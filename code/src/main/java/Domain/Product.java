package Domain;

import DataAPI.ProductData;
import DataAPI.PurchaseTypeData;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private String name; //unique
    private int amount;
    private double price;
    private List<Discount> discount;
    private PurchaseType purchaseType;
    private Category category;
    private List<Review> reviews;

    public Product(ProductData productData, Category category) {
        this.name = productData.getProductName();
        this.purchaseType = createPurchaseType(productData.getPurchaseType());
        this.category = category;
        category.addProduct(this);
        this.amount=productData.getAmount();
        this.price=productData.getPrice();
        this.discount=productData.getDiscount();
        this.reviews=new ArrayList<>();
    }

    private PurchaseType createPurchaseType(PurchaseTypeData purchaseType) {
        if(purchaseType==PurchaseTypeData.IMMEDDIATE)
            return new PurchaseType();//immediate purchase type we have only one type
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Discount> getDiscount() {
        return discount;
    }

    public void setDiscount(List<Discount> discount) {
        this.discount = discount;
    }

    public PurchaseType getPurchaseType() {
        return purchaseType;
    }

    public void setPurches(PurchaseType purchases) {
        this.purchaseType = purchases;
    }

    public Category getCategory() {

        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPurchaseType(PurchaseType purchaseType) {
        this.purchaseType = purchaseType;
    }
}
