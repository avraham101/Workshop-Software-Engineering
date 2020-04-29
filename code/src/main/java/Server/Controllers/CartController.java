package Server.Controllers;

import DataAPI.CartData;
import DataAPI.ProductIdData;
import DataAPI.Response;
import Service.SingleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController("home/cart")
public class CartController {

    @GetMapping
    public Response<CartData> getCart (@RequestParam(name="id") int id){
        return SingleService.getInstance().watchCartDetatils(id);
    }

    @DeleteMapping
    public Response<Boolean> deleteFromCart(@RequestParam(name="id") int id,
                                            @RequestBody ProductIdData product){
        return SingleService.getInstance().deleteFromCart(id,product.getProductName(),product.getStoreName());
    }

    @PutMapping
    public Response<Boolean> editProductAmount(@RequestParam(name="id") int id,
                                               @RequestBody ProductIdData product){
        return  SingleService.getInstance().editProductInCart(id,product.getProductName(),
                product.getStoreName(),product.getAmount());
    }
}
