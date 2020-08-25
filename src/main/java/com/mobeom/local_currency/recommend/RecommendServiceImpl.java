package com.mobeom.local_currency.recommend;

import com.mobeom.local_currency.join.IndustryStore;
import com.mobeom.local_currency.rating.RatingRepository;
import com.mobeom.local_currency.store.Store;
import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.AllArgsConstructor;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.model.jdbc.ReloadFromJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Component
interface RecommendService {

    List<IndustryStore> findBestStores(double lnt, double lng);

    List<String> findUserBasedRecommend(String id) throws TasteException;

    List<String> findItemBasedRecommend(String itemId) throws TasteException;

    List<IndustryStore> findRecommendedStores(List<String> recommendItemIds);

    List<Consume> findIndustryByGenderAndAge(String searchWord, int birthYear);

    List<Consume> findIndustryByAge(int age);

    List<Consume> findIndustryByGender(String gender);

    List<Consume> findIndustryByTotal();

    Store findOneRatedStore(String id);

    Store findOneFavStore(String id);

    List<IndustryStore> findStoresByIndustry(String id, double lnt, double lng);

    Map<String, List<IndustryStore>> findStoresByIndustryList(List<Consume> industryList, double lnt, double lng);

    List<IndustryStore> findFavoriteStores(double lat, double lng);

    List<IndustryStore> findBestRatedStores(double lat, double lng);

    Map<String, List<IndustryStore>> findBestRatedStoresByIndustryList(List<Consume> industryList, double lat, double lng);

    Map<String, List<IndustryStore>> findFavStoresByIndustryList(List<Consume> industryList, double lat, double lng);

    boolean findUserByUserIdInRating(String id);
}

@Service
@AllArgsConstructor
public class RecommendServiceImpl implements RecommendService {
    private final RecommendRepository recommendRepository;
    private final RatingRepository ratingRepository;


    @Override
    public List<String> findUserBasedRecommend(String id) throws TasteException {

        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/mariadb?serverTimezone=UTC");
        dataSource.setUser("root");
        dataSource.setPassword("mariadb");
        MySQLJDBCDataModel model = new MySQLJDBCDataModel
                (dataSource, "rating", "user_id",
                        "store_id", "star_rating", null);
        ReloadFromJDBCDataModel fastModel= new ReloadFromJDBCDataModel(model); // 다음 유저의 즐겨찾기 데이터가 없을시 최근 디비에 갔다온 유저 데이터가 나오는 버그 발생.
        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
        UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.7, similarity, fastModel);
        UserBasedRecommender recommender = new GenericUserBasedRecommender(fastModel, neighborhood, similarity);

        List<RecommendedItem> recommendations = recommender.recommend(Long.parseLong(id), 7);
        List<String> recommendItemIds = new ArrayList<>();

        for (RecommendedItem recommendation : recommendations) {
            recommendItemIds.add(Long.toString(recommendation.getItemID()));
        }

        return recommendItemIds;
    }

    @Override
    public List<String> findItemBasedRecommend(String id) throws TasteException {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/mariadb?serverTimezone=UTC");
        dataSource.setUser("root");
        dataSource.setPassword("mariadb");
        MySQLJDBCDataModel model = new MySQLJDBCDataModel
                (dataSource, "rating", "user_id",
                        "store_id", "star_rating", null);
        ReloadFromJDBCDataModel fastModel = new ReloadFromJDBCDataModel(model);
        ItemSimilarity similarity = new PearsonCorrelationSimilarity(fastModel);
        ItemBasedRecommender recommender = new GenericItemBasedRecommender(fastModel, similarity);

        Long itemId = findOneRatedStore(id).getId();
        List<RecommendedItem> recommendations = recommender.mostSimilarItems(itemId, 7);

        List<String> recommendItemIds = new ArrayList<>();

        for (RecommendedItem recommendation : recommendations) {
            recommendItemIds.add(Long.toString(recommendation.getItemID()));
        }
        return recommendItemIds;
    }


    @Override
    @Transactional
    public List<IndustryStore> findRecommendedStores(List<String> recommendItemIds) {

        List<IndustryStore> recommendList = new ArrayList<>();
        for (String StoreId : recommendItemIds) {
            IndustryStore stores = recommendRepository.fetchRecommendedStores(StoreId);
            stores.setImgUrl(recommendRepository.fetchImg(stores));
            recommendList.add(stores);
        }
        return recommendList;
    }

    @Override
    @Transactional
    public List<IndustryStore> findBestStores(double lat, double lng) {
        List<IndustryStore> list = recommendRepository.fetchBestStore(lat, lng);
        List<IndustryStore> result = new ArrayList<>();
        for (IndustryStore store : list) {
            store.setImgUrl(recommendRepository.fetchImg(store));
            result.add(store);
        }
        return result;
    }


    @Override
    public List<Consume> findIndustryByGenderAndAge(String searchWord, int ageGroup) {
        return recommendRepository.fetchIndustryRankByGenderAndAge(searchWord, ageGroup);
    }


    @Override
    public List<Consume> findIndustryByAge(int age) {
        return recommendRepository.fetchIndustryRankByAge(age);
    }

    @Override
    public List<Consume> findIndustryByGender(String gender) {
        return recommendRepository.fetchIndustryRankByGender(gender);
    }

    @Override
    public List<Consume> findIndustryByTotal() {
        return recommendRepository.fetchIndustryRankByTotal();
    }


    @Override
    public Map<String, List<IndustryStore>> findStoresByIndustryList(List<Consume> industryList, double lat, double lng) {
        Map<String, List<IndustryStore>> result = new HashMap<>();
        for (Consume industryName : industryList) {
            result.put(industryName.getIndustryName(), recommendRepository.fetchStoreByIndustry(industryName.getIndustryName(), lat, lng));
        }
        return result;
    }

    @Override
    public Map<String, List<IndustryStore>> findFavStoresByIndustryList(List<Consume> industryList, double lat, double lng) {
        Map<String, List<IndustryStore>> result = new HashMap<>();
        for (Consume industryName : industryList) {
            if (recommendRepository.fetchMostFavStoresByIndustry(industryName.getIndustryName(), lat, lng).size() == 0) {
                result.put(industryName.getIndustryName(), recommendRepository.fetchStoreByIndustry(industryName.getIndustryName(), lat, lng));
            } else {
                result.put(industryName.getIndustryName(), recommendRepository.fetchMostFavStoresByIndustry(industryName.getIndustryName(), lat, lng));
            }
        }
        return result;
    }

    @Override
    public Map<String, List<IndustryStore>> findBestRatedStoresByIndustryList(List<Consume> industryList, double lat, double lng) {
        Map<String, List<IndustryStore>> result = new HashMap<>();
        for (Consume industryName : industryList) {
            if (recommendRepository.fetchBestRatedStoresByIndustry(industryName.getIndustryName(), lat, lng).size() == 0) {
                result.put(industryName.getIndustryName(), recommendRepository.fetchStoreByIndustry(industryName.getIndustryName(), lat, lng));
            } else {
                result.put(industryName.getIndustryName(), recommendRepository.fetchBestRatedStoresByIndustry(industryName.getIndustryName(), lat, lng));
            }
        }
        return result;
    }


    @Override
    public Store findOneRatedStore(String id) {
        return recommendRepository.fetchRatedStore(id);
    }

    @Override
    public Store findOneFavStore(String id) {
        return recommendRepository.fetchOneFavStore(id);
    }


    @Override
    public List<IndustryStore> findStoresByIndustry(String industry, double lat, double lng) {
        return recommendRepository.fetchStoreByIndustry(industry, lat, lng);
    }

    @Override
    public List<IndustryStore> findBestRatedStores(double lat, double lng) {
        return recommendRepository.fetchBestRatedStores(lat, lng);
    }

    @Override
    public List<IndustryStore> findFavoriteStores(double lat, double lng) {
        return recommendRepository.fetchMostFavoriteStores(lat, lng);
    }

    @Override
    public boolean findUserByUserIdInRating(String id) {
        Optional<Long> ratingUser = Optional.ofNullable(ratingRepository.findByUserId(id));
        return ratingUser.isPresent();
    }


}


