package DataAPI;

import java.util.List;
import java.util.Objects;

public class CartData {

    private double priceBeforeDiscount;
    private double priceAfterDiscount;
    private List<ProductData> products;

    public CartData(double priceBeforeDiscount, double priceAfterDiscount, List<ProductData> products) {
        this.priceBeforeDiscount = priceBeforeDiscount;
        this.priceAfterDiscount = priceAfterDiscount;
        this.products = products;
    }

    // ============================ getters & setters ============================ //

    public double getPriceBeforeDiscount() {
        return priceBeforeDiscount;
    }

    public double getPriceAfterDiscount() {
        return priceAfterDiscount;
    }

    public List<ProductData> getProducts() {
        return products;
    }

    public void setPriceBeforeDiscount(double priceBeforeDiscount) {
        this.priceBeforeDiscount = priceBeforeDiscount;
    }

    // ============================ getters & setters ============================ //

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartData cartData = (CartData) o;
        return Double.compare(cartData.priceBeforeDiscount, priceBeforeDiscount) == 0 &&
                Double.compare(cartData.priceAfterDiscount, priceAfterDiscount) == 0 &&
                Objects.equals(products, cartData.products);
    }
}
