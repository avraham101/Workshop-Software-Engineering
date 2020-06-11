package AcceptanceTests.AcceptanceTestDataObjects;

import java.time.LocalDate;

public class DayVisitData {

    private LocalDate date;
    private int guestVisit;
    private int subVisit;
    private int managerVisit;
    private int ownerVisit;
    private int adminVisit;


    public DayVisitData(LocalDate date, int guestVisit, int subVisit,
                        int managerVisit, int ownerVisit, int adminVisit) {
        this.date = date;
        this.guestVisit = guestVisit;
        this.subVisit = subVisit;
        this.managerVisit = managerVisit;
        this.adminVisit = adminVisit;
        this.ownerVisit = ownerVisit;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getGuestVisit() {
        return guestVisit;
    }

    public void setGuestVisit(int guestVisit) {
        this.guestVisit = guestVisit;
    }

    public int getSubVisit() {
        return subVisit;
    }

    public void setSubVisit(int subVisit) {
        this.subVisit = subVisit;
    }

    public int getManagerVisit() {
        return managerVisit;
    }

    public void setManagerVisit(int managerVisit) {
        this.managerVisit = managerVisit;
    }

    public int getOwnerVisit() {
        return ownerVisit;
    }

    public void setOwnerVisit(int ownerVisit) {
        this.ownerVisit = ownerVisit;
    }

    public int getAdminVisit() {
        return adminVisit;
    }

    public void setAdminVisit(int adminVisit) {
        this.adminVisit = adminVisit;
    }

    public int getTotal() {
        return getAdminVisit() +
                getGuestVisit() +
                getManagerVisit() +
                getManagerVisit() +
                getOwnerVisit();
    }
}
