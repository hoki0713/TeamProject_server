package com.mobeom.local_currency.store;

import com.google.gson.JsonObject;
import com.mobeom.local_currency.proxy.Box;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/stores")
public class StoreController {
    static Logger logger = LoggerFactory.getLogger(StoreController.class);

    @Autowired StoreRepository storeRepository;
    @Autowired Box box;

    @GetMapping("/list")
    public Map<?,?> getAll(){
        logger.info("list()");
        List<Store> storeList = storeRepository.findAll();
        System.out.println(storeList.get(0));
        box.put("list", storeList);
        return box.get();
    }
}
