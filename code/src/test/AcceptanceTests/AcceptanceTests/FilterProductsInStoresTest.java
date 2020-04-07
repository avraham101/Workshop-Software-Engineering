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
import static org.junit.Assert.*;


public class FilterProductsInStoresTest extends AcceptanceTests {

    @Before
    public void setUp(){
        addStores(stores);
        addProducts(products);
    }

    @Test
    public void filterProductsInStoresTestSuccess(){
        HashSet<ProductTestData> expectedProducts = new HashSet<>(Arrays.asList(products.get(5) , products.get(9)));
        FilterTestData priceRangeFilter = new PriceRangeFilterTestData(0,5);
        FilterTestData categoryFilter = new CategoryFilterTestData("Dairy");
        List<FilterTestData> filters = new ArrayList<>(Arrays.asList(priceRangeFilter,categoryFilter));

        HashSet<ProductTestData> filteredProducts = bridge.filterProducts(products,filters);

        boolean isFiltered = filteredProducts.equals(expectedProducts);
        assertTrue(isFiltered);
    }

    @Test
    public void filterProductsInStoresTestSuccessWrongFilters(){
        HashSet<ProductTestData> expectedProducts = new HashSet<>(Arrays.asList(products.get(4),products.get(3)));
        FilterTestData priceRangeFilter = new PriceRangeFilterTestData(0,Integer.MAX_VALUE + 1);
        FilterTestData categoryFilter = new CategoryFilterTestData("Sodas");
        List<FilterTestData> filters = new ArrayList<>(Arrays.asList(priceRangeFilter,categoryFilter));

        HashSet<ProductTestData> filteredProducts = bridge.filterProducts(products,filters);

        boolean isFiltered = filteredProducts.equals(expectedProducts);
        assertTrue(isFiltered);
    }

    @Test
    public void filterProductsInStoresTestSuccessEmptyFilters(){
        List<FilterTestData> filters = new ArrayList<>();
        HashSet<ProductTestData> filteredProducts = bridge.filterProducts(products,filters);
        HashSet<ProductTestData> expectedFilteredProducts = new HashSet<>(products);

        boolean isFiltered = filteredProducts.equals(expectedFilteredProducts);
        assertTrue(isFiltered);

        filteredProducts = bridge.filterProducts(products,null);
        isFiltered = filteredProducts.equals(expectedFilteredProducts);
        assertTrue(isFiltered);
    }

    @Test
    public void filterProductsInStoresTestFailWrongFilters(){
        FilterTestData priceRangeFilter = new PriceRangeFilterTestData(Integer.MIN_VALUE,Integer.MAX_VALUE);
        FilterTestData categoryFilter = new CategoryFilterTestData("notExistingCategory");
        FilterTestData productNameFilter = new ProductNameFilterTestData("notExistingProduct");
        List<FilterTestData> filters = new ArrayList<>(Arrays.asList(priceRangeFilter,categoryFilter,productNameFilter));

        HashSet<ProductTestData> filteredProducts = bridge.filterProducts(products,filters);
        assertEquals(filteredProducts.size(),0);
    }


    @After
    public void tearDown(){
        deleteProducts(products);
        deleteStores(stores);
    }
}
