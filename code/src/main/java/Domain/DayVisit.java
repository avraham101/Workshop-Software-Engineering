package Domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name="visits_per_day")
public class DayVisit implements Serializable {

    @Id
    @Column(name="date")
    private LocalDate date;

    @Column(name="guest")
    private int guestNumber;

    @Column(name="subscribe")
    private int subscribeNumber;

    @Column(name="manager")
    private int managerNumber;

    @Column(name="owner")
    private int ownerNumber;

    @Column(name="admin")
    private int adminNumber;

    public DayVisit() {
    }

    public DayVisit(LocalDate date) {
        this.date = date;
        guestNumber=0;
        subscribeNumber=0;
        managerNumber=0;
        ownerNumber=0;
        adminNumber=0;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getGuestNumber() {
        return guestNumber;
    }

    public int getSubscribeNumber() {
        return subscribeNumber;
    }

    public int getManagerNumber() {
        return managerNumber;
    }

    public int getOwnerNumber() {
        return ownerNumber;
    }

    public int getAdminNumber() {
        return adminNumber;
    }

    public synchronized void increaseGuest(){
        guestNumber++;
    }

    public synchronized void increaseSubscribe(){
        subscribeNumber++;
    }

    public synchronized void increaseManagers(){
        managerNumber++;
    }

    public synchronized void increaseOwners(){
        ownerNumber++;
    }

    public synchronized void increaseAdmin(){
        adminNumber++;
    }

}
