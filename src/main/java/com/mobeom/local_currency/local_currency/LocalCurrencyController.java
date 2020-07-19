package com.mobeom.local_currency.local_currency;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@CrossOrigin(origins = "*", maxAge = 3306)
@RestController
@RequestMapping("/")
public class LocalCurrencyController {
    @Autowired
    LocalCurrencyRepository localCurrencyRepository;

    @Autowired
    EntityManager manager;

    @GetMapping("/")
    public String test(){
        JsonObject obj = new JsonObject();
        obj.addProperty("title", "test1");
        obj.addProperty("content", "test 내용");

        JsonObject data = new JsonObject();

        data.addProperty("time", "12:00");
        obj.add("data", data);
        return obj.toString();
    }
}
