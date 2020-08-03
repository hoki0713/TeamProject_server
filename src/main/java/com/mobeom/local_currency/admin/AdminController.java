package com.mobeom.local_currency.admin;

import com.mobeom.local_currency.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admins")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminController {

    @Autowired AdminService adminService;

    @GetMapping("/list/{searchWord}")
    public ResponseEntity<List<User>> getAllList(@PathVariable String searchWord){

        System.out.println("왔다"+searchWord);
        List<User> userList = adminService.getAllList(searchWord);
         System.out.println(adminService.getAllList(searchWord).toString());
        return ResponseEntity.ok(userList);
    }
    
    @GetMapping("/chart/ratio-of-user-region")
    public ResponseEntity<Map<String,Long>> ratioOfUserRegion(){
        System.out.println("실행");
        Map<String,Long> userResion = adminService.chart();
        System.out.println(adminService.chart());
        return ResponseEntity.ok(userResion);
    }

    @GetMapping("/userTotal-chart/{localSelect}")
    public ResponseEntity<Map<String,Long>> chart(@PathVariable String localSelect){
        System.out.println("들어왓음"+localSelect);
        Map<String,Long> userLocal = adminService.userLocalTotal(localSelect);

        System.out.println(adminService.userLocalTotal(localSelect).toString());

        return ResponseEntity.ok(userLocal);
    }

    @GetMapping("/userAge-chart/{localSelect}")
    public ResponseEntity<Map<String,Integer>> chart2(@PathVariable String localSelect){
        Map<String,Integer> userAge = adminService.userAgeTotal(localSelect);
        System.out.println(adminService.userAgeTotal(localSelect).toString());
        return ResponseEntity.ok(userAge);
    }


}
