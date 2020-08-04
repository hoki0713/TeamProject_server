package com.mobeom.local_currency.recommend;

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

    @GetMapping("/individual")
    public void individual() throws IOException, TasteException {


        DataModel model = new FileDataModel(new File("C:\\Users\\bit\\TeamProject\\TeamProject_server\\src\\main\\resources\\static\\recommend_store.csv"));

        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
        UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.7,
                similarity, model);
        UserBasedRecommender recommender = new GenericUserBasedRecommender(
                model, neighborhood, similarity);
        LogLikelihoodSimilarity similarUser = new LogLikelihoodSimilarity(model);

        List<RecommendedItem> recommendations = recommender.recommend(21036, 10);
        List<String> recommendItemIds = new ArrayList<>();
        for (RecommendedItem recommendation : recommendations) {
            System.out.println(recommendation.getItemID());
            recommendItemIds.add(Long.toString(recommendation.getItemID()));
        }
        System.out.println("배열의 갯수" + recommendItemIds.size());

        recommendService.recommendStore(recommendItemIds);

        System.out.println("-------------");
        System.out.println("????" + similarUser.userSimilarity(21036, 21431));

    }

    @GetMapping("bestStore/{searchWord}")
    public void bestStore(@PathVariable String searchWord){
        System.out.println("베스트가맹점 진입");
        List<Store> stores = recommendService.bestStores(searchWord);

        for(Store bestStore : stores){
            System.out.println(bestStore.getStoreName());
        }

    }
}
