package DataAPI;

public class PaymentData {

    private String name;
    private String address;
    private int age;
    private String creditCard;
    private double totalPrice;
    private String country;

    //new fields
    private int cvv;
    private int id;
    private int transactionId;
    private String city;
    private int zip;

    public PaymentData(String name, String address, int age, String creditCard,int cvv,int id) {
        this.name = name;
        this.address = address;
        this.age = age;
        this.creditCard = creditCard;
        this.cvv=cvv;
        this.totalPrice = 0;
        this.country="";
        this.id=id;
    }

    // ============================ getters & setters ============================ //

    public String getName() {
        return name;
    }


    public String getAddress() {
        return address;
    }


    public String getCreditCard() {
        return creditCard;
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

    public String getCountry() {
        return country;
    }

    public int getCvv() {
        return cvv;
    }

    public int getId() {
        return id;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getCity() {
        return city;
    }

    public int getZip() {
        return zip;
    }

    public void setCity(String city) {
        this.city=city;
    }

    public void setZip(int zip) {
        this.zip=zip;
    }

    // ============================ getters & setters ============================ //
}
