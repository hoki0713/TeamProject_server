package com.mobeom.local_currency.recommend;

import com.mobeom.local_currency.consume.GenderAge;
import com.mobeom.local_currency.join.IndustryStore;
import com.mobeom.local_currency.store.Store;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
interface RecommendService {
    List<Store> recommendStore(List<String> recommendItemIds);

    List<Store> selectBestStores(String searchWord);

    List<String> mahout(String id) throws IOException, TasteException;

    //    List<StoreVo> testRecommend(String storeName, String storeType);
    List<IndustryStore> fetchStoreByIndustry(String searchIndustry, String town);

    List<GenderAge> industryByGenderAndAge(String searchWord, int age);

    List<GenderAge> industryByAge(int age);

    List<GenderAge> industryByGender(String gender);

    List<List<IndustryStore>> fetchStores(String searchWord, int age, String town);

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


    @Override
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

//    @Override
//    public List<StoreVo> testRecommend(String storeName, String storeType) {
//        return recommendRepository.testRecommend(storeName, storeType);
//    }

    @Override
    public List<IndustryStore> fetchStoreByIndustry(String searchIndustry, String town) {
        return recommendRepository.fetchStoreByIndustry(searchIndustry, town);
    }

    @Override
    public List<GenderAge> industryByGenderAndAge(String searchWord, int age) {
        return recommendRepository.industryByGenderAndAge(searchWord, age);
    }

    @Override
    public List<GenderAge> industryByAge(int age) {
//        Box<List<GenderAge>> box= new Box<>();
//
//        for(int i=1; i<7; i++){
//            box.put(String.valueOf(i), recommendRepository.industryByAge(i));
//        }
        return recommendRepository.industryByAge(age);

    }

    @Override
    public List<GenderAge> industryByGender(String gender) {
        return recommendRepository.industryByGender(gender);
    }

    @Override
    public List<List<IndustryStore>> fetchStores(String searchWord, int age, String town) {
        List<List<IndustryStore>> resultList = new ArrayList<>();
        for(GenderAge industryName : industryByGenderAndAge(searchWord, age)){
            resultList.add(recommendRepository.fetchStoreByIndustry(industryName.getIndustryName(), town));
        }

        return resultList;

    }
}


