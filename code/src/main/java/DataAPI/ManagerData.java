package DataAPI;

import Domain.PermissionType;

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

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<PermissionType> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionType> permissions) {
        this.permissions = permissions;
    }
}
