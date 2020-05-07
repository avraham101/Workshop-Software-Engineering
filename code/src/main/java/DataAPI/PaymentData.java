package DataAPI;

public class PaymentData {

    private String name;
    private String address;
    private int age;
    private String creditCard;
    private double totalPrice;
    private String country;

    public PaymentData(String name, String address, int age, String creditCard) {
        this.name = name;
        this.address = address;
        this.age = age;
        this.creditCard = creditCard;
        this.totalPrice = 0;
        this.country="";
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    // ============================ getters & setters ============================ //
}
