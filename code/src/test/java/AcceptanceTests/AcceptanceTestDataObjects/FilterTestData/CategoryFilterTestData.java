package AcceptanceTests.AcceptanceTestDataObjects.FilterTestData;

public class CategoryFilterTestData extends FilterTestData {
    private String category;

    public CategoryFilterTestData(String category) {
        super(FilterType.CATEGORY);
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
