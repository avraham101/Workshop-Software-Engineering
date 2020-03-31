package Domain;

import java.util.HashSet;

public class Permission {

    private Subscribe owner;
    private Store store;
    private HashSet<PermissionType> permissionType;

    public Permission(Subscribe owner) {
        this.owner = owner;
        permissionType=new HashSet<>();
    }

    public Permission(Subscribe owner, Store store) {
        this.owner = owner;
        this.store = store;
        permissionType=new HashSet<>();
    }

    public Subscribe getOwner() {
        return owner;
    }

    public void setOwner(Subscribe owner) {
        this.owner = owner;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public HashSet<PermissionType> getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(HashSet<PermissionType> permissionType) {
        this.permissionType = permissionType;
    }

    /**
     * Use case 3.2
     * @param type - the permission type
     * @return true if the permission doesnt exits
     */
    public boolean addType(PermissionType type) {
        if(this.permissionType.contains(type))
            return false;
        this.permissionType.add(type);
        return true;
    }

    public boolean canAddProduct() {
        return permissionType.contains(PermissionType.OWNER)||
                permissionType.contains(PermissionType.PRODUCTS_INVENTORY);
    }
}

