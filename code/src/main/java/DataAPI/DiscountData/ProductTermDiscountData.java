package DataAPI.DiscountData;


import DataAPI.DiscountData.TermData.TermData;

public class ProductTermDiscountData implements DiscountData {
    private TermData term;
    private String product;
    private int amount;

    public ProductTermDiscountData(TermData term, String product, int amount) {
        this.term = term;
        this.product = product;
        this.amount = amount;
    }

    public TermData getTerm() {
        return term;
    }

    public void setTerm(TermData term) {
        this.term = term;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
