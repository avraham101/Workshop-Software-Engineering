package Server.Controllers;


import DataAPI.Filter;
import DataAPI.ProductData;
import DataAPI.Response;
import DataAPI.ReviewData;
import Service.SingleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("home/product")
public class ProductController {

    @GetMapping("filter")
    public Response<List<ProductData>>  getProductByFilter(@RequestBody Filter filter){
        return SingleService.getInstance().viewSpasificProducts(filter);

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
