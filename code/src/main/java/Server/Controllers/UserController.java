package Server.Controllers;


import DataAPI.*;
import Domain.Purchase;
import Domain.User;
import Service.ServiceAPI;
import Service.SingleService;
import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller

public class UserController {

    Gson json;

    public UserController() {
        json= new Gson();
    }

    @GetMapping("/index")
    public String get (){
        return "index";
    }



    @GetMapping("home/connect")
    @ResponseBody
    public Integer connect(){
       return SingleService.getInstance().connectToSystem();

    }

    /**
     * use case 2.2 - Register
     */

    @PostMapping("home/register")
    @ResponseBody
    public ResponseEntity<?> register(@RequestBody String userStr){
        UserData user = json.fromJson(userStr,UserData.class);
        Response<Boolean> response= SingleService.getInstance().register(user.getName(),user.getPassword());
        return getResponseEntity(response);
    }

    /**
     * use case 2.3 - Login
     */

    @PostMapping("home/login")
    @ResponseBody
    public ResponseEntity<?> logIn (@RequestParam(name="id") int id ,@RequestBody String userStr){
        UserData user = json.fromJson(userStr,UserData.class);
        Response<Boolean> response =SingleService.getInstance().login(id,user.getName(),user.getPassword());
        return getResponseEntity(response);

    }

    /**
     * use case 3.1 - Logout
     */

    @PostMapping("home/logout")
    @ResponseBody
    public ResponseEntity<?> logOut(@RequestParam(name="id") int id){
        Response<Boolean> response= SingleService.getInstance().logout(id);
        return getResponseEntity(response);
    }

    /**
     * use case 2.8 - buy cart
     */

    @PostMapping("home/buy/{country}")
    @ResponseBody
    //TODO discus the delivery
    public ResponseEntity<?> buyCart(@PathVariable String country,
                                     @RequestParam(name="id") int id, @RequestBody String  paymentDataStr){
        PaymentData paymentData = json.fromJson(paymentDataStr,PaymentData.class);
        Response<Boolean> response= SingleService.getInstance().purchaseCart(id,country,paymentData,paymentData.getAddress());
        return getResponseEntity(response);

    }

    /**
     * use case 3.7 - watch purchase history
     */

    @GetMapping("home/history")
    public ResponseEntity<?> getPurchaseHistory(@RequestParam(name="id") int id){
        Response<List<Purchase>> response= SingleService.getInstance().watchMyPurchaseHistory(id);
        return getResponseEntity(response);
    }

    /**
     * get user permissions for specific store
     * @param id
     * @param store
     * @return
     */

    @GetMapping("home/permissions/{store}")
    public ResponseEntity<?> getPermissionsForStore(@RequestParam(name="id") int id, @PathVariable String store){
        Response<Set<StorePermissionType>> response = SingleService.getInstance().getPermissionsForStore(id,store);
        return getResponseEntity(response);
    }

    @GetMapping("home/managers/{store}")
    public ResponseEntity<?> getManagersAppointedByUser(@RequestParam(name="id") int id, @PathVariable String store){
        Response<List<String>> response =  SingleService.getInstance().getManagersOfStoreUserManaged(id,store);
        return getResponseEntity(response);
    }

    private ResponseEntity<?> getResponseEntity(Response<?> response) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }


}
