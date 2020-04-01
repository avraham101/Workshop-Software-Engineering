package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.FilterTestData.CategoryFilterTestData;
import AcceptanceTests.AcceptanceTestDataObjects.FilterTestData.FilterTestData;
import AcceptanceTests.AcceptanceTestDataObjects.FilterTestData.PriceRangeFilterTestData;
import AcceptanceTests.AcceptanceTestDataObjects.ProductTestData;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class FilterProductsInStoresTest extends AcceptanceTests {

    @Test
    public void filterProductsInStoresTestSuccess(){
        FilterTestData priceRangeFilter = new PriceRangeFilterTestData(0,5);
        FilterTestData categoryFilter = new CategoryFilterTestData("Dairy");
        List<FilterTestData> filters = new ArrayList<>(Arrays.asList(priceRangeFilter,categoryFilter));

        List<ProductTestData> filteredProducts = bridge.filterProducts(products,filters);
        List<ProductTestData> expectedFilteredProducts = Arrays.asList(products.get(1),products.get(9));

        boolean isFiltered = new HashSet<>(filteredProducts).equals(new HashSet<>(expectedFilteredProducts));

        assertTrue(isFiltered);
    }
}
