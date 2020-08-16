package com.mobeom.local_currency.recommend;

import com.mobeom.local_currency.join.IndustryStore;
import com.mobeom.local_currency.proxy.Box;
import com.mobeom.local_currency.store.LatLngVo;
import com.mobeom.local_currency.store.UserLatLngVo;
import lombok.AllArgsConstructor;
import org.apache.mahout.cf.taste.common.TasteException;
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
    public Map<String, List<IndustryStore>> getIndividualRecommend(@PathVariable String id) throws TasteException {
        box.clear();
        box.put("bestStore", recommendService.findBestStores(id));
        box.put("userBased", recommendService.findRecommendStores(recommendService.findUserBasedRecommend(id)));
//        box.put("itemBased", recommendService.recommendStore(recommendService.itemBasedRecommend(id)));
        return box.get();

    }

    @GetMapping("/tag/{gender}/{birthYear}")
    public Map<String, ?> findIndustryByProps(@PathVariable String gender, @PathVariable int birthYear) {
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
        box.put("byTotal", recommendService.findIndustryByTotal());
        box.put("byGenderAge", recommendService.findIndustryByGenderAndAge(gender, ageGroup));
        box.put("byGender", recommendService.findIndustryByGender(gender));
        box.put("byAge", recommendService.findIndustryByAge(ageGroup));
        box.put("userAgeGroup", String.valueOf(ageGroup) + "대");
        box.put("userGenderKor", userGender);
        return box.get();
    }

    @GetMapping("/search/{gender}/{ageGroup}")
    public Map<String, List<GenderAge>> findIndustryByTag(@PathVariable String gender, @PathVariable int ageGroup) {
        System.out.println(gender + ageGroup);
        if (gender.equals("null") && ageGroup == 0) {
            box.clear();
            System.out.println("몇개냐" + recommendService.findIndustryByTotal().size());
            box.put("searchResult", recommendService.findIndustryByTotal());
        } else if (gender.equals("null")) {
            box.clear();
            System.out.println("나이 몇개냐" + recommendService.findIndustryByAge(ageGroup).size());
            box.put("searchResult", recommendService.findIndustryByAge(ageGroup));
        } else if (ageGroup == 0) {
            box.clear();
            System.out.println("성별 몇개냐" + recommendService.findIndustryByGender(gender).size());
            box.put("searchResult", recommendService.findIndustryByGender(gender));
        } else {
            box.clear();
            System.out.println("답있음" + recommendService.findIndustryByGenderAndAge(gender, ageGroup).size());
            box.put("searchResult", recommendService.findIndustryByGenderAndAge(gender, ageGroup));
        }
        return box.get();
    }

    @PostMapping("/storesByIndustry/{gender}/{ageGroup}")
    public Map<String, List<IndustryStore>> findStoresByIndustry(@PathVariable String gender, @PathVariable int ageGroup,
                                                                 @RequestBody LatLngVo latLng){
        System.out.println("가게 리스트 입성");
        List<GenderAge> industryList=findIndustryByTag(gender, ageGroup).get("searchResult");
        return recommendService.findStoresByIndustryList(industryList, latLng);
    }}


//    @GetMapping("/resultStore/{gender}/{age}/{town}")
//    public void resultStores(@PathVariable String gender, @PathVariable int age, @PathVariable String town) {
//        List<List<IndustryStore>> industryList = recommendService.fetchStores(gender, age, town);
//        for (List<IndustryStore> storeList : industryList) {
//            for (IndustryStore store : storeList) {
//                System.out.println(store.getStoreName() + store.getAddress());
//            }
//        }
//    }



