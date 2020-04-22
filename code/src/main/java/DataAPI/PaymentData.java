package DataAPI;

public class PaymentData {

    private String name;
    private String address;
    private String creditCard;
    private double totalPrise;

    public PaymentData(String name, String address, String creditCard) {
        this.name = name;
        this.address = address;
        this.creditCard = creditCard;
        this.totalPrise = 0;
    }

    // ============================ getters & setters ============================ //

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public double getTotalPrise() {
        return totalPrise;
    }

    public void setTotalPrise(double totalPrise) {
        this.totalPrise = totalPrise;
    }

    // ============================ getters & setters ============================ //
}
