package Server.Controllers;

import DataAPI.UserData;
import Service.SingleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @PostMapping
    @ResponseBody
    public Boolean initialStart(@RequestBody UserData user){
        System.out.println(user);
        return (SingleService.getInstance(user.getName(),user.getPassword()) !=null);
    }
}
