package Domain;

import DataAPI.PermissionType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Entity
@Table(name="permission")
public class Permission implements Serializable {


    @Id
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="owner",referencedColumnName = "username")
    private Subscribe owner;

    @Id
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="store",referencedColumnName = "storename")
    private Store store;

    @Column(name="givenby",nullable = true)
    private String givenBy;

    @Transient
    private HashSet<PermissionType> permissionType;

    @Transient
    private ReentrantReadWriteLock lock;

    public Permission(Subscribe owner) {
        this.owner = owner;
        permissionType=new HashSet<>();
        lock=new ReentrantReadWriteLock();
    }

    public Permission() {
    }

    public Permission(Subscribe owner, Store store) {
        this.owner = owner;
        this.store = store;
        permissionType=new HashSet<>();
        lock=new ReentrantReadWriteLock();
    }

    public Permission(Subscribe sub, HashSet<PermissionType> permissionTypes) {
        this.owner = sub;
        permissionType=permissionTypes;
        lock=new ReentrantReadWriteLock();
    }

    // ============================ getters & setters ============================ //

    public Subscribe getOwner() {
        return owner;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        if(store!=null)
            this.store = store;
    }

    public HashSet<PermissionType> getPermissionType() {
        lock.readLock().lock();
        HashSet<PermissionType> p = new HashSet<>(permissionType);
        lock.readLock().unlock();
        return p;
    }

    // ============================ getters & setters ============================ //

    /**
     * Use case 3.2 - open store
     * use case 4.6.1 - add permissions
     * @param type - the permission type
     * @return true if the permission does'nt exists or if the manager is not owner
     */
    public boolean addType(PermissionType type) {
        lock.writeLock().lock();
        if(permissionType.contains(PermissionType.OWNER)||this.permissionType.contains(type)) {
            lock.writeLock().unlock();
            return false;
        }
        if(type==PermissionType.OWNER)
            permissionType.clear();
        boolean result=this.permissionType.add(type);
        lock.writeLock().unlock();
        return result;
    }

    /**
     * use case 4.1 - manage products
     * check if user has permission to CRUD products of store
     * @return true if user has permission to CRUD products of store
     */
    public boolean canAddProduct() {
        lock.readLock().lock();
        boolean result=permissionType.contains(PermissionType.OWNER)||
                permissionType.contains(PermissionType.PRODUCTS_INVENTORY);
        lock.readLock().unlock();
        return result;
    }

    /**
     * use case 4.6.2 - remove permissions
     * remove the type from permissions list
     * @param type
     * @return
     */
    public boolean removeType(PermissionType type){
        lock.writeLock().lock();
        boolean result=permissionType.remove(type);
        lock.writeLock().unlock();
        return result;
    }

    /**
     * use case 4.5 - add manager
     * @return true if can
     */
    public boolean canAddOwner() {
        lock.readLock().lock();
        boolean result=permissionType.contains(PermissionType.ADD_OWNER)||
                permissionType.contains(PermissionType.OWNER);
        lock.readLock().unlock();
        return result;
    }

    public boolean canAddManager() {
        lock.readLock().lock();
        boolean result=permissionType.contains(PermissionType.ADD_MANAGER)||
                permissionType.contains(PermissionType.OWNER);
        lock.readLock().unlock();
        return result;
    }

    public boolean canCRUDPolicyAndDiscount() {
        lock.readLock().lock();
        boolean result=permissionType.contains(PermissionType.CRUD_POLICY_DISCOUNT)||
                permissionType.contains(PermissionType.OWNER);
        lock.readLock().unlock();
        return result;
    }
}

