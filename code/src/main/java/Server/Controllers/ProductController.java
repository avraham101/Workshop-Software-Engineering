//package Server.Controllers;
//
//
//import DataAPI.*;
//import Domain.PurchaseType;
//import Service.SingleService;
//import com.google.gson.Gson;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("home/product")
//public class ProductController {
//    Gson json ;
//
//    public ProductController() {
//        json = new Gson();
//    }
//
//
//    /**
//     * use case 2.5 - Search product in store
//     */
//    @PostMapping("filter")
//    public ResponseEntity<?>  getProductByFilter(@RequestBody String filterStr){
//        System.out.println(filterStr);
//        Filter filter = json.fromJson(filterStr,Filter.class);
//        Response<List<ProductData>> response =  SingleService.getInstance().viewSpasificProducts(filter);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
//        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
//
//    }
//
//    /**
//     * use case 2.4.2 - show the products of a given store
//     */
//
//    @GetMapping
//    public ResponseEntity<?> getProductInStore(@RequestParam(name="store") String storeName){
//        Response<List<ProductData>> response=SingleService.getInstance().viewProductsInStore(storeName);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
//        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
//    }
//
//    /**
//     * use case 3.3 - write review
//     */
//    @PostMapping("review")
//    public ResponseEntity<?> postReview (@RequestParam(name="id") int id, @RequestBody String reviewDataStr){
//        System.out.println("post");
//        ReviewData reviewData = json.fromJson(reviewDataStr,ReviewData.class);
//        Response<Boolean> response =  SingleService.getInstance().writeReview(id,reviewData.getStoreName(),reviewData.getProductName(),reviewData.getContent());
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
//        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
//    }
//
//    /**
//     * use case 4.1.1
//     */
//    @PostMapping()
//    public ResponseEntity<?> addProduct(@RequestParam(name="id") int id, @RequestBody String productStr){
//        System.out.println("add");
//        ProductData product= json.fromJson(productStr,ProductData.class);
//        Response<Boolean> response = SingleService.getInstance().addProductToStore(id,product);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
//        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
//    }
//
//    /**
//     * use case 4.1.2
//     */
//
//    @PostMapping("delete")
//    public ResponseEntity<?> deleteProduct(@RequestParam(name="id") int id, @RequestBody String productStr){
//        System.out.println("delete");
//        ProductData product= json.fromJson(productStr,ProductData.class);
//        Response<Boolean> response= SingleService.getInstance().removeProductFromStore(id,product.getStoreName(),product.getProductName());
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
//        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
//    }
//
//    /**
//     * use case 4.1.3
//     */
//
//    @PostMapping("edit")
//    public ResponseEntity<?> editProduct(@RequestParam(name="id") int id, @RequestBody String productStr){
//        System.out.println("edit");
//        ProductData product= json.fromJson(productStr,ProductData.class);
//        Response<Boolean> response= SingleService.getInstance().editProductFromStore(id,product);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
//        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
//    }
//
//
//
//}
