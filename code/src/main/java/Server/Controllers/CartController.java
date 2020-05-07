package Server.Controllers;

import DataAPI.CartData;
import DataAPI.ProductIdData;
import DataAPI.Response;
import Service.SingleService;
import com.google.gson.Gson;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("home/cart")
public class CartController {

    Gson json ;

    public CartController() {
        json= new Gson();
    }

    /**
     * use case 2.7.1 - watch cart details
     */


    @GetMapping
    public ResponseEntity<?> getCart (@RequestParam(name="id") int id){
        Response <CartData> response = SingleService.getInstance().watchCartDetatils(id);
        return getResponseEntity(response);
    }

    /**
     * use case 2.7.2 - delete product from cart
     */

    @PostMapping("delete")
    public ResponseEntity<?> deleteFromCart(@RequestParam(name="id") int id,
                                            @RequestBody String productStr){
        ProductIdData product = json.fromJson(productStr,ProductIdData.class);
        Response <Boolean> response = SingleService.getInstance().deleteFromCart(id,product.getProductName(),product.getStoreName());
        return getResponseEntity(response);
    }

    /**
     * use case 2.7.3 - edit amount of product
     */
    @PostMapping("edit")
    public ResponseEntity<?> editProductAmount(@RequestParam(name="id") int id,
                                               @RequestBody String  productStr){
        ProductIdData product = json.fromJson(productStr,ProductIdData.class);
        Response<Boolean> response=  SingleService.getInstance().editProductInCart(id,product.getProductName(),
                product.getStoreName(),product.getAmount());
        return getResponseEntity(response);
    }

    @PostMapping
    public ResponseEntity<?> addProductToStore(@RequestParam(name="id") int id,
                                               @RequestBody String productStr){
        ProductIdData product = json.fromJson(productStr,ProductIdData.class);
        Response<Boolean> response = SingleService.getInstance().addProductToCart(id,product.getProductName(),
                                            product.getStoreName(),product.getAmount());
        return getResponseEntity(response);

    }

    private ResponseEntity<?> getResponseEntity(Response<?> response) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }
}
