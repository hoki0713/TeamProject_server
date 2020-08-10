package com.mobeom.local_currency.recommend;

import com.mobeom.local_currency.consume.GenderAge;
import com.mobeom.local_currency.join.IndustryStore;
import com.mobeom.local_currency.proxy.Box;
import com.mobeom.local_currency.store.Store;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/recommends")
public class RecommendController {
    @Autowired
    RecommendService recommendService;

    @GetMapping("/individual/{id}")
    public List<Store> individual(@PathVariable String id) throws IOException, TasteException {
       return recommendService.recommendStore(recommendService.mahout(id));

    }

    @GetMapping("/bestStores/{searchWord}")
    public void bestStore(@PathVariable String searchWord) {
        System.out.println("베스트가맹점 진입");
        List<Store> stores = recommendService.selectBestStores(searchWord);

        for (Store bestStore : stores) {
            System.out.println(bestStore.getStoreName());
        }

    }

//    @GetMapping("/test/{storeName}/{storeType}")
//    public void testRecommend(@PathVariable String storeName, @PathVariable String storeType) {
//        System.out.println("테스트");
//        List<StoreVo> lists = recommendService.testRecommend(storeName, storeType);
//        System.out.println(lists.size());
//        for (StoreVo storeVo : lists) {
//            System.out.println(storeVo.getStoreName());
//        }
//    }



    @GetMapping("/industry/{searchWord}/{age}")
    public void industryByGenderAndAge(@PathVariable String searchWord, @PathVariable int age) {
        List<GenderAge> list;
        if (searchWord.equals("남성")) {
            list = recommendService.industryByGenderAndAge("M", age);
        } else if (searchWord.equals("여성")) {
            list = recommendService.industryByGenderAndAge("F", age);
        } else {
            list = recommendService.industryByGenderAndAge("%", age);
        }
        for (GenderAge genderAge : list) {
            System.out.println(genderAge.getIndustryName());
        }
    }

    @GetMapping("/age/{age}")
    public void rankingByAge(@PathVariable int age) {

        List<GenderAge> list = recommendService.industryByAge(age);
        for (GenderAge genderAge : list) {
            System.out.println(genderAge.getIndustryName());
        }
    }

    @GetMapping("/gender/{gender}")
    public void rankingByGender(@PathVariable String gender) {
        List<GenderAge> list = recommendService.industryByGender(gender);
        for (GenderAge genderAge : list) {
            System.out.println(genderAge.getIndustryName());
        }
    }

    @GetMapping("/resultStore/{gender}/{age}")
    public void resultStores(@PathVariable String gender, @PathVariable int age) {
        Map<String, List<IndustryStore>> storeList = recommendService.fetchStores(gender, age);

        System.out.println(storeList.toString());
        System.out.println("학원입니당"+storeList.get("학원").toString());
        List<IndustryStore> abc = storeList.get("학원");
        for(IndustryStore industryStore :abc) {
            System.out.println("가게이름"+industryStore.getStoreName());
        }

//        for (IndustryStore store : lists) {
//            System.out.println(store.getStoreName() + store.getImgUrl());
//        }


    }
    @GetMapping("/stores/{searchWord}")
    public void selectStoreByIndustry(@PathVariable String searchWord) {
        System.out.println("업종으로 가게 검색");
        List<IndustryStore> storeList = recommendService.fetchStoreByIndustry(searchWord);
        System.out.println(storeList.size());
        for (IndustryStore store : storeList) {
            System.out.println(store.getStoreName() + store.getImgUrl());
        }
    }
}
