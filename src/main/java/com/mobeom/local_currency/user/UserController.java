package com.mobeom.local_currency.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping(value = "/idCheck")
    public ResponseEntity<User> idCheck(@RequestBody User user) {
        Optional<User> idCheckResult = userService.findUser(user.getUserId());
        if(idCheckResult.isPresent()) {
            return ResponseEntity.ok(idCheckResult.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/account-info/{userId}")
    public ResponseEntity<User> getOneInfo(@PathVariable String userId) {
        Optional<User> user = userService.findUser(userId);
        if(user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

}
