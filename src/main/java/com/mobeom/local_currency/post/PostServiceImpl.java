package com.mobeom.local_currency.post;


import com.mobeom.local_currency.board.Board;
import com.mobeom.local_currency.board.BoardRepository;
import com.mobeom.local_currency.join.NoticeVo;
import com.mobeom.local_currency.join.QuestionVO;
import com.mobeom.local_currency.join.ReviewRatingVO;
import com.mobeom.local_currency.rating.Rating;
import com.mobeom.local_currency.rating.RatingRepository;
import com.mobeom.local_currency.store.Store;
import com.mobeom.local_currency.store.StoreRepository;
import com.mobeom.local_currency.user.User;
import com.mobeom.local_currency.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
interface PostService {
    List<Post> postNoticeList();

    Optional<Post> onePost(long postId);

    Post insertNotice(NoticeVo notice);

    Post updatePost(Post notice);

    List<Post> noticeSearch(String searchWord, String category);

    List<Post> inquiryList();

    Post createReview(String storeId, ReviewRatingVO review);

    Map<Long, ReviewRatingVO> getAllReviewsByUserId(long userId);

    ReviewRatingVO getOneReviewById(long postId);

    Post findPost(long reviewId);

    void deletePost(Post findOne);

    Post createQuestion(String userId, QuestionVO question);

    Map<Long, QuestionVO> getAllQuestionsByUserId(long userId);

    QuestionVO getOneQuestionById(long postId);

    Map<Long, QuestionVO> getAllQuestionsBySelectedOption(String selectedOption, String searchWord);

}

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final RatingRepository ratingRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public PostServiceImpl(PostRepository postRepository,
                           RatingRepository ratingRepository,
                           StoreRepository storeRepository,
                           UserRepository userRepository,
                           BoardRepository boardRepository) {
        this.postRepository = postRepository;
        this.ratingRepository = ratingRepository;
        this.storeRepository = storeRepository;
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
    }

    @Override
    public List<Post> postNoticeList() {
        return postRepository.postList();
    }

    @Override
    public Optional<Post> onePost(long postId) {
        Optional<Post> onePost = postRepository.findById(postId);
        return onePost;
    }

    @Override
    public Post insertNotice(NoticeVo notice) {
        Optional<User> user = userRepository.findById(notice.getUserId());
        Optional<Board> board = boardRepository.findById((long) 2);

        Post insertNotice = new Post();
        insertNotice.setCategory(notice.getCategory());
        insertNotice.setPostTitle(notice.getPostTitle());
        insertNotice.setContents(notice.getContents());
        insertNotice.setBoard(board.get());
        insertNotice.setUser(user.get());
        insertNotice.setDeleteYn(false);
        insertNotice.setNoticeYn(true);
        return postRepository.save(insertNotice);
    }

    @Override
    public Post updatePost(Post notice) {
        return postRepository.save(notice);
    }

    @Override
    public List<Post> noticeSearch(String searchWord, String category) {
        return postRepository.noticeSearch(searchWord, category);
    }

    @Override
    public List<Post> inquiryList() {
        return postRepository.inquiryList();
    }

    @Override
    public Post createReview(String storeId, ReviewRatingVO review) {
        Optional<Store> store = storeRepository.findById(Long.parseLong(storeId));
        Optional<User> user = userRepository.findById(review.getUserId());
        Optional<Board> board = boardRepository.findById((long) 3);

        Rating newRating = new Rating();
        newRating.setUser(user.get());
        newRating.setStore(store.get());
        newRating.setStarRating(review.getStarRating());
        Rating savedRating = ratingRepository.save(newRating);


        Post newPost = new Post();
        newPost.setBoard(board.get());
        newPost.setContents(review.getContents());
        newPost.setDeleteYn(false);
        newPost.setNoticeYn(false);
        newPost.setRating(savedRating);
        newPost.setUser(user.get());
        newPost.setRegDate(newPost.getRegDate());
        return postRepository.save(newPost);
    }

    @Override
    public Map<Long, ReviewRatingVO> getAllReviewsByUserId(long userId) {
        Map<Long, ReviewRatingVO> resultMap = new HashMap<>();
        List<Post> usersReviews = postRepository.findAllPostsByUserIdAndBoardId(userId, (long) 3);
        usersReviews.forEach(review -> {
            ReviewRatingVO oneReview = new ReviewRatingVO();
            oneReview.setStoreId(review.getRating().getStore().getId());
            oneReview.setStoreName(review.getRating().getStore().getStoreName());
            oneReview.setContents(review.getContents());
            oneReview.setStarRating(review.getRating().getStarRating());
            resultMap.put(review.getPostId(), oneReview);
        });
        return resultMap;
    }

    @Override
    public ReviewRatingVO getOneReviewById(long postId) {
        ReviewRatingVO resultReview = new ReviewRatingVO();
        Optional<Post> findOne = postRepository.findById(postId);
        Post review = findOne.get();
        resultReview.setStoreName(review.getRating().getStore().getStoreName());
        resultReview.setStoreId(review.getRating().getStore().getId());
        resultReview.setStarRating(review.getRating().getStarRating());
        resultReview.setUserId(review.getUser().getId());
        resultReview.setContents(review.getContents());
        return resultReview;
    }

    @Override
    public Post findPost(long reviewId) {
        Optional<Post> findOne = postRepository.findById(reviewId);
        return findOne.get();
    }

    @Override
    public void deletePost(Post findOne) {
        postRepository.delete(findOne);
    }

    @Override
    public Post createQuestion(String userId, QuestionVO question) {
        Optional<User> user = userRepository.findById(Long.parseLong(userId));
        Optional<Board> board = boardRepository.findById((long) 1);

        Post newPost = new Post();
        newPost.setBoard(board.get());
        newPost.setPostTitle(question.getPostTitle());
        newPost.setContents(question.getContents());
        newPost.setDeleteYn(false);
        newPost.setNoticeYn(false);
        newPost.setUser(user.get());
        newPost.setRegDate(newPost.getRegDate());
        return postRepository.save(newPost);
    }

    @Override
    public Map<Long, QuestionVO> getAllQuestionsByUserId(long userId) {
        Map<Long, QuestionVO> resultMap = new HashMap<>();
        List<Post> usersQuestions = postRepository.findAllPostsByUserIdAndBoardId(userId, (long) 1);
        usersQuestions.forEach(question -> {
            QuestionVO oneQuestion = new QuestionVO();
            oneQuestion.setPostTitle(question.getPostTitle());
            oneQuestion.setContents(question.getContents());
            oneQuestion.setComment(question.getComment());
            resultMap.put(question.getPostId(), oneQuestion);
        });
        return resultMap;
    }

    @Override
    public QuestionVO getOneQuestionById(long postId) {
        QuestionVO resultQuestion = new QuestionVO();
        Optional<Post> findOne = postRepository.findById(postId);
        Post question = findOne.get();
        resultQuestion.setUserId(question.getUser().getId());
        resultQuestion.setPostTitle(question.getPostTitle());
        resultQuestion.setContents(question.getContents());
        resultQuestion.setComment(question.getComment());
        return resultQuestion;
    }

    @Override
    public Map<Long, QuestionVO> getAllQuestionsBySelectedOption(String selectedOption, String searchWord) {
        Map<Long, QuestionVO> resultMap = new HashMap<>();
        List<Post> questions = new ArrayList<>();
        if (searchWord.equals("undefined") || searchWord.equals("")) {
            if (selectedOption.equals("all")) {
                questions.addAll(postRepository.findAllByBoardId((long) 1));
            } else {
                questions.addAll(postRepository.findAllByBoardIdAndCommentYn((long) 1, selectedOption));
            }
        } else {
            questions.addAll(postRepository.findAllByBoardIdCommentYnSearchWord((long) 1, selectedOption, searchWord));
        }

        questions.forEach(question -> {
            QuestionVO newQuestion = new QuestionVO();
            newQuestion.setUserId(question.getUser().getId());
            newQuestion.setUserUniqueId(question.getUser().getUserId());
            newQuestion.setUserName(question.getUser().getName());
            newQuestion.setPostTitle(question.getPostTitle());
            newQuestion.setContents(question.getContents());
            newQuestion.setComment(question.getComment());
            newQuestion.setRegDate(question.getRegDate());
            resultMap.put(question.getPostId(), newQuestion);
        });
        return resultMap;
    }
}
