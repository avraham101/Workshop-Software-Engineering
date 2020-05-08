package Domain;

import DataAPI.*;
import Domain.Discount.Discount;
import Domain.PurchasePolicy.PurchasePolicy;
import Publisher.Publisher;
import com.sun.deploy.net.MessageHeader;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Subscribe extends UserState{

    private String userName; //unique
    private String password;
    private ConcurrentHashMap<String, Permission> permissions; //map of <storeName, Domain.Permission>
    private List<Permission> givenByMePermissions; //map of <storeName, Domain.Permission>
    private List<Purchase> purchases;
    private List<Request> requests;
    private List<Review> reviews;
    private AtomicInteger sessionNumber;
    private AtomicInteger notificationNumber;
    private ReentrantReadWriteLock lock;
    private Publisher publisher;
    private ConcurrentLinkedQueue<Notification> notifications;

    public Subscribe(String userName, String password) {
        initSubscribe(userName,password);
    }

    public Subscribe(String userName, String password, Cart cart) {
        this.cart = cart;
        initSubscribe(userName,password);
    }

    private void initSubscribe(String userName, String password) {
        notifications=new ConcurrentLinkedQueue();
        lock=new ReentrantReadWriteLock();
        this.userName = userName;
        this.password = password;
        permissions=new ConcurrentHashMap<>();
        givenByMePermissions=new ArrayList<>();
        purchases=new ArrayList<>();
        requests=new ArrayList<>();
        reviews = new LinkedList<>();
        sessionNumber=new AtomicInteger(-1);
         notificationNumber = new AtomicInteger(0);
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
     * use case 2.8 - purchase cart and savePurchases
     * @param buyer - the name of the user
     */
    @Override
    public void savePurchase(String buyer) {
        this.purchases.addAll(this.cart.savePurchases(buyer));
        this.cart = new Cart();
    }

    /**
     * use case 3.1 - Logout
     * @param user - the user who using the system
     * @return return true. The user changed his state to guest from subscribe.
     */
    @Override
    public boolean logout(User user) {
        user.setState(new Guest());
        sessionNumber.set(-1);
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
        permission.addType(PermissionType.OWNER); //Always true, store just created.
        permissions.put(store.getName(),permission);
        return store;
    }


    /**
     * use case 3.3 - add review
     * @param review - the review to add
     */
    @Override
    public boolean addReview(Review review) {
        reviews.add(review);
        return true;
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
                for(ProductData productData: p.getProduct()) {
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
     * @param requestId
     * @param storeName - The id of the store
     * @param content - The content of the request
     * @return true if success, false else
     */
    @Override
    public Request addRequest(int requestId, String storeName, String content){
        Request request = new Request(userName, storeName, content,requestId);
        requests.add(request);
        return request;
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
        Permission permission=permissions.get(productData.getStoreName());
        if(permission==null)
            return new Response<>(false, OpCode.Dont_Have_Permission);
        if(!permission.canAddProduct())
            return new Response<>(false, OpCode.Dont_Have_Permission);
        return permission.getStore().addProduct(productData);
    }

    /**
     * use case 4.1.2 - remove product
     * @param storeName name of store to remove the product from
     * @param productName name of the product to be removed
     * @return if the product was removed
     */
    @Override
    public Response<Boolean> removeProductFromStore(String storeName, String productName) {
        Permission permission=permissions.get(storeName);
        if(permission==null)
            return new Response<>(false,OpCode.Dont_Have_Permission);
        if(!permission.canAddProduct())
            return new Response<>(false,OpCode.Dont_Have_Permission);
        return permissions.get(storeName).getStore().removeProduct(productName);
    }

    /**
     * use case 4.1.3 - edit product
     * @param productData product to edit
     * @return
     */
    @Override
    public Response<Boolean> editProductFromStore(ProductData productData) {
        Permission permission=permissions.get(productData.getStoreName());
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
        Permission permission=permissions.get(storeName);
        if(permission==null)
            return new Response<>(false, OpCode.Dont_Have_Permission);
        if(!permission.canCRUDPolicyAndDiscount())
            return new Response<>(false, OpCode.Dont_Have_Permission);
        return permission.getStore().addDiscount(discount);
    }
    /**
     * 4.2.1.2 - remove discount
     * @param discountId - id of the discount ro delete
     * @param storeName - name of the store to remove the discount from
     */
    @Override
    public Response<Boolean> deleteDiscountFromStore(int discountId, String storeName) {
        Permission permission=permissions.get(storeName);
        if(permission==null)
            return new Response<>(false, OpCode.Dont_Have_Permission);
        if(!permission.canCRUDPolicyAndDiscount())
            return new Response<>(false, OpCode.Dont_Have_Permission);
        return permission.getStore().deleteDiscount(discountId);
    }

    @Override
    public Response<Boolean> updateStorePolicy(String storeName, PurchasePolicy policy) {
        Permission permission=permissions.get(storeName);
        if(permission==null)
            return new Response<>(false, OpCode.Dont_Have_Permission);
        if(!permission.canCRUDPolicyAndDiscount())
            return new Response<>(false, OpCode.Dont_Have_Permission);
        return permission.getStore().addPolicy(policy);
    }

    /**
     * use case 4.5 - add manager to store
     * @param youngOwner the new manager
     * @param storeName the store to add manager to
     * @return
     */
    @Override
    public Response<Boolean> addManager(Subscribe youngOwner, String storeName) {
        Permission permission=permissions.get(storeName);
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
                if(added)
                    return new Response<>(true,OpCode.Success);
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
                if(removed)
                    return new Response<>(true,OpCode.Success);
                return new Response<>(false,OpCode.Invalid_Permissions);
            }
        }
        lock.readLock().unlock();
        return new Response<>(false,OpCode.Not_Found);
    }

    /**
     * use case 4.7 - remove manager
     * @param userName
     * @param storeName
     * @return
     */
    @Override
    public Response<Boolean>  removeManager(String userName, String storeName) {
        if(!permissions.containsKey(storeName))
            return new Response<>(false,OpCode.Dont_Have_Permission);

        for(Permission p: givenByMePermissions) {
            if (p.getStore().getName().equals(storeName) && p.getOwner().getName().equals(userName)) {
                lock.writeLock().lock();
                p.getOwner().removeManagerFromStore(storeName);
                givenByMePermissions.remove(p);
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
        Store store=permissions.get(storeName).getStore();
        //remove the permission from the user
        permissions.remove(storeName);
        //remove the permission from the store
        store.getPermissions().remove(userName);
        //TODO real time trough this
        sendNotification( new Notification<>(storeName,OpCode.Removed_From_Management,notificationNumber.getAndIncrement()));

    }
    /**
     * use case 4.9.1 - view request
     * @param storeName
     * @return
     */
    @Override
    public List<Request> viewRequest(String storeName) {
        List<Request> output = new LinkedList<>();
        if(storeName==null || !permissions.containsKey(storeName))
            return output;
        Permission permission = permissions.get(storeName);
        if(permission != null){
            Store store = permission.getStore();
            output = new LinkedList<>(store.getRequests().values());
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
    public Response<Request> replayToRequest(String storeName, int requestID, String content) {
        if((storeName==null || content==null))
            return new Response<>(null, OpCode.InvalidRequest);
        Permission permission = permissions.get(storeName);
        if(permission == null)
            return new Response<>(null, OpCode.Dont_Have_Permission);
        Store store = permission.getStore();
        if(store!=null &&
                store.getRequests().containsKey(requestID) &&
                store.getRequests().get(requestID).getCommentReference().compareAndSet(null, content)) {
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
        return permissions.containsKey(storeName);
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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ConcurrentHashMap<String, Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(ConcurrentHashMap<String, Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Permission> getGivenByMePermissions() {
        return givenByMePermissions;
    }

    public void setGivenByMePermissions(List<Permission> givenByMePermissions) {
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

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    @Override
    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public AtomicInteger getSessionNumber() {
        return sessionNumber;
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
     Permission permission = permissions.get(storeName);
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



    public void setPublisher(Publisher publisher) {
        this.publisher=publisher;
    }

    public void sendNotification(Notification<?> notification) {
        notification.setId(notificationNumber.getAndIncrement());
        notifications.add(notification);
        sendAllNotifications();
    }

    public void sendAllNotifications() {
        int id=sessionNumber.get();
        if(!notifications.isEmpty()&&publisher!=null&&id!=-1) {
            publisher.update(String.valueOf(id), new ArrayList<Notification>(notifications));
        }
    }
    @Override
    public void deleteReceivedNotifications(List<Integer> notificationsId) {

        notifications.removeIf(n ->notificationsId.contains(n.getId()));

    }


}
