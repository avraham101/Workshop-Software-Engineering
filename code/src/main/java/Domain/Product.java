package Domain;

import DataAPI.ProductData;
import DataAPI.PurchaseTypeData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    /**
     * clone function for edit product
     * @param productData
     * @param category
     * parameters to get data from
     */

    public void edit(ProductData productData, Category category) {
        this.purchaseType = createPurchaseType(productData.getPurchaseType());
        category.removeProduct(name);
        this.category = category;
        category.addProduct(this);
        this.amount=productData.getAmount();
        this.price=productData.getPrice();
        this.discount=productData.getDiscount();
    }



    private PurchaseType createPurchaseType(PurchaseTypeData purchaseType) {
        if(purchaseType==PurchaseTypeData.IMMEDDIATE)
            return new PurchaseType();//immediate purchase type we have only one type
        return null;
    }

    /**
     * use case 2.4.1 - view all products from store
     * @return the price of the product after discount
     */
    public double getDiscountPrice() {
        double p = price;
        for (Discount d: discount) {
            p = p -  p* d.getPercentage();
        }
        return p;
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

    public boolean equal(ProductData product) {
        return amount == product.getAmount() &&
                product.getPrice()==price &&
                name.equals(product.getProductName()) &&
                category.getName().equals(product.getCategory());
    }

}
