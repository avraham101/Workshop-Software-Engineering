package Server.Controllers;

import DataAPI.*;
import Domain.DayVisit;
import Service.SingleService;
import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminController {

    Gson json ;

    public AdminController() {
        json = new Gson();
    }

    @RequestMapping
    public String getAdminPage(){
        return "admin";
    }

    /**
     * use case 1.1
     */

    @PostMapping("init")
    public ResponseEntity<?> initialStart(@RequestBody String string){
        Gson json = new Gson();
        UserData user =  json.fromJson(string,UserData.class);
        Boolean state = SingleService.getInstance(user.getName(),user.getPassword()) !=null;
        Response<Boolean> response = new Response<>(state, OpCode.Success);
        //send the response
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }

    /**
     * use case 6.4.1 - admin watch history purchases of some user
     */
    @GetMapping("userhistory/{user}")
    public ResponseEntity<?> getUserHistory(@RequestParam(name="id") int id ,@PathVariable String user){
        Response<List<Purchase>> response = SingleService.getInstance().AdminWatchUserPurchasesHistory(id,user);
        return getResponseEntity(response);

    }

    /**
     * use case 6.4.2 - admin watch history purchases of some store
     */

    @GetMapping("storehistory/{store}")
    public ResponseEntity<?> getStoreHistory(@RequestParam(name="id") int id ,@PathVariable String store){
        Response<List<Purchase>> response = SingleService.getInstance().AdminWatchStoreHistory(id,store);
        return getResponseEntity(response);
    }

    /**
     * get all the users for the admin
     * @param id - the id of the admin
     * @return - list of all the names of the users
     */
    @GetMapping("allusers")
    public ResponseEntity<?> getAllUsers(@RequestParam(name="id") int id ) {
        Response<List<String>> response = SingleService.getInstance().getAllUsers(id);
        return getResponseEntity(response);
    }


    /**
     * use case 6.5
     * get the daily visits from a period of time
     * @param id - the id of the admin
     * @param datesData - the dates from and to in string
     * @return - list of DayVisit
     */
    @PostMapping("visits")
    public ResponseEntity<?> getVisits(@RequestParam(name="id") int id,
                                       @RequestBody String datesData) {
        Dates datesToAndFrom=json.fromJson(datesData, Dates.class);
        DateData from = datesToAndFrom.getFromDate();
        DateData to = datesToAndFrom.getToDate();
        Response<List<DayVisit>> response = SingleService.getInstance().watchVisitsBetweenDates(id, from, to);
        return getResponseEntity(response);
    }

        /**
         * use case 6.6.1
         * get the revenue today
         * @param id - the id of the admin
         * @return - the revenue today
         */
    @GetMapping("todayRevenue")
    public ResponseEntity<?> getTodayRevenue(@RequestParam(name="id") int id ) {
        Response<Double> response = SingleService.getInstance().getRevenueToday(id);
        return getResponseEntity(response);
    }

    /**
     * use case 6.6.2
     * get the revenue of specific say
     * @param id - the id of the admin
     * @return - the revenue today
     */
    @PostMapping("daysRevenue")
    public ResponseEntity<?> getRevenueByDay(@RequestParam(name="id") int id,@RequestBody String data) {
        DateData date = json.fromJson(data, DateData.class);
        Response<Double> response = SingleService.getInstance().getRevenueByDay(id, date);
        return getResponseEntity(response);
    }

    private ResponseEntity<?> getResponseEntity(Response<?> response) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }


}
