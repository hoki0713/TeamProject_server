package com.mobeom.local_currency.admin;

import com.mobeom.local_currency.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminController {

    @Autowired AdminService adminService;

    @GetMapping("/list/{searchWord}")
    public ResponseEntity<List<User>> getAllList(@PathVariable String searchWord){

        System.out.println("왔다"+searchWord);
        List<User> userList = adminService.getAllList();
      //  System.out.println(userList.toString());
        return ResponseEntity.ok(userList);
    }
   /* @GetMapping("/list")
    public List<User> getAllList(){
        System.out.println("list Test2");
        List<User> userList = adminService.getAllList();
        return userList;
    }*/
}
