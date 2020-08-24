package com.mobeom.local_currency.recommend;

import com.mobeom.local_currency.join.IndustryStore;
import com.mobeom.local_currency.proxy.Box;
import com.mobeom.local_currency.store.LatLng;
import lombok.AllArgsConstructor;
import org.apache.mahout.cf.taste.common.TasteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/userBased/{id}")
    public ResponseEntity<Map<String, ?>> getUserBasedRecommend(@PathVariable String id) throws TasteException {
        if (recommendService.findUserByUserIdInRating(id)) {
            if (recommendService.findUserBasedRecommend(id).size() != 0) {
                box.put("userBased", recommendService.findRecommendedStores(recommendService.findUserBasedRecommend(id)));
            } else {
                box.put("noUserBased", "별점 데이터가 부족합니다. 더 많은 가맹점들을 리뷰해주세요.");
            }
            ;
        } else {
            box.put("noUserBased", "별점 데이터가 부족합니다. 더 많은 가맹점들을 리뷰해주세요.");
        }

        return ResponseEntity.ok(box.get());
    }

    @GetMapping("/itemBased/{id}")
    public Map<String, ?> getItemBasedRecommend(@PathVariable String id) throws TasteException {
        if (recommendService.findUserByUserIdInRating(id)) {
            if (recommendService.findItemBasedRecommend(id).size() != 0) {
                box.put("itemBased", recommendService.findRecommendedStores(recommendService.findItemBasedRecommend(id)));
                box.put("itemBasedStore", recommendService.findOneRatedStore(id).getStoreName());
            } else {
                box.put("noItemBased", "별점 데이터가 부족합니다. 더 많은 가맹점들을 리뷰해주세요.");
            }
        } else {
            box.put("noItemBased", "별점 데이터가 부족합니다. 더 많은 가맹점들을 리뷰해주세요.");
        }
        return box.get();
    }


    @PostMapping("/all/{id}")
    public Map<String, ?> getAllRecommend(@PathVariable String id, @RequestBody LatLng latLng) {
        double lat = latLng.getLat();
        double lng = latLng.getLng();
        box.put("restaurant", recommendService.findStoresByIndustry("일반휴게음식", lat, lng));
        box.put("drinks", recommendService.findStoresByIndustry("음료식품", lat, lng));
        box.put("hospital", recommendService.findStoresByIndustry("의원", lat, lng));

        box.put("bestStore", recommendService.findBestStores(lat, lng));

        box.put("mostFavorites", recommendService.findFavoriteStores(lat, lng));
        box.put("bestRated", recommendService.findBestRatedStores(lat, lng));
        if (recommendService.findOneFavStore(id) != null) {
            String favoriteIndustry = recommendService.findOneFavStore(id).getMainCode();
            box.put("userFavBased", recommendService.findStoresByIndustry(favoriteIndustry, lat, lng));
            box.put("userFavStore", recommendService.findOneFavStore(id).getStoreName());
        } else {
            box.put("noFavorite", "즐겨찾기 데이터가 없습니다. 즐겨찾는 가맹점을 등록해보세요.");
        }
        return box.get();
    }

    @GetMapping("/user/{gender}/{birthYear}")
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
        box.put("userAgeGroup", ageGroup);
        box.put("userGenderKor", userGender);
        return box.get();
    }

    @GetMapping("/rank/{gender}/{ageGroup}")
    public Map<String, List<Consume>> findIndustryByTag(@PathVariable String gender, @PathVariable int ageGroup) {
        box.clear();
        if (gender.equals("none") && ageGroup == 100) {
            box.put("searchResult", recommendService.findIndustryByTotal());
        } else if (gender.equals("none")) {
            box.put("searchResult", recommendService.findIndustryByAge(ageGroup));
        } else if (ageGroup == 100) {
            box.put("searchResult", recommendService.findIndustryByGender(gender));
        } else {
            box.put("searchResult", recommendService.findIndustryByGenderAndAge(gender, ageGroup));
        }
        return box.get();
    }

    @PostMapping("/result/{gender}/{ageGroup}/{option}")
    public Map<String, List<IndustryStore>> findStoresByIndustry(@PathVariable String gender, @PathVariable int ageGroup,
                                                                 @PathVariable int option,
                                                                 @RequestBody LatLng latLng) {
        double lat = latLng.getLat();
        double lng = latLng.getLng();
        List<Consume> industryList = findIndustryByTag(gender, ageGroup).get("searchResult");
        if (option == 1) return recommendService.findStoresByIndustryList(industryList, lat, lng);
        else if (option == 2) return recommendService.findFavStoresByIndustryList(industryList, lat, lng);
        else {
            return recommendService.findBestRatedStoresByIndustryList(industryList, lat, lng);
        }

    }
}
