package DataAPI;

import java.util.List;

public class ManagerData {

    String storeName;
    String userName;
    List<PermissionType> permissions;

    public ManagerData(String storeName, String userName, List<PermissionType> permissions) {
        this.storeName = storeName;
        this.userName = userName;
        this.permissions = permissions;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getUserName() {
        return userName;
    }


    public List<PermissionType> getPermissions() {
        return permissions;
    }
}
