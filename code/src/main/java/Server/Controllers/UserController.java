package Server.Controllers;


import DataAPI.*;
import DataAPI.Purchase;
import Service.SingleService;
import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class UserController {

    Gson json;
    AtomicInteger counter = new AtomicInteger(0);

    public UserController() {
        json= new Gson();
    }

    @GetMapping("/index")
    public String get (){
        return "index";
    }



    @GetMapping("home/connect")
    @ResponseBody
    public ResponseEntity<?> connect(){
        Integer id = SingleService.getInstance().connectToSystem();
        Response<Integer> response = new Response<>(id,OpCode.Success);
        return getResponseEntity(response);
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

    @PostMapping("home/buy")
    @ResponseBody
    public ResponseEntity<?> buyCart(@RequestParam(name="id") int id, @RequestBody String  paymentDataStr){
        PaymentData paymentData = json.fromJson(paymentDataStr,PaymentData.class);
        Response<Boolean> response= SingleService.getInstance().purchaseCart(id,paymentData.getCountry(),paymentData,
                paymentData.getAddress(),paymentData.getCity(),paymentData.getZip());
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

    @PostMapping("ack")
    public ResponseEntity<?> deleteNotifications(@RequestParam(name="id") int id,@RequestBody String notificationsList){
        List<Double> listOfNotification= json.fromJson(notificationsList,List.class);
        List<Integer> list = new LinkedList<>();
        for(double d:listOfNotification)
            list.add((int)d);//
        SingleService.getInstance().deleteRecivedNotifications(id,list);
        return getResponseEntity(new Response<>("", OpCode.Success));
    }

    private ResponseEntity<?> getResponseEntity(Response<?> response) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }


}
