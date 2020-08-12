package com.mobeom.local_currency.recommend;

import com.mobeom.local_currency.join.IndustryStore;
import com.mobeom.local_currency.store.Store;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/recommends")
public class RecommendController {
    @Autowired
    RecommendService recommendService;

    @GetMapping("/individual/{id}")
    public List<IndustryStore> individual(@PathVariable String id) throws TasteException {
       return recommendService.recommendStore(recommendService.userBasedRecommend(id));

    }

    @GetMapping("/item/{itemId}")
    public List<IndustryStore> item(@PathVariable String itemId) throws TasteException {
        return recommendService.recommendStore(recommendService.itemBasedRecommend(itemId));

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

    @GetMapping("/resultStore/{gender}/{age}/{town}")
    public void resultStores(@PathVariable String gender, @PathVariable int age, @PathVariable String town) {
        List<List<IndustryStore>> industryList = recommendService.fetchStores(gender, age, town);
        for(List<IndustryStore> storeList : industryList ){
            for(IndustryStore store : storeList){
                System.out.println(store.getStoreName()+store.getAddress());
            }
        }


    }
    @GetMapping("/stores/{searchWord}/{town}")
    public void selectStoreByIndustry(@PathVariable String searchWord, @PathVariable String town) {
        System.out.println("업종으로 가게 검색");
        List<IndustryStore> storeList = recommendService.fetchStoreByIndustry(searchWord, town);
        System.out.println(storeList.size());
        for (IndustryStore store : storeList) {
            System.out.println(store.getStoreName() + store.getImgUrl());
        }
    }
}
