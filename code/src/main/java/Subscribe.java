import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Subscribe extends UserState{
    private String userName; //unique
    private String password;
    private HashMap<String, Permission> permissions; //map of <storeName, Permission>
    private HashMap<String,Permission> givenByMePermissions; //map of <storeName, Permission>
    private List<Purchase> purchases;
    private List<Request> requests;

    public Subscribe(String userName, String password) {
        this.userName = userName;
        this.password = password;
        permissions=new HashMap<>();
        givenByMePermissions=new HashMap<>();
        purchases=new ArrayList<>();
        requests=new ArrayList<>();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashMap<String, Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(HashMap<String, Permission> permissions) {
        this.permissions = permissions;
    }

    public HashMap<String, Permission> getGivenByMePermissions() {
        return givenByMePermissions;
    }

    public void setGivenByMePermissions(HashMap<String, Permission> givenByMePermissions) {
        this.givenByMePermissions = givenByMePermissions;
    }

    public List<Purchase> getPurchese() {
        return purchases;
    }

    public void setPurchese(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }
}
