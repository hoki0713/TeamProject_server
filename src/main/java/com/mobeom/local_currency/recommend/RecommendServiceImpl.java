package com.mobeom.local_currency.recommend;

import com.mobeom.local_currency.favorites.FavoritesRepository;
import com.mobeom.local_currency.join.IndustryStore;
import com.mobeom.local_currency.user.User;
import com.mobeom.local_currency.user.UserRepository;
import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.AllArgsConstructor;
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
    List<IndustryStore> recommendStore(List<String> recommendItemIds);

    List<IndustryStore> selectBestStores(String id);

    List<String> userBasedRecommend(String id) throws TasteException;

    List<String> itemBasedRecommend(String itemId) throws TasteException;

    List<IndustryStore> fetchStoreByIndustry(String searchIndustry);

    List<GenderAge> industryByGenderAndAge(String searchWord, int birthYear);

    List<GenderAge> industryByAge(int age);

    List<GenderAge> industryByGender(String gender);

    List<GenderAge> industryByTotal();

    Map<String, List<IndustryStore>> fetchStores(String searchWord, int age, String town);

    Map<String, List<IndustryStore>> findStoreByIndustryList(List<GenderAge> industryList);
}

@Service
@AllArgsConstructor
public class RecommendServiceImpl implements RecommendService {
    private final RecommendRepository recommendRepository;
    private final UserRepository userRepository;
    private final FavoritesRepository favoritesRepository;



    @Override
    public List<String> userBasedRecommend(String id) throws TasteException {

        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/mariadb?serverTimezone=UTC");
        dataSource.setUser("root");
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
    public List<String> itemBasedRecommend(String id) throws TasteException {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/mariadb?serverTimezone=UTC");
        dataSource.setUser("root");
        dataSource.setPassword("mariadb");

        MySQLJDBCDataModel model = new MySQLJDBCDataModel(dataSource, "rating", "user_id", "store_id", "star_rating", null);

        ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
        ItemBasedRecommender recommender = new GenericItemBasedRecommender(model, similarity);

        Long itemId = fetchStoreIdByUserId(id);
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
    public List<IndustryStore> recommendStore(List<String> recommendItemIds) {
        List<IndustryStore> recommendList = new ArrayList<>();
        for (String StoreId : recommendItemIds) {
            recommendList.add(recommendRepository.recommendStores(StoreId));
        }

        return recommendList;
    }

    @Override
    public List<IndustryStore> selectBestStores(String id) {
        Optional<User> searchUser = userRepository.findById(Long.parseLong(id));
        String address = searchUser.get().getDefaultAddr().split("\\s")[2];
        String[] town = address.split("");
        StringBuilder townName= new StringBuilder();
        for (String s : town) {
            townName.append(s);
        }
        System.out.println(townName);


        if (searchUser.isPresent()) {
            System.out.println("유저 있음");
        } else {
            System.out.println("유저 없대");
        }
        return recommendRepository.fetchByBestStore(townName.toString());


    }


    @Override
    public List<IndustryStore> fetchStoreByIndustry(String searchIndustry) {
        return recommendRepository.fetchStoreByIndustry(searchIndustry);
    }

    @Override
    public List<GenderAge> industryByGenderAndAge(String searchWord, int ageGroup) {
        return recommendRepository.industryByGenderAndAge(searchWord, ageGroup);}


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
    public Map<String, List<IndustryStore>> fetchStores(String searchWord, int age, String town) {
        Map<String, List<IndustryStore>> resultList = new HashMap<>();
        for (GenderAge industryName : industryByGenderAndAge(searchWord, age)) {
            resultList.put("", recommendRepository.fetchStoreByIndustry(industryName.getIndustryName()));
        }

        return resultList;

    }

    @Override
    public Map<String, List<IndustryStore>> findStoreByIndustryList(List<GenderAge> industryList) {
        Map<String, List<IndustryStore>> result = new HashMap<>();
        for (GenderAge industryName :industryList) {
            result.put(industryName.getIndustryName(), recommendRepository.fetchStoreByIndustry(industryName.getIndustryName()));
        }
        return result;
    }

    public Long fetchStoreIdByUserId(String id){
//        Optional<User> findUser = userRepository.findById(Long.parseLong(id));
//        if (findUser.isPresent()){
//            List<Favorites> favoriteStores = favoritesRepository.findAllStoreByUserId(findUser.get().getId());
//            List<Store> favoritesStores = new ArrayList<>();
//            for(Favorites favorites : favoriteStores){
//                favoritesStores.add(favorites.getStore());
//            }
//        }
        return recommendRepository.fetchedStoreId(id);

    }
    @Override
    public List<GenderAge> industryByTotal(){
        return recommendRepository.industryByTotal();
    }


}


