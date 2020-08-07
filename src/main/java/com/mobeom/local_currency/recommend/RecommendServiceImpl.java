package com.mobeom.local_currency.recommend;

import com.mobeom.local_currency.store.Store;
import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.AbstractDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.impl.model.MySQLJDBCIDMigrator;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
interface RecommendService {
    List<Store> recommendStore(List<String> recommendItemIds);

    List<Store> selectBestStores(String searchWord);
    List<String> mahout(String id) throws IOException, TasteException;


}

@Service
public class RecommendServiceImpl implements RecommendService {
    @Autowired
    RecommendRepository recommendRepository;

    @Override
    public List<Store> recommendStore(List<String> recommendItemIds) {
        List<Store> recommendList = new ArrayList<>();
        for (String StoreId : recommendItemIds) {
            recommendList.add(recommendRepository.recommendStores(StoreId));
        }

        return recommendList;
    }

    @Override
    public List<Store> selectBestStores(String searchWord) {
        return recommendRepository.fetchByBestStore(searchWord);
    }


    public List<String> mahout(String id) throws IOException, TasteException {
        DataModel model = new FileDataModel(new File("C:\\Users\\bit\\TeamProject\\TeamProject_server\\src\\main\\resources\\static\\recommend_dataset.csv"));
        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
        UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.6,
                similarity, model);
        UserBasedRecommender recommender = new GenericUserBasedRecommender(
                model, neighborhood, similarity);

        List<RecommendedItem> recommendations = recommender.recommend(Long.parseLong(id), 30);

        List<String> recommendItemIds = new ArrayList<>();

        for (RecommendedItem recommendation : recommendations) {
            recommendItemIds.add(Long.toString(recommendation.getItemID()));
        }
        System.out.println("배열의 갯수" + recommendItemIds.size());
        return recommendItemIds;
    }
}


