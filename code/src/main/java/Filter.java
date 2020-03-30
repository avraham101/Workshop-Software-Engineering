public class Filter {

    private Search search;
    private String value;
    private double minPrice;
    private double maxPrice;
    private String category;

    public Filter(Search search, String value, double minPrice, double maxPrice, String category) {
        this.search = search;
        this.value = value;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.category = category;
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
