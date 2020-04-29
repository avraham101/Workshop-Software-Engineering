package Server.Controllers;


import DataAPI.Filter;
import DataAPI.ProductData;
import DataAPI.Response;
import Service.SingleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("home/product")
public class ProductController {

    @GetMapping("filter")
    public Response<List<ProductData>>  getProductByFilter(@RequestBody Filter filter){
        return SingleService.getInstance().viewSpasificProducts(filter);

    }

    //productInSTORE
   // @GetMapping
   // public Response<ProductData> getProductInStore()
}
