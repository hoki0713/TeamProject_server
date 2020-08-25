package com.mobeom.local_currency.store;

import com.mobeom.local_currency.admin.Industry;
import com.mobeom.local_currency.join.IndustryStore;
import com.mobeom.local_currency.proxy.Box;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
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
    @Autowired
    Box box;


    @GetMapping("/fromAddr/{lat}/{lng}")
    public Map<?, ?> getStoreByAddr(@PathVariable String lat, @PathVariable String lng) {
        box.clear();
        box.put("list", storeService.getStores(lat, lng));
        return box.get();
    }


    @GetMapping("/realTimeSearch/{searchWD}")
    public Map<?, ?> realTimeSearch(@PathVariable String searchWD) {
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

    @GetMapping("/getSome/{stateName}/{category}/{pageNow}/{limitSize}")
    public Map<?, ?> getStoreList(@PathVariable(name = "stateName") String stateName,
                                  @PathVariable(name = "category") String category,
                                  @PathVariable(name = "pageNow") int pageNow,
                                  @PathVariable(name = "limitSize") int limitSize) {
        box.clear();
        Object results = storeService.getSome(stateName, category, pageNow, limitSize);
        if (results != null) {
            box.put("msg", "success");
            box.put("list", results);
            box.put("count", storeService.count());
        } else {
            box.put("msg", "fail");
        }

        return box.get();
    }


    @GetMapping("/findStore/{storeName}")
    public ResponseEntity<Map<Long, SearchStoreVO>> findStoreByName(@PathVariable String storeName) {
        Map<Long, SearchStoreVO> resultStores = new HashMap<>();
        Optional<List<Store>> storeList = storeService.findAllStoreByName(storeName);
        if (storeList.isPresent()) {
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

    @GetMapping("/searchStore/{searchWord}")
    public ResponseEntity<List<Store>> searchStore(@PathVariable String searchWord) {
        List<Store> storeList = storeService.findStoreBySearchWord(searchWord);
        return ResponseEntity.ok(storeList);
    }

    @GetMapping("/chatbotSearch/{searchWord}/{pageSize}")
    public ResponseEntity<Map<?,?>> chatbotSearch(@PathVariable String searchWord, @PathVariable int pageSize){
        box.clear();
        box.put("list",storeService.getChatbotSearch(searchWord,pageSize));
        box.put("count",storeService.getChatbotSearchCount(searchWord));
        return ResponseEntity.ok(box.get());
    }

    @GetMapping("/chatbotRecoMain/{lat}/{lng}")
    public ResponseEntity<List<IndustryStore>> chatbotRecoMain(@PathVariable String lat, @PathVariable String lng){
        return ResponseEntity.ok(storeService.getChatbotRecoMain(lat,lng));
    }
    @GetMapping("/chatbotRank/{stateName}")
    public ResponseEntity<List<IndustryStore>> chatbotRank(@PathVariable String stateName){
        return ResponseEntity.ok(storeService.getChatbotRank(stateName));
    }

    @GetMapping("/chatbotStarRank/{stateName}")
    public ResponseEntity<List<IndustryStore>> chatbotStarRank(@PathVariable String stateName){
        return ResponseEntity.ok(storeService.getChatbotStarRank(stateName));
    }
}
