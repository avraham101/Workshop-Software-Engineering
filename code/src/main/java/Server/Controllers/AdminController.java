package Server.Controllers;

import DataAPI.OpCode;
import DataAPI.Response;
import DataAPI.UserData;
import Service.SingleService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminController {

    @GetMapping("admin")
    @ResponseBody
    public String initialStart(){
        return "admin";


    }

    @PostMapping("admin")
    @ResponseBody
    public ResponseEntity<?> initialStart(@RequestBody UserData user){
        Boolean state = SingleService.getInstance(user.getName(),user.getPassword()) !=null;
        Response<Boolean> response = new Response<>(state, OpCode.Success);
        //send the response
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);


    }
}
