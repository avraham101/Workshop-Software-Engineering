package Domain;


import DataAPI.PermissionType;
import Domain.Notification.AddOwnerNotification;
import Domain.Notification.approve_notification;
import Persitent.Cache;
import Persitent.DaoInterfaces.ISubscribeDao;
import Persitent.DaoProxy.SubscribeDaoProxy;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name="manager_agreement")
public class OwnerAgreement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ElementCollection
    @CollectionTable(name="agreement_approvers", joinColumns=@JoinColumn(name="id"))
    @Column(name="manager")
    private Set<String> managers;

    @Column(name="givenby")
    private String givenBy;

    @Column(name="owner")
    private String owner;

    @Column(name="store")
    private String store;

    @Transient
    private final ISubscribeDao dao;

    @Transient
    private final Cache cache;

    public OwnerAgreement(Set<String> managers, String givenBy, String owner, String store) {
        this.managers = managers;
        this.givenBy = givenBy;
        this.owner = owner;
        this.store = store;
        dao=new SubscribeDaoProxy();
        cache=new Cache();
    }

    public OwnerAgreement() {
         dao= new SubscribeDaoProxy();
         cache=new Cache();
    }

    /**
     * approve the manger to be an owner
     * @param name
     * @return if the owner was added as new owner
     */
    @Transactional
    public boolean approve(String name){
        if(managers.contains(name)){
            managers.remove(name);
            if(!managers.isEmpty())
                return false;
            Subscribe mainOwner=cache.findSubscribe(givenBy);
            Subscribe subOwner=cache.findSubscribe(owner);
            Subscribe originalOwner;
            if(mainOwner!=null){ //check if db fell
                if(mainOwner.addManager(subOwner,store).getValue()){
                    originalOwner=mainOwner;
                }
                else{
                    Map<String, Permission> originalPermissions = subOwner.getPermissions();
                    Permission originalPermissionForStore = originalPermissions.get(store);
                    String originalOwnerName =  originalPermissionForStore.getGivenBy();
                     originalOwner=cache.findSubscribe(originalOwnerName);

                }

                List<PermissionType> permissionList=new ArrayList<>();
                permissionList.add(PermissionType.OWNER);
                originalOwner.addPermissions(permissionList,store,owner);
                //dao.update(mainOwner);
                dao.update(originalOwner);
                subOwner.sendNotification(new AddOwnerNotification(store));
                dao.update(subOwner);
                return true;
            }
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public String getGivenBy() {
        return givenBy;
    }

    public String getOwner() {
        return owner;
    }

    public void sendNotifications() {
        for(String name:managers)
            cache.findSubscribe(name).sendNotification(new approve_notification(store,owner));
    }

    public boolean containsOwner(String owner){
        return managers.contains(owner);
    }
}
