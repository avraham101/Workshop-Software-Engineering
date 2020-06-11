package DataAPI;
public class StoreData {

        private String name;
        private String description;

    public StoreData(String name,String description) {
        this.name = name;
        this.description=description;
    }

    // ============================ getters & setters ============================ //

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    // ============================ getters & setters ============================ //

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StoreData)) {
            return false;
        }
        StoreData storeData = (StoreData) obj;
        return (storeData.getName().equals(this.getName()));
    }
}
