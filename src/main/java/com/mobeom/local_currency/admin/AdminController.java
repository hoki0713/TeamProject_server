package com.mobeom.local_currency.admin;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admins")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminController {
}
