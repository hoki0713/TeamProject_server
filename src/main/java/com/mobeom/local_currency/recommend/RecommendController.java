package com.mobeom.local_currency.recommend;

import com.mobeom.local_currency.join.IndustryStore;
import com.mobeom.local_currency.proxy.Box;
import com.mobeom.local_currency.store.Store;
import lombok.AllArgsConstructor;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/recommends")
@AllArgsConstructor
public class RecommendController {
private final RecommendService recommendService;
private final Box box;

    @GetMapping("/individual/{id}")
    public Map<String,List<Industry>> individual(@PathVariable String id) throws TasteException {
        box.put("bestStore", recommendService.selectBestStores(id));
        box.put("userBased", recommendService.recommendStore(recommendService.userBasedRecommend(id)));
        box.put("itemBased", recommendService.recommendStore(recommendService.itemBasedRecommend(id)));
       return box.get();

    }

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
