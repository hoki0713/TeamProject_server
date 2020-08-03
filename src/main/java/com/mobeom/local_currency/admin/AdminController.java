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
    public Map<String,Long> ratioOfUserRegion(){
        System.out.println("실행");

        System.out.println(adminService.chart());
        return adminService.chart();
    }

    @GetMapping("/userTotal-chart/{localSelect}")
    public void chart(@PathVariable String localSelect){
        System.out.println("들어왓음"+localSelect);
        adminService.userTotal(localSelect);
        System.out.println(adminService.userTotal(localSelect).toString());

    }

}
