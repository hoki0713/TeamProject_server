package com.mobeom.local_currency.store;

import com.google.gson.JsonObject;
import com.mobeom.local_currency.proxy.Box;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class StoreController {
    private StoreService storeService;
    static Logger logger = LoggerFactory.getLogger(StoreController.class);

    @Autowired Box box;

    @GetMapping("/list")
    public Map<?,?> getAll(){
        logger.info("list()");
        List<Store> storeList = storeService.getAll();
        System.out.println(storeList.get(0));
        box.put("list", storeList);
        return box.get();
    }

    @GetMapping("/ui")
    public Map<?,?> getuiList(){
        logger.info("getuiList()");
        box.put("list",storeService.getUi());
        return box.get();
    }
}
