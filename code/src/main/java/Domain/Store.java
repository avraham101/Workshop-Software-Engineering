package Domain;

import Systems.PaymentSystem.PaymentSystem;
import Systems.SupplySystem.SupplySystem;

import java.util.HashMap;
import java.util.List;

public class Store {

    private String name; //unique
    private PurchesPolicy purchesPolicy;
    private DiscountPolicy discount;
    private HashMap<String, Product> products;
    private HashMap<String, Category> categoryList;
    private List<Request> requests;
    private HashMap<String, Permission> permissions;
    private SupplySystem supplySystem;
    private PaymentSystem paymentSystem;

    public Store(String name, PurchesPolicy purchesPolicy, DiscountPolicy discount,
                 HashMap<String, Permission> permissions, SupplySystem supplySystem,
                 PaymentSystem paymentSystem) {
        this.name = name;
        this.purchesPolicy = purchesPolicy;
        this.discount = discount;
        this.permissions = permissions;
        this.supplySystem = supplySystem;
        this.paymentSystem = paymentSystem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PurchesPolicy getPurchesPolicy() {
        return purchesPolicy;
    }

    public void setPurchesPolicy(PurchesPolicy purchesPolicy) {
        this.purchesPolicy = purchesPolicy;
    }

    public DiscountPolicy getDiscout() {
        return discount;
    }

    public void setDiscout(DiscountPolicy discount) {
        this.discount = discount;
    }

    public HashMap<String, Product> getProducts() {
        return products;
    }

    public void setProducts(HashMap<String, Product> products) {
        this.products = products;
    }

    public HashMap<String, Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(HashMap<String, Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public HashMap<String, Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(HashMap<String, Permission> permissions) {
        this.permissions = permissions;
    }

    public SupplySystem getSupplySystem() {
        return supplySystem;
    }

    public void setSupplySystem(SupplySystem supplySystem) {
        this.supplySystem = supplySystem;
    }

    public PaymentSystem getPaymentSystem() {
        return paymentSystem;
    }

    public void setPaymentSystem(PaymentSystem paymentSystem) {
        this.paymentSystem = paymentSystem;
    }
}
