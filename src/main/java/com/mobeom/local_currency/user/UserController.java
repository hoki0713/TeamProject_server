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

    @ResponseBody
    @PostMapping(value = "/idCheck")
    public ResponseEntity<User> idCheck(@RequestBody User user) {
        Optional<User> idCheckResult = userService.idCheck(user.getUserId());
        if(idCheckResult.isPresent()) {
            return ResponseEntity.ok(idCheckResult.get());
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

}
