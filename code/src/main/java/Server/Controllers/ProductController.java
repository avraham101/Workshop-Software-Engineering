package Server.Controllers;


import DataAPI.Filter;
import DataAPI.ProductData;
import DataAPI.Response;
import DataAPI.ReviewData;
import Service.SingleService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("home/product")
public class ProductController {

    @GetMapping("filter")
    public ResponseEntity<?>  getProductByFilter(@RequestBody Filter filter){
        Response<List<ProductData>> response =  SingleService.getInstance().viewSpasificProducts(filter);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);

    }

    @GetMapping
    public Response<List<ProductData>> getProductInStore(String storeName){
        return SingleService.getInstance().viewProductsInStore(storeName);
    }

    @PostMapping("review")
    public Response<Boolean> postReview (@RequestParam(name="id") int id, @RequestBody ReviewData reviewData){
        return SingleService.getInstance().writeReview(id,reviewData.getStoreName(),reviewData.getStoreName(),reviewData.getContent());
    }
}
