package AcceptanceTests.AcceptanceTestDataObjects;

public class DeliveryDetailsTestData {
    String country;
    String city;
    String street;
    Integer houseNumber;

    public DeliveryDetailsTestData(String country, String city, String street, Integer houseNumber) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public String toString(){
        return country+" "+city+" "+street+" "+houseNumber;
    }

}
