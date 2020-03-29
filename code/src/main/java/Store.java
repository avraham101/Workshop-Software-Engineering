import java.util.HashMap;
import java.util.List;

public class Store {

    private String name; //unique
    private PurchesPolicy purchesPolicy;
    private DiscountPolicy discout;
    private HashMap<String, Product> products;
    private HashMap<String, Category> categoryList;
    private List<Request> requests;
    private HashMap<String, Permission> permissions;
    private SupplySytem supplySytem;
    private PaymentSystem paymentSystem;

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
        return discout;
    }

    public void setDiscout(DiscountPolicy discout) {
        this.discout = discout;
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

    public SupplySytem getSupplySytem() {
        return supplySytem;
    }

    public void setSupplySytem(SupplySytem supplySytem) {
        this.supplySytem = supplySytem;
    }

    public PaymentSystem getPaymentSystem() {
        return paymentSystem;
    }

    public void setPaymentSystem(PaymentSystem paymentSystem) {
        this.paymentSystem = paymentSystem;
    }
}
