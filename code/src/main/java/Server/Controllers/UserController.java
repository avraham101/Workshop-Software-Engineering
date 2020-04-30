package Server.Controllers;


import DataAPI.*;
import Domain.Purchase;
import Service.ServiceAPI;
import Service.SingleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    /*private ServiceAPI serviceAPI;

    public UserController() {
        this.serviceAPI = SingleService.getInstance();
    }*/

    @GetMapping("/index")
    public String get (){
        return "index";
    }

    @GetMapping("home/connect")
    @ResponseBody
    public Integer connect(){

       return SingleService.getInstance().connectToSystem();
    }

    @PostMapping("home/register")
    @ResponseBody
    public Response<Boolean> register(@RequestBody UserData user){
        return SingleService.getInstance().register(user.getName(),user.getPassword());
    }

    @PostMapping("home/login")
    @ResponseBody
    public Response<Boolean> logIn (@RequestParam(name="id") int id ,@RequestBody UserData user){
       return SingleService.getInstance().login(id,user.getName(),user.getPassword());

    }

    @PostMapping("home/logout")
    @ResponseBody
    public Response<Boolean> logOut(@RequestParam(name="id") int id){
        return SingleService.getInstance().logout(id);
    }

    @PostMapping("home/buy/{country}")
    @ResponseBody
    //TODO discus the delivery
    public Response<Boolean> buyCart(@PathVariable String country,
                                     @RequestParam(name="id") int id, @RequestBody PaymentData paymentData){
        return SingleService.getInstance().purchaseCart(id,country,paymentData,paymentData.getAddress());

    }

    @GetMapping("home/history")
    public Response<List<Purchase>> getPurchaseHistory(@RequestParam(name="id") int id){
        return SingleService.getInstance().watchMyPurchaseHistory(id);
    }


}
