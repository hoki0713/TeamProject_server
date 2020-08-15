package com.mobeom.local_currency.recommend;

import com.mobeom.local_currency.join.IndustryStore;
import com.mobeom.local_currency.proxy.Box;
import com.mobeom.local_currency.store.Store;
import lombok.AllArgsConstructor;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
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
    public Map<String, List<IndustryStore>> individual(@PathVariable String id) throws TasteException {
        box.clear();
        box.put("bestStore", recommendService.selectBestStores(id));
        box.put("userBased", recommendService.recommendStore(recommendService.userBasedRecommend(id)));
//        box.put("itemBased", recommendService.recommendStore(recommendService.itemBasedRecommend(id)));
        return box.get();

    }

    @GetMapping("/tag/{gender}/{birthYear}")
    public Map<String, ?> IndustryByTag(@PathVariable String gender, @PathVariable int birthYear) {
        Calendar cal = Calendar.getInstance();
        int thisYear = cal.get(Calendar.YEAR);
        int ageGroup = ((thisYear - birthYear + 1) / 10) * 10;
        if (ageGroup <= 10) {
            ageGroup = 10;
        }
        String userGender = "";
        if (gender.equals("M")) {
            userGender = "남성";
        } else if (gender.equals("F")) {
            userGender = "여성";
        }
        box.clear();
        box.put("byTotal", recommendService.industryByTotal());
        box.put("byGenderAge", recommendService.industryByGenderAndAge(gender, ageGroup));
        box.put("byGender", recommendService.industryByGender(gender));
        box.put("byAge", recommendService.industryByAge(ageGroup));
        box.put("userAgeGroup", String.valueOf(ageGroup) + "대");
        box.put("userGenderKor", userGender);
        return box.get();
    }

    @GetMapping("/search/{gender}/{ageGroup}")
    public Map<String, List<GenderAge>> IndustryBySearch(@PathVariable String gender, @PathVariable int ageGroup) {
        System.out.println(gender + ageGroup);
        if (gender.equals("null") && ageGroup == 0) {
            box.clear();
            System.out.println("몇개냐" + recommendService.industryByTotal().size());
            box.put("searchResult", recommendService.industryByTotal());
        } else if (gender.equals("null")) {
            box.clear();
            System.out.println("나이 몇개냐" + recommendService.industryByTotal().size());
            box.put("searchResult", recommendService.industryByAge(ageGroup));
        } else if (ageGroup == 0) {
            box.clear();
            System.out.println("성별 몇개냐" + recommendService.industryByTotal().size());
            box.put("searchResult", recommendService.industryByGender(gender));
        } else {
            box.clear();
            System.out.println("답있음" + recommendService.industryByTotal().size());
            box.put("searchResult", recommendService.industryByGenderAndAge(gender, ageGroup));
        }
        return box.get();
    }


    @GetMapping("/industry/{searchWord}/{age}")
    public void industryByGenderAndAge(@PathVariable String searchWord, @PathVariable int age) {
        List<GenderAge> list;
        if (searchWord.equals("")) {
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

//    @GetMapping("/resultStore/{gender}/{age}/{town}")
//    public void resultStores(@PathVariable String gender, @PathVariable int age, @PathVariable String town) {
//        List<List<IndustryStore>> industryList = recommendService.fetchStores(gender, age, town);
//        for (List<IndustryStore> storeList : industryList) {
//            for (IndustryStore store : storeList) {
//                System.out.println(store.getStoreName() + store.getAddress());
//            }
//        }
//    }

    @GetMapping("/stores/{searchWord}/{town}")
    public void selectStoreByIndustry(@PathVariable String searchWord, @PathVariable String town) {
        System.out.println("업종으로 가게 검색");
        List<IndustryStore> storeList = recommendService.fetchStoreByIndustry(searchWord);
        System.out.println(storeList.size());
        for (IndustryStore store : storeList) {
            System.out.println(store.getStoreName() + store.getImgUrl());
        }
    }

    @GetMapping("/storesByIndustry/{gender}/{ageGroup}")
    public Map<String, ?> storesByIndustry(@PathVariable String gender, @PathVariable int ageGroup){
        box.clear();
        System.out.println("가게 리스트 입성");
        List<GenderAge> industryList=IndustryBySearch(gender, ageGroup).get("searchResult");
        System.out.println("가게에 몇개 담겼니"+industryList.size());
        Map<String, List<IndustryStore>> result= recommendService.findStoreByIndustryList(industryList);
        return result;
}}
