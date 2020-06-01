package Domain;

import DataAPI.*;
import Domain.Discount.Discount;
import Domain.Notification.RemoveNotification;
import Domain.PurchasePolicy.PurchasePolicy;
import Domain.Notification.Notification;
import Persitent.Cache;
import Persitent.DaoHolders.SubscribeDaoHolder;
import Persitent.RequestDao;
import Persitent.SubscribeDao;
import Publisher.Publisher;
import Publisher.SinglePublisher;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="Subscribe")
public class Subscribe extends UserState{

    @Id
    @Column(name="username")
    private String userName; //unique

    @Column(name="password")
    private String password;

    @OneToOne(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="username",referencedColumnName = "username")
    private Cart cart;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade=CascadeType.ALL)
    @MapKeyColumn(name = "store")
    @JoinColumn(name="owner",referencedColumnName = "username",insertable = false,updatable = false)
    private Map<String, Permission> permissions; //map of <storeName, Domain.Permission>

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="givenby",referencedColumnName = "username",insertable = false,updatable = false)
    private List<Permission> givenByMePermissions; //map of <storeName, Domain.Permission>

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade=CascadeType.DETACH)
    @JoinColumn(name="buyer",referencedColumnName = "username",updatable = false)
    private List<Purchase> purchases;

    @OneToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="sender",referencedColumnName = "username",insertable = false,updatable = false)
    private List<Request> requests;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="writer",referencedColumnName = "username",insertable = false,updatable = false)
    private List<Review> reviews;

    @Column(name="sessionNumber")
    private Integer sessionNumber;

    @Transient
    private final ReentrantReadWriteLock lock;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name="subscibe_notifications",
            joinColumns ={@JoinColumn(name = "username", referencedColumnName="username")},
            inverseJoinColumns={@JoinColumn(name="notfiication_id", referencedColumnName="id")}
    )
    private List<Notification> notifications;

    @Transient
    private final SubscribeDaoHolder daos;

    public Subscribe(String userName, String password) {
        this.cart = new Cart(userName);
        initSubscribe(userName,password);
        lock = new ReentrantReadWriteLock();
        daos=new SubscribeDaoHolder();
    }

    public Subscribe() {
        lock=new ReentrantReadWriteLock();
        daos=new SubscribeDaoHolder();
    }

    @Override
    public Cart getCart() {
        return this.cart;
    }

    public Subscribe(String userName, String password, Cart cart) {
        this.cart = cart;
        initSubscribe(userName,password);
        lock = new ReentrantReadWriteLock();
        daos=new SubscribeDaoHolder();
    }

    private void initSubscribe(String userName, String password) {
        notifications=new CopyOnWriteArrayList<>();
        this.userName = userName;
        this.password = password;
        permissions=new ConcurrentHashMap<>();
        givenByMePermissions=new ArrayList<>();
        purchases=new ArrayList<>();
        requests=new ArrayList<>();
        reviews = new LinkedList<>();
        sessionNumber=-1;
    }

    /**
     * use case 2.3 - Login
     * @param user - The user who using the system
     * @param subscribe - The user state who need to be set
     * @return always false. The user state cant be changed from subscribe -> subscribe.
     *         the user already logged
     */
    @Override
    public boolean login(User user, Subscribe subscribe) {
        return false;
    }


    /**
     * 2.7.4 add product to cart
     * @param store - the store of the product
     * @param product - the product to add
     * @param amount - the amount of the product
     * @return
     */
    @Override
    public boolean addProductToCart(Store store, Product product, int amount) {
        cart=daos.getCartDao().find(userName);
        boolean output= super.addProductToCart(store, product, amount);
        if(output){
            return daos.getCartDao().replaceCart(cart);
        }
        return false;
    }

    /**
     * use case 2.8 - purchase cart and savePurchases
     * @param buyer - the name of the user
     */
    @Override
    public void savePurchase(String buyer) {
        this.purchases.addAll(this.cart.savePurchases(this.userName));
        this.daos.getCartDao().remove(this.getCart()); //remove the old cart
        this.cart = new Cart(this.userName);
        this.daos.getCartDao().add(this.getCart()); //add the new cart
    }

    /**
     * use case 3.1 - Logout
     * @param user - the user who using the system
     * @return return true. The user changed his state to guest from subscribe.
     */
    @Override
    public boolean logout(User user) {
        //setSessionNumber(-1);
        user.setState(new Guest());
        return true;
    }

    /**
     * use case 3.2 - open store
     * @param storeDetails - the details of the store
     * @return The store that we opened.
     * this function is synchronized because of the locker in logic manger open store function.
     */
    @Override
    public Store openStore(StoreData storeDetails) {
        Permission permission = new Permission(this);
        Store store = new Store(storeDetails.getName(), permission,storeDetails.getDescription());
        permission.setStore(store);
        // permission.addType(PermissionType.OWNER); //Always true, store just created.
        permissions.put(store.getName(),permission);
        if(!daos.getStoreDao().addStore(store))
            return null;
        permission.addType(PermissionType.OWNER); //Always true, store just created.
        return store;
    }


    /**
     * use case 3.3 - add review
     * @param review - the review to add
     */
    @Override
    public boolean addReview(Review review) {
        return reviews.add(review);

    }

    /**
     * use case 3.3 remove review
     * @param review - the review to remove
     */
    public void removeReview(Review review) {
        reviews.remove(review);
    }

    /**
     * use case 3.3 - write review
     * the function check if the product is purchased
     * @param storeName - the store name
     * @return true if the product is purchased
     */
    @Override
    public boolean isItPurchased(String storeName, String other) {
        lock.readLock().lock();
        for(Purchase p: purchases) {
            if(p.getStoreName().compareTo(storeName)==0) {
                for(ProductPeristentData productData: p.getProduct()) {
                    String productName = productData.getProductName();
                    if(productName.compareTo(other)==0) {
                        lock.readLock().unlock();
                        return true;
                    }
                }
            }
        }
        lock.readLock().unlock();
        return false;
    }

    /**
     * use case 3.5 - add request
     * @param storeName - The id of the store
     * @param content - The content of the request
     * @return true if success, false else
     */
    @Override
    public Request addRequest(String storeName, String content){
        Request request = new Request(userName, storeName, content);
        requests.add(request);
        RequestDao requestDao = daos.getRequestDao();
        if(requestDao.addRequest(request))
            return request;
        return null;
    }

    /**
     * use case 3.7 - watch purchase history
     * the function return the purchase list
     * @return the purchase list
     */
    @Override
    public Response<List<Purchase>> watchMyPurchaseHistory() {
        return new Response<>(purchases,OpCode.Success);
    }


    /**
     * use case 4.1.1 - add product
     * @param productData product details to be added
     * @return if the product was added
     */

    @Override
    public Response<Boolean> addProductToStore(ProductData productData) {
        String storeName=productData.getStoreName();
        Permission permission=daos.getStoreDao().find(storeName).getPermissions().get(userName);
        if(permission==null)
            return new Response<>(false, OpCode.Dont_Have_Permission);
        if(!permission.canAddProduct())
            return new Response<>(false, OpCode.Dont_Have_Permission);

        Store cashStore = daos.getStoreDao().find(productData.getStoreName());
        if(cashStore != null) {
//            Permission storePermission = cashStore.getPermissions().get(this.getName());
//            if(storePermission==null)
//                return new Response<>(false, OpCode.Dont_Have_Permission);
//            if(!storePermission.canAddProduct())
//                return new Response<>(false, OpCode.Dont_Have_Permission);
            Response<Boolean> output = cashStore.addProduct(productData);
            if(output.getValue()) {
                daos.getStoreDao().update(cashStore);
                permission.setStore(cashStore);
            }
            return output;
        }
        return new Response<>(false,OpCode.Store_Not_Found);
//        Store store = storeDao.find(permission.getStore().getName());
//        Response<Boolean> output = store.addProduct(productData);
//        if(output.getValue()) {
//            daos.getStoreDao().update(store);
//            permission.setStore(store);
//
//        }
//        return output;
    }

    /**
     * use case 4.1.2 - remove product
     * @param storeName name of store to remove the product from
     * @param productName name of the product to be removed
     * @return if the product was removed
     */
    @Override
    public Response<Boolean> removeProductFromStore(String storeName, String productName) {
        Permission permission=daos.getStoreDao().find(storeName).getPermissions().get(userName);
        if(permission==null)
            return new Response<>(false,OpCode.Dont_Have_Permission);
        if(!permission.canAddProduct())
            return new Response<>(false,OpCode.Dont_Have_Permission);
        return permission.getStore().removeProduct(productName);
    }

    /**
     * use case 4.1.3 - edit product
     * @param productData product to edit
     * @return
     */
    @Override
    public Response<Boolean> editProductFromStore(ProductData productData) {
        Permission permission=daos.getStoreDao().find((productData.getStoreName())).getPermissions().get(userName);
        if(permission==null)
            return new Response<>(false, OpCode.Dont_Have_Permission);
        if(!permission.canAddProduct())
            return new Response<>(false, OpCode.Dont_Have_Permission);
        return permission.getStore().editProduct(productData);
    }

    /**
     * use case 4.2.1.1 - add discount to store
     * @param storeName
     * @param discount
     * @return
     */
    @Override
    public Response<Boolean> addDiscountToStore(String storeName, Discount discount) {
        Permission permission=daos.getStoreDao().find(storeName).getPermissions().get(userName);
        if(permission==null)
            return new Response<>(false, OpCode.Dont_Have_Permission);
        if(!permission.canCRUDPolicyAndDiscount())
            return new Response<>(false, OpCode.Dont_Have_Permission);
        Response<Boolean> output= permission.getStore().addDiscount(discount);
        if(output.getValue())
            daos.getStoreDao().update(permission.getStore());
        return output;
    }
    /**
     * 4.2.1.2 - remove discount
     * @param discountId - id of the discount ro delete
     * @param storeName - name of the store to remove the discount from
     */
    @Override
    public Response<Boolean> deleteDiscountFromStore(int discountId, String storeName) {
        Permission permission=daos.getStoreDao().find(storeName).getPermissions().get(userName);
        if(permission==null)
            return new Response<>(false, OpCode.Dont_Have_Permission);
        if(!permission.canCRUDPolicyAndDiscount())
            return new Response<>(false, OpCode.Dont_Have_Permission);
        Response<Boolean> output= permission.getStore().deleteDiscount(discountId);
        if(output.getValue())
            daos.getStoreDao().update(permission.getStore());
        return output;
    }

    @Override
    public Response<Boolean> updateStorePolicy(String storeName, PurchasePolicy policy) {
        Permission permission=daos.getStoreDao().find(storeName).getPermissions().get(userName);
        if(permission==null)
            return new Response<>(false, OpCode.Dont_Have_Permission);
        if(!permission.canCRUDPolicyAndDiscount())
            return new Response<>(false, OpCode.Dont_Have_Permission);
        Response<Boolean> output= permission.getStore().addPolicy(policy);
        if(output.getValue())
            daos.getStoreDao().update(permission.getStore());
        return output;
    }

    /**
     * use case 4.3.1 - manage owner
     * @param storeName the name of the store to be manager of
     * @param newOwner the user to be manager of the store
     * @return
     */
    @Override
    public Response<Boolean> addOwner(String storeName, String newOwner) {
        Permission permission=daos.getStoreDao().find(storeName).getPermissions().get(userName);
        if(permission==null)
            return new Response<>(false,OpCode.Dont_Have_Permission);
        Store store=permission.getStore();
        if(store==null||!permission.isOwner())
            return new Response<>(false,OpCode.Dont_Have_Permission);
        Response<Boolean> output= store.addOwner(this.userName,newOwner);
        if(output.getValue())
            daos.getStoreDao().update(store);
        return output;
    }

    /**
     * use case 4.3.2 - approve manage owner
     * @param storeName the name of the store to be manager of
     * @param newOwner the user to be manager of the store
     * @return
     */
    @Override
    public Response<Boolean> approveManageOwner(String storeName, String newOwner) {
        Permission permission=daos.getStoreDao().find(storeName).getPermissions().get(userName);
        if(permission==null)
            return new Response<>(false,OpCode.Dont_Have_Permission);
        Store store=permission.getStore();
        if(store==null||!permission.isOwner())
            return new Response<>(false,OpCode.Dont_Have_Permission);
        Response<Boolean> output= store.approveAgreement(this.userName,newOwner);
        if(output.getValue())
            daos.getStoreDao().update(store);
        return output;
    }

    /**
     * use case 4.5 - add manager to store
     * @param youngOwner the new manager
     * @param storeName the store to add manager to
     * @return
     */
    @Override
    public Response<Boolean> addManager(Subscribe youngOwner, String storeName) {
        Permission permission=daos.getStoreDao().find(storeName).getPermissions().get(userName);
        if(permission==null)
            return new Response<>(false,OpCode.Dont_Have_Permission);
        Store store=permission.getStore();
        if(store==null||!permission.canAddOwner())
            return new Response<>(false,OpCode.Dont_Have_Permission);
        //create new permission process
        Permission newPermission=new Permission(youngOwner,store);
        if(store.getPermissions().putIfAbsent(youngOwner.getName(),newPermission)==null) {
            youngOwner.getPermissions().put(storeName, newPermission);
            lock.writeLock().lock();
            givenByMePermissions.add(newPermission);
            newPermission.setGivenBy(userName);
            daos.getPermissionDao().addPermission(newPermission);
            lock.writeLock().unlock();
            return new Response<>(true,OpCode.Success);
        }
        return new Response<>(false,OpCode.Already_Exists);

    }

    /**
     * use case 4.6.1 - add permissions
     * @param permissions types to be added
     * @param storeName store to be added
     * @param userName user to add permissions to
     * @return if the permissions were added
     */
    @Override
    public Response<Boolean> addPermissions(List<PermissionType> permissions, String storeName, String userName) {
        lock.readLock().lock();
        for(Permission p: givenByMePermissions){
            if(p.getStore().getName().equals(storeName)&&p.getOwner().getName().equals(userName)){
                boolean added=false;
                for(PermissionType type: permissions)
                    added=added|p.addType(type);
                lock.readLock().unlock();
                if(added) {
                    Cache cache = new Cache();
                    Subscribe managerWithNewPermissions = cache.findSubscribe(userName);
                    if(managerWithNewPermissions!=null)
                        managerWithNewPermissions.permissions.put(storeName,p); //if he is logged in he will get permissions in real time
                    return new Response<>(true, OpCode.Success);
                }
                return new Response<>(false,OpCode.Already_Exists);
            }
        }
        lock.readLock().unlock();
        return new Response<>(false,OpCode.Dont_Have_Permission);
    }

    /**
     * use case 4.6.2 - remove permissions
     * @param permissions types to be removed
     * @param storeName store to be removed from
     * @param userName user to remove permissions from
     * @return
     */
    @Override
    public Response<Boolean>  removePermissions(List<PermissionType> permissions, String storeName, String userName) {
        lock.readLock().lock();
        for(Permission p: givenByMePermissions){
            if(p.getStore().getName().equals(storeName)&&p.getOwner().getName().equals(userName)){
                boolean removed=false;
                for(PermissionType type: permissions)
                    removed=removed|p.removeType(type);
                lock.readLock().unlock();
                if(removed) {
                    Cache cache = new Cache();
                    Subscribe managerWithNewPermissions = cache.findSubscribe(userName);
                    if(managerWithNewPermissions!=null)
                        managerWithNewPermissions.permissions.put(storeName,p); //if he is logged in he will get permissions in real time
                    return new Response<>(true, OpCode.Success);
                }
                return new Response<>(false,OpCode.Invalid_Permissions);
            }
        }
        lock.readLock().unlock();
        return new Response<>(false,OpCode.Not_Found);
    }

    /**
     * use case 4.7 - remove manager
     * @param xManager
     * @param storeName
     * @return
     */
    @Override
    public Response<Boolean>  removeManager(Subscribe xManager, String storeName) {
        if(!permissions.containsKey(storeName))
            return new Response<>(false,OpCode.Dont_Have_Permission);

        for(Permission p: givenByMePermissions) {
            if (p.getStore().getName().equals(storeName) && p.getOwner().getName().equals(xManager.userName)) {
                lock.writeLock().lock();
                p.getOwner().removeManagerFromStore(storeName);
                givenByMePermissions.remove(p);
                xManager.getPermissions().remove(storeName);
                lock.writeLock().unlock();
                return new Response<>(true,OpCode.Success);
            }
        }
        return new Response<>(false,OpCode.Not_Found);
    }

    /**
     * use case 4.7 - remove manager
     * remove manager form store and the managers he managed
     * @param storeName the store to remove to be manager from and the mangers
     * managed by me
     */
    private void removeManagerFromStore(String storeName) {
        Permission permission=null;
        lock.writeLock().lock();
        for(Permission p: givenByMePermissions) {
            if (p.getStore().getName().equals(storeName)) {
                p.getOwner().removeManagerFromStore(storeName);
                permission=p;
            }
        }
        if(permission!=null)
            givenByMePermissions.remove(permission);
        lock.writeLock().unlock();
        Store store=daos.getStoreDao().find(storeName);
        //Store store=permissions.get(storeName).getStore();
        //remove the permission from the user
        Permission p=daos.getPermissionDao().findPermission(permissions.get(storeName));
        permissions.remove(storeName);
        //remove the permission from the store
        store.getPermissions().remove(userName);
        removePermission(p);
        store.removeAgreement(userName);
        store.approveAgreementsOfUser(userName);
        daos.getStoreDao().update(store);
        sendNotification( new RemoveNotification(storeName,OpCode.Removed_From_Management));
    }

    /**
     * use case 4.9.1 - view request
     * @param store
     * @return
     */
    @Override
    public List<Request> viewRequest(Store store) {
        List<Request> output = new LinkedList<>();
        if( !permissions.containsKey(store.getName()))
            return output;
        Permission permission = daos.getStoreDao().find(store.getName()).getPermissions().get(userName);
        if(permission != null){
            output = new LinkedList<>(permission.getStore().getRequests().values());
        }
        return output;
    }

    /**
     * use case 4.9.2 - replay to request
     * @param storeName
     * @param requestID
     * @param content
     * @return
     */
    @Override
    public Response<Request> replayToRequest(String storeName, Integer requestID, String content) {
        if(requestID==null || storeName==null || content==null)
            return new Response<>(null, OpCode.InvalidRequest);
        Permission permission = daos.getStoreDao().find(storeName).getPermissions().get(userName);
        if(permission == null)
            return new Response<>(null, OpCode.Dont_Have_Permission);
        Store store = permission.getStore();
        Request request = store.getRequests().get(requestID);
        if(store!=null &&
                request!=null &&
                request.setComment(content)) {
            daos.getRequestDao().update(request);

            Store storeToUpdate = daos.getStoreDao().find(storeName);
            if(storeToUpdate !=null)
                storeToUpdate.getRequests().put(requestID,request);
            return new Response<>(store.getRequests().get(requestID),OpCode.Success);
        }
        return new Response<>(null, OpCode.Dont_Have_Permission);
    }

    /**
     * use case 4.10 , 6.4.2 - watch Store History by store owner or admin
     * @param storeName - the store name to watch history
     * @return if can watch the purchase list
     */
    @Override
    public boolean canWatchStoreHistory(String storeName) {
        Store store = daos.getStoreDao().find(storeName);
        Permission permission = store.getPermissions().get(this.userName);
        if(permission==null)
            return false;
        permissions.put(storeName,permission);
        return true;
    }

    private void removePermission(Permission p) {
        daos.getPermissionDao().removePermissionFromSubscribe(p,this);
    }

    /**
     * use case 6.4.1 - watch user history check
     * @return if can watch another user purchase list
     */
    @Override
    public boolean canWatchUserHistory() {
        return false;
    }

    // ============================ getters & setters ============================ //

    @Override
    public String getName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public Map<String, Permission> getPermissions() {
        return permissions;
    }

    public List<Permission> getGivenByMePermissions() {
        return givenByMePermissions;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    @Override
    public List<Review> getReviews() {
        return reviews;
    }

    public synchronized Integer getSessionNumber() {
        return sessionNumber;
    }

    @Override
    public synchronized boolean setSessionNumber(Integer sessionNumber) {
        if(sessionNumber!=-1 && this.sessionNumber==-1) { //login
            this.sessionNumber = sessionNumber;
            return true;
        }
        if(sessionNumber==-1 && this.sessionNumber!=-1) { //logout
            this.sessionNumber = sessionNumber;
            return true;
        }
        return false;
    }

    //make permissions concurrent
    public void initPermissions(){
        this.notifications=new CopyOnWriteArrayList<>(this.notifications);
        if(!(this.permissions instanceof ConcurrentHashMap)) {
            this.permissions = new ConcurrentHashMap<>(this.permissions);
            for (Permission p : this.permissions.values()){
                p.getOwner().initPermissions();
                p.getStore().initPermissions();
            }
        }
    }
    /**
     * gets given user Status: admin/manager/regular
     * @return the user status
     */
    public StatusTypeData getStatus(){
        if(this.canWatchUserHistory()) {
            return StatusTypeData.ADMIN;
        }
        else if(!permissions.isEmpty()){
            return StatusTypeData.MANAGER;
        }
        else{
            return StatusTypeData.REGULAR;
        }

    }

    /**
     *
     * @return list of stores managed by the user
     */
    @Override
    public List<Store> getMyManagedStores(){
        List<Permission> storesPermissions = new ArrayList<Permission>(permissions.values());
        List<Store> myStores = new ArrayList<>();
        for (Permission p: storesPermissions ) {
            myStores.add(p.getStore());
        }
        if(myStores.isEmpty())
            return null;
        return myStores;
    }

    /**
     *
     * @param storeName
     * @return list of permission for store
     */
    @Override
    public Set<StorePermissionType> getPermissionsForStore(String storeName) {
        Permission permission = daos.getStoreDao().find(storeName).getPermissions().get(userName);
        if(permission==null)
            return null;
        Set<StorePermissionType> permissionsForStore = new HashSet<>();
        HashSet<PermissionType> permissionTypes= permission.getPermissionType();
        if(permissionTypes.contains(PermissionType.OWNER)){
            permissionsForStore.add(StorePermissionType.OWNER);
            return permissionsForStore;
        }
        if(permission.canAddProduct())
            permissionsForStore.add(StorePermissionType.PRODUCTS_INVENTORY);
        if(permission.canAddOwner())
            permissionsForStore.add(StorePermissionType.ADD_OWNER);
        if(permission.canAddManager())
            permissionsForStore.add(StorePermissionType.ADD_MANAGER);
        if(!givenByMePermissions.isEmpty())
            permissionsForStore.add(StorePermissionType.DELETE_MANAGER);
        if(permissionTypes.contains(PermissionType.CRUD_POLICY_DISCOUNT))
            permissionsForStore.add(StorePermissionType.CRUD_POLICY_DISCOUNT);
        return permissionsForStore;

    }

    /**
     * return all the managers of a specific store that user with id managed
     * @return managers of specific store
     */
    @Override
    public Response<List<String>> getManagersOfStoreUserManaged(String storeName) {
        List<String> managers=new LinkedList<>();
        lock.readLock().lock();
        for( Permission p:givenByMePermissions)
            if(p.getStore().getName().equals(storeName))
                managers.add(p.getOwner().getName());
        lock.readLock().unlock();
        return new Response<>(managers,OpCode.Success);
    }


    public void sendNotification(Notification<?> notification) {
        if(daos.getNotificationDao().add(notification, this.getName())) {
            notifications.add(notification);
            sendAllNotifications();
        }
    }

    public void sendAllNotifications() {
        int id=getSessionNumber();
        Publisher publisher= SinglePublisher.getInstance();
        if(!notifications.isEmpty()&&publisher!=null&&id!=-1) {
            publisher.update(String.valueOf(id), new ArrayList<Notification>(notifications));
        }
    }
    @Override
    public void deleteReceivedNotifications(List<Integer> notificationsId) {

        List<Notification> remove = new LinkedList<>();
        for(Notification not: this.notifications) {
            for(int d:notificationsId) {
                if(not.getId()==d){
                    if(daos.getNotificationDao().remove(d))
                    remove.add(not);
                }
            }
        }
        this.notifications.removeAll(remove);

    }

    @Override
    public Boolean sendMyNotifications() {
        sendAllNotifications();
        return true;
    }
}
