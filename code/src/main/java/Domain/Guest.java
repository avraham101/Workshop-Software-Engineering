package Domain;

public class Guest extends UserState {

    @Override
    public boolean login(User user, Subscribe subscribe) {
        user.setState(subscribe);
        return true;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean logout(User user) {
        return false;
    }
}
