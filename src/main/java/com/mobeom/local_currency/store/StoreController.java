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
        box.clear();
        box.put("list", storeService.findAll());
        return box.get();
    }

    @GetMapping("/ui")
    public Map<?,?> getuiList(){
        logger.info("getuiList()");
        box.clear();
        box.put("list",storeService.getUi());
        return box.get();
    }

    @GetMapping("/mapClick/{clickedState}")
    public Map<?,?> getMapClick(@PathVariable String clickedState){
        logger.info("getMapClick()");
        box.clear();
        box.put("list",storeService.getMap(clickedState));
        return box.get();
    }
    @GetMapping("/fromAddr/{lat}/{lng}")
    public Map<?,?> getStoreByAddr(@PathVariable String lat, @PathVariable String lng){
        logger.info("getStoreByAddr()");
        logger.info(lat+lng);
        box.clear();
        box.put("list", storeService.getStores(lat,lng));

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

    @GetMapping("/realTimeSearch/{searchWD}")
    public Map<?,?>  realTimeSearch(@PathVariable String searchWD){
        logger.info("realTimeSearch()  "+"searchWD:"+searchWD);
        Object results = storeService.getSeveral(searchWD);
        box.clear();
        if (results != null) {
            box.put("msg", "success");
            box.put("list", results);
        } else {
            box.put("msg", "fail");
        }
        return box.get();
    }

}
