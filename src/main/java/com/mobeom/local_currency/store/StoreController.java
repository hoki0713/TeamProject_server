package com.mobeom.local_currency.store;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/stores")
public class StoreController {

    @Autowired StoreService storeService;

    @GetMapping("/localChart/{localSelect}")
    public void localChart(@PathVariable String localSelect){
        System.out.println("들어옴"+localSelect);
        //String localSelect ="고양";
        storeService.storeLocalsChart(localSelect);
        System.out.println(storeService.storeLocalsChart(localSelect).toString());
    }


    @GetMapping("/storeTypeChart")
    public void storeTypeChart(){
        storeService.storeTypeChart();
    }
}
