package Server.Controllers;


import DataAPI.CartData;
import DataAPI.ProductIdData;
import DataAPI.Response;
import DataAPI.UserData;
import Service.ServiceAPI;
import Service.SingleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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


}
