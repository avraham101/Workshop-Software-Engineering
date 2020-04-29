package Server.Controllers;

import DataAPI.UserData;
import Service.SingleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminController {

    @PostMapping("admin")
    @ResponseBody
    public Boolean initialStart(@RequestBody UserData user){
        return (SingleService.getInstance(user.getName(),user.getPassword()) !=null);


    }
}
