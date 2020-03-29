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
}
