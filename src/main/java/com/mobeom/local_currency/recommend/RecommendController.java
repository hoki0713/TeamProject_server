package com.mobeom.local_currency.recommend;

import com.mobeom.local_currency.consume.Gender;
import com.mobeom.local_currency.post.Post;
import com.mobeom.local_currency.store.Store;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    public void bestStore(@PathVariable String searchWord){
        System.out.println("베스트가맹점 진입");
        List<Store> stores = recommendService.selectBestStores(searchWord);

        for(Store bestStore : stores){
            System.out.println(bestStore.getStoreName());
        }

    }
    @GetMapping("/test/{storeName}/{storeType}")
    public void testRecommend(@PathVariable String storeName, @PathVariable String storeType){
        System.out.println("테스트");
        List<StoreVo> lists = recommendService.testRecommend(storeName, storeType);
        System.out.println(lists.size());
        for(StoreVo storeVo : lists){
            System.out.println(storeVo.getStoreName());
        }
    }

    @GetMapping("/gender")
    public void gender(){
        System.out.println("성별추천");
        List<Store> genderlists=recommendService.genderRecommend();
        System.out.println(genderlists.size());
        for(Store store : genderlists){
            System.out.println(store.toString());
        }
    }

    @GetMapping("/gender/{searchWord}")
    public void rankingByGender(@PathVariable String searchWord){
        List<Gender> list;
        if(searchWord.equals("남성")){
            searchWord="M";
            list= recommendService.industryByGender(searchWord);
        } else if(searchWord.equals("여성")){
            searchWord="F";
            list=recommendService.industryByGender(searchWord);
        } else

        {
            searchWord="";
            list=  recommendService.industryByGender(searchWord);}
        for(Gender gender : list){
            System.out.println(gender.getIndustryName());
        }
    }
}
