package Domain;

import java.util.List;

public class Product {
    private String name; //unique
    private List<Discount> discount;
    private PurchesType purches;
    private Category category;
    private List<Review> reviews;

    public Product(String name, PurchesType purches, Category category) {
        this.name = name;
        this.purches = purches;
        this.category = category;
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

    public PurchesType getPurches() {
        return purches;
    }

    public void setPurches(PurchesType purches) {
        this.purches = purches;
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
}
