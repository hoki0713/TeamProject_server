package com.mobeom.local_currency.admin;

import com.mobeom.local_currency.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admins")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminController {

    @Autowired AdminService adminService;

    @GetMapping("/list")
    public ResponseEntity<List<User>> getAllList(){
        System.out.println("왔다");
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
