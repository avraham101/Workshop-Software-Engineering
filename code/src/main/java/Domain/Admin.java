package Domain;

public class Admin extends Subscribe {

    public Admin(String userName, String password) {
        super(userName, password);
    }

    /**
     * get if the user ia an admin
     * @return - if the user is admin
     */
    @Override
    public boolean canWatchUserHistory() {
        return true;
    }

    @Override
    public boolean canWatchStoreHistory(String storeName) {
        return true;
    }
}

