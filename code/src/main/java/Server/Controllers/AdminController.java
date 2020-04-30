package Server.Controllers;

import DataAPI.UserData;
import Service.SingleService;
import com.google.gson.Gson;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class AdminController {

    @GetMapping("/admin")
    @ResponseBody
    public String getAdminPage(){
        System.out.println("2");
        return "admin";
    }

    @PostMapping("/admin")
    @ResponseBody
    public Boolean initialStart(@RequestHeader Map<String,String> header, @RequestBody String string){
        Gson json = new Gson();
        UserData user =  json.fromJson(string,UserData.class);
        System.out.println(user.getName());
        System.out.println(user.getPassword());
        return SingleService.getInstance(user.getName(),user.getPassword()) !=null;

    }
}
