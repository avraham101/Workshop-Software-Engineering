public class User {
    private UserState state;

    public User() {
        state=new UserState();
    }

    public UserState getState() {
        return state;
    }

    public void setState(UserState state) {
        this.state = state;
    }
}
