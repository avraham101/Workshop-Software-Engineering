package Server.Controllers;

import DataAPI.CartData;
import DataAPI.ProductIdData;
import DataAPI.Response;
import Service.SingleService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController("home/cart")
public class CartController {

    @GetMapping
    public ResponseEntity<?> getCart (@RequestParam(name="id") int id){
        Response <CartData> response= SingleService.getInstance().watchCartDetatils(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteFromCart(@RequestParam(name="id") int id,
                                            @RequestBody ProductIdData product){
        Response <Boolean> response =SingleService.getInstance().deleteFromCart(id,product.getProductName(),product.getStoreName());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> editProductAmount(@RequestParam(name="id") int id,
                                               @RequestBody ProductIdData product){
        Response<Boolean> response=  SingleService.getInstance().editProductInCart(id,product.getProductName(),
                product.getStoreName(),product.getAmount());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }
}
