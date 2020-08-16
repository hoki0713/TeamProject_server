package com.mobeom.local_currency.store;

import com.mobeom.local_currency.proxy.Box;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/stores")
@AllArgsConstructor
public class StoreController {
    private final StoreService storeService;
    static Logger logger = LoggerFactory.getLogger(StoreController.class);

    @Autowired Box box;

    @GetMapping("/list")
    public Map<?,?> getAll(){
        logger.info("list()");
        Iterable<Store> storeList = storeService.findAll();
        box.put("list", storeList);
        return box.get();
    }

    @GetMapping("/ui")
    public Map<?,?> getuiList(){
        logger.info("getuiList()");
        box.put("list",storeService.getUi());
        return box.get();
    }

    @GetMapping("/mapClick/{clickedState}")
    public Map<?,?> getMapClick(@PathVariable String clickedState){
        logger.info("getMapClick()");
        box.put("list",storeService.getMap(clickedState));
        System.out.println(box.get());
        return box.get();
    }

    @GetMapping("/findStore/{storeName}")
    public ResponseEntity<Map<Long, SearchStoreVO>> findStoreByName(@PathVariable String storeName) {
        System.out.println(storeName);
        Map<Long, SearchStoreVO> resultStores = new HashMap<>();
        Optional<List<Store>> storeList = storeService.findAllStoreByName(storeName);
        if(storeList.isPresent()) {
            storeList.get().forEach(store -> {
                SearchStoreVO storeInfo = new SearchStoreVO();
                storeInfo.setStoreName(store.getStoreName());
                storeInfo.setStoreLocal(store.getLocalName());
                storeInfo.setStoreAddr(store.getAddress());
                resultStores.put(store.getId(), storeInfo);
            });
            return ResponseEntity.ok(resultStores);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getStores/{localName}")
    public ResponseEntity<Map<Long,StoresVO>> getAllStoresByLocalName (@PathVariable String localName) {
        Map<Long,StoresVO> storesMap = storeService.getAllStoresByLocalName(localName.trim());
        return ResponseEntity.ok(storesMap);
    }

}
