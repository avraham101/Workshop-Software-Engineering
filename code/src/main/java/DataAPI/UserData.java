package DataAPI;


public class UserData {

    String name;
    String password;

    public UserData(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

}
