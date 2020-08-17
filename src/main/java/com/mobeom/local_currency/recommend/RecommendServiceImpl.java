package com.mobeom.local_currency.recommend;

import com.mobeom.local_currency.favorites.Favorites;
import com.mobeom.local_currency.favorites.FavoritesRepository;
import com.mobeom.local_currency.join.IndustryStore;
import com.mobeom.local_currency.store.LatLngVo;
import com.mobeom.local_currency.store.Store;
import com.mobeom.local_currency.user.User;
import com.mobeom.local_currency.user.UserRepository;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
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

import java.util.*;

@Component
interface RecommendService {

    boolean isPresentFavorites(String id);

    List<IndustryStore> findBestStores(double lnt, double lng);

    List<String> findUserBasedRecommend(String id) throws TasteException;

    List<String> findItemBasedRecommend(String itemId) throws TasteException;

    List<IndustryStore> findRecommendStores(List<String> recommendItemIds);

    List<GenderAge> findIndustryByGenderAndAge(String searchWord, int birthYear);

    List<GenderAge> findIndustryByAge(int age);

    List<GenderAge> findIndustryByGender(String gender);

    List<GenderAge> findIndustryByTotal();

    Store fetchStoreIdByUserId(String id);

    List<IndustryStore> findStoresByIndustry(String id, double lnt, double lng);

    Map<String, List<IndustryStore>> findStoresByIndustryList(List<GenderAge> industryList, double lnt, double lng);

    List<IndustryStore> findMostFavoriteStores(double lat, double lng);

    List<IndustryStore> findBestRatedStores(double lat, double lng);
}

@Service
public class RecommendServiceImpl implements RecommendService {
    private final RecommendRepository recommendRepository;
    private final UserRepository userRepository;
    private final FavoritesRepository favoritesRepository;

    public RecommendServiceImpl(RecommendRepository recommendRepository, UserRepository userRepository, FavoritesRepository favoritesRepository) {
        this.recommendRepository = recommendRepository;
        this.userRepository = userRepository;
        this.favoritesRepository = favoritesRepository;
    }


    @Override
    public List<String> findUserBasedRecommend(String id) throws TasteException {

        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/teamproject?serverTimezone=UTC");
        dataSource.setUser("mariadb");
        dataSource.setPassword("mariadb");

        MySQLJDBCDataModel model = new MySQLJDBCDataModel(dataSource, "rating", "user_id", "store_id", "star_rating", null);

        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
        UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.7,
                similarity, model);
        UserBasedRecommender recommender = new GenericUserBasedRecommender(
                model, neighborhood, similarity);

        List<RecommendedItem> recommendations = recommender.recommend(Long.parseLong(id), 7);

        List<String> recommendItemIds = new ArrayList<>();

        for (RecommendedItem recommendation : recommendations) {
            recommendItemIds.add(Long.toString(recommendation.getItemID()));
        }
        System.out.println("유저 배열의 갯수" + recommendItemIds.size());

        return recommendItemIds;
    }

    @Override
    public List<String> findItemBasedRecommend(String id) throws TasteException {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/teamproject?serverTimezone=UTC");
        dataSource.setUser("mariadb");
        dataSource.setPassword("mariadb");

        MySQLJDBCDataModel model = new MySQLJDBCDataModel
                (dataSource, "rating", "user_id",
                        "store_id", "star_rating", null);

        ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
        ItemBasedRecommender recommender = new GenericItemBasedRecommender(model, similarity);

        Long itemId = fetchStoreIdByUserId(id).getId();
        System.out.println(itemId);
        List<RecommendedItem> recommendations = recommender.mostSimilarItems(1213, 7);

        List<String> recommendItemIds = new ArrayList<>();

        for (RecommendedItem recommendation : recommendations) {
            recommendItemIds.add(Long.toString(recommendation.getItemID()));
        }
        System.out.println("아이템 배열의 갯수" + recommendItemIds.size());
        return recommendItemIds;
    }


    @Override
    public List<IndustryStore> findRecommendStores(List<String> recommendItemIds) {
        List<IndustryStore> recommendList = new ArrayList<>();
        for (String StoreId : recommendItemIds) {
            recommendList.add(recommendRepository.recommendStores(StoreId));
        }

        return recommendList;
    }

    @Override
    public List<IndustryStore> findBestStores(double lat, double lng) {

        return recommendRepository.fetchByBestStore(lat, lng);
    }


    @Override
    public List<GenderAge> findIndustryByGenderAndAge(String searchWord, int ageGroup) {
        return recommendRepository.industryByGenderAndAge(searchWord, ageGroup);
    }


    @Override
    public List<GenderAge> findIndustryByAge(int age) {
        return recommendRepository.industryByAge(age);

    }

    @Override
    public List<GenderAge> findIndustryByGender(String gender) {
        return recommendRepository.industryByGender(gender);
    }

    @Override
    public List<GenderAge> findIndustryByTotal() {
        return recommendRepository.industryByTotal();
    }

//    @Override
//    public Map<String, List<IndustryStore>> fetchStores(String searchWord, int age, String town) {
//        Map<String, List<IndustryStore>> resultList = new HashMap<>();
//        for (GenderAge industryName : industryByGenderAndAge(searchWord, age)) {
//            resultList.put("", recommendRepository.fetchStoreByIndustry(industryName.getIndustryName()));
//        }
//
//        return resultList;
//
//    }

    @Override
    public Map<String, List<IndustryStore>> findStoresByIndustryList(List<GenderAge> industryList, double lat, double lng) {
        Map<String, List<IndustryStore>> result = new HashMap<>();
        for (GenderAge industryName : industryList) {
            result.put(industryName.getIndustryName(), recommendRepository.fetchStoreByIndustry(industryName.getIndustryName(), lat, lng));
        }
        return result;
    }

    @Override
    public boolean isPresentFavorites(String id) {
        List<Favorites> favorites = favoritesRepository.findAllByUserId(Long.parseLong(id));
        if (favorites.size() != 0) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public Store fetchStoreIdByUserId(String id) {
        return recommendRepository.fetchedFavoriteStoreByUserId(id);

    }

    @Override
    public List<IndustryStore> findStoresByIndustry(String industry, double lat, double lng) {
        return recommendRepository.fetchStoreByIndustry(industry, lat, lng);

    }

    @Override
    public List<IndustryStore> findBestRatedStores(double lat, double lng) {

        return recommendRepository.fetchedBestRatedStores(lat, lng);
    }

    @Override
    public List<IndustryStore> findMostFavoriteStores(double lat, double lng) {

        return recommendRepository.fetchedMostFavoriteStores(lat, lng);
    }


}


