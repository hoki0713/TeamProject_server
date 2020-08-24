package com.mobeom.local_currency.subsidyPolicy;

import com.mobeom.local_currency.proxy.Box;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/policy")
@AllArgsConstructor
public class SubsidyPolicyController {
    private final SubsidyPolicyService subsidyPolicyService;


    @GetMapping("/chatbotPolicy/{userAge}/{children}")
    public ResponseEntity<Object> chatbotPolicySearch(@PathVariable int userAge, @PathVariable String children) {
        boolean childrenb = false;
        if (children.equals("유자녀")){
            childrenb =true;
        }
        return ResponseEntity.ok(subsidyPolicyService.getFitPolicy(userAge,childrenb));
    }
}
