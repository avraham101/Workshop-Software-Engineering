package Domain;

public class User {
    private UserState state;

    public User() {
        state=new Guest();
    }

    public UserState getState() {
        return state;
    }

    public void setState(UserState state) {
        this.state = state;
    }

    public boolean login(Subscribe subscribe) {
        return state.login(this, subscribe);
    }
}
