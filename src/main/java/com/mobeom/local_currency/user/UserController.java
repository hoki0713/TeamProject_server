package com.mobeom.local_currency.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/idCheck/{userId}")
    public ResponseEntity<User> idCheck(@PathVariable String userId) {
        Optional<User> idCheckResult = userService.findUserbyUserId(userId);
        if(idCheckResult.isPresent()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/{id}")
    public ResponseEntity<User> checkCurrentPassword(@PathVariable String id, @RequestBody User user) {
        Optional<User> findUser = userService.findUser(Long.valueOf(id));
        if(findUser.isPresent()) {
            User targetUser = findUser.get();
            if(user.getPassword().equals(targetUser.getPassword())) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getOneInfo(@PathVariable String id) {
        Optional<User> user = userService.findUser(Long.valueOf(id));
        if(user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
        Optional<User> findUser = userService.findUser(Long.valueOf(id));
        if(findUser.isPresent()) {
            User selectUser = findUser.get();
            Optional.ofNullable(user.getPassword()).ifPresent(password -> selectUser.setPassword(password));
            Optional.ofNullable(user.getDefaultAddr()).ifPresent(defaultAddress -> selectUser.setDefaultAddr(defaultAddress));
            Optional.ofNullable(user.getOptionalAddr()).ifPresent(optionalAddress -> selectUser.setOptionalAddr(optionalAddress));
            Optional.ofNullable(user.getEmail()).ifPresent(email -> selectUser.setEmail(email));
            return ResponseEntity.ok(userService.update(selectUser));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable String id) {
        Optional<User> findUser = userService.findUser(Long.valueOf(id));
        if(findUser.isPresent()) {
            User selectUser = findUser.get();
            userService.delete(selectUser);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        Optional<User> findUser = userService.findUserbyUserId(user.getUserId());
        if(findUser.isPresent()) {
            User requestLoginUser = findUser.get();
            if(user.getPassword().equals(requestLoginUser.getPassword())) {
                return ResponseEntity.ok(requestLoginUser);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value ="/")
    public ResponseEntity<User> create(@RequestBody User user) {
        Optional<User> createUser = userService.createUser(user);
        if(createUser.isPresent()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/findId")
    public ResponseEntity<User> findId(@RequestParam String name, @RequestParam String email) {
        Optional<User> findUser = userService.findUserbyNameAndEmail(name, email);
        if(findUser.isPresent()) {
            return ResponseEntity.ok(findUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/checkUserForResetPassword")
    public ResponseEntity<User> checkUserForResetPassword(@RequestParam String userId,
                                                          @RequestParam String name, @RequestParam String email) {
        Optional<User> findUser = userService.findUserForResetPassword(userId, name, email);
        if(findUser.isPresent()) {
            return ResponseEntity.ok(findUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
