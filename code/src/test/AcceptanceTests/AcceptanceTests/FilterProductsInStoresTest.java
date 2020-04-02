package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.FilterTestData.CategoryFilterTestData;
import AcceptanceTests.AcceptanceTestDataObjects.FilterTestData.FilterTestData;
import AcceptanceTests.AcceptanceTestDataObjects.FilterTestData.PriceRangeFilterTestData;
import AcceptanceTests.AcceptanceTestDataObjects.FilterTestData.ProductNameFilterTestData;
import AcceptanceTests.AcceptanceTestDataObjects.ProductTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class FilterProductsInStoresTest extends AcceptanceTests {

    @Before
    public void setUp(){
        super.setUp();
        bridge.addStores(stores);
        bridge.addProducts(products);
    }

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

    @Test
    public void filterProductsInStoresTestSuccessWrongFilters(){
        FilterTestData priceRangeFilter = new PriceRangeFilterTestData(0,Integer.MAX_VALUE + 1);
        FilterTestData categoryFilter = new CategoryFilterTestData("Sodas");
        List<FilterTestData> filters = new ArrayList<>(Arrays.asList(priceRangeFilter,categoryFilter));

        List<ProductTestData> filteredProducts = bridge.filterProducts(products,filters);
        List<ProductTestData> expectedFilteredProducts = Arrays.asList(products.get(3),products.get(4));

        boolean isFiltered = new HashSet<>(filteredProducts).equals(new HashSet<>(expectedFilteredProducts));
        assertTrue(isFiltered);
    }

    @Test
    public void filterProductsInStoresTestSuccessEmptyFilters(){
        List<FilterTestData> filters = new ArrayList<>();
        List<ProductTestData> filteredProducts = bridge.filterProducts(products,filters);
        List<ProductTestData> expectedFilteredProducts = products;

        boolean isFiltered = new HashSet<>(filteredProducts).equals(new HashSet<>(expectedFilteredProducts));
        assertTrue(isFiltered);

        filteredProducts = bridge.filterProducts(products,null);

        isFiltered = new HashSet<>(filteredProducts).equals(new HashSet<>(expectedFilteredProducts));
        assertTrue(isFiltered);

    }

    @Test
    public void filterProductsInStoresTestFailWrongFilters(){
        FilterTestData priceRangeFilter = new PriceRangeFilterTestData(Integer.MIN_VALUE,Integer.MAX_VALUE);
        FilterTestData categoryFilter = new CategoryFilterTestData("notExistingCategory");
        FilterTestData productNameFilter = new ProductNameFilterTestData("notExistingProduct");
        List<FilterTestData> filters = new ArrayList<>(Arrays.asList(priceRangeFilter,categoryFilter,productNameFilter));

        List<ProductTestData> filteredProducts = bridge.filterProducts(products,filters);
        assertEquals(filteredProducts.size(),0);
    }

    @After
    public void tearDown(){
        bridge.deleteProducts(products);
        bridge.deleteStores(stores);
    }
}
