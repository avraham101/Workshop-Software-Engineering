import java.util.HashMap;
import java.util.List;

public class Subscribe extends UserState{
    private String userName; //unique
    private String password;
    private HashMap<String, Permission> permissions; //map of <storeName, Permission>
    private HashMap<String,Permission> givenByMePermissions; //map of <storeName, Permission>
    private List<Purchase> purchese;
    private List<Request> requests;
}
