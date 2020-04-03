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

    public Permission(Subscribe sub, HashSet<PermissionType> permissionTypes) {
        this.owner = owner;
        permissionType=permissionTypes;
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
     * @return true if the permission doesnt exists or if the manager is not owner
     */
    public boolean addType(PermissionType type) {
        if(permissionType.contains(PermissionType.OWNER)||this.permissionType.contains(type))
            return false;
        if(type==PermissionType.OWNER)
            permissionType.clear();
        return this.permissionType.add(type);
    }

    /**
     * check if user has permission to CRUD products of store
     * @return true if user has permission to CRUD products of store
     */
    public boolean canAddProduct() {
        return permissionType.contains(PermissionType.OWNER)||
                permissionType.contains(PermissionType.PRODUCTS_INVENTORY);
    }

    public boolean removeType(PermissionType type){
        if(!this.permissionType.contains(type))
            return false;
        this.permissionType.remove(type);
        return true;
    }

    public boolean canAddOwner() {
        return permissionType.contains(PermissionType.ADD_OWNER)||
                permissionType.contains(PermissionType.OWNER);
    }
}

