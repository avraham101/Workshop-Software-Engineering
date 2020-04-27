package Domain.Discount;

import Domain.Product;

import java.util.HashMap;

public class RegularDiscount implements Discount{
    private String product;
    private double percantage;

    public RegularDiscount(String product, double percantage) {
        this.product = product;
        this.percantage = percantage;
    }

    @Override
    public void calculateDiscount(HashMap<Product, Integer> list) {
        for(Product p:list.keySet())
            if(p.getName().equals(product))
                p.setPrice(p.getPrice() * (100-percantage) / 100);
            return;
    }

    @Override
    public boolean checkTerm(HashMap<Product, Integer> list) {
        for(Product p: list.keySet()){
            if(p.getName().equals(product))
                return true;
        }
        return false;
    }

    @Override
    public boolean isValid() {
        return product!=null&&percantage>0&&percantage<=100;
    }
}
