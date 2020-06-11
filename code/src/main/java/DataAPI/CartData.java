package DataAPI;

import java.util.List;
import java.util.Objects;

public class CartData {

    private double priceBeforeDiscount;
    private List<ProductData> products;

    public CartData(double priceBeforeDiscount, List<ProductData> products) {
        this.priceBeforeDiscount = priceBeforeDiscount;
        this.products = products;
    }

    // ============================ getters & setters ============================ //

    public List<ProductData> getProducts() {
        return products;
    }


    // ============================ getters & setters ============================ //

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartData cartData = (CartData) o;
        return Double.compare(cartData.priceBeforeDiscount, priceBeforeDiscount) == 0 &&
                Objects.equals(products, cartData.products);
    }
}
