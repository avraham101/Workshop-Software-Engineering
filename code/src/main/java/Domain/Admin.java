package Domain;

public class Admin extends Subscribe {

    public Admin(String userName, String password) {
        super(userName, password);
    }

    /**
     * use case 6.4.1
     * get if the user ia an admin
     * @return - if the user is admin
     */
    @Override
    public boolean canWatchUserHistory() {
        return true;
    }

    /**
     * use case 6.4.2
     * @param storeName - the store name to watch history
     * @return
     */
    @Override
    public boolean canWatchStoreHistory(String storeName) {
        return true;
    }
}

