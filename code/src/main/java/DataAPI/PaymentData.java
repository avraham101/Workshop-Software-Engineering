package DataAPI;

public class PaymentData {

    private String name;
    private String address;
    private String creditCard;
    private double totalPrice;

    public PaymentData(String name, String address, String creditCard) {
        this.name = name;
        this.address = address;
        this.creditCard = creditCard;
        this.totalPrice = 0;
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    // ============================ getters & setters ============================ //
}
