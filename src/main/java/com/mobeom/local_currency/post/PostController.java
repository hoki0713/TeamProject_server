package com.mobeom.local_currency.post;

import com.mobeom.local_currency.join.NoticeVo;
import com.mobeom.local_currency.join.QuestionVO;
import com.mobeom.local_currency.join.ReviewRatingVO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostRepository postRepository;
    private final PostService postService;

    public PostController(PostRepository postRepository, PostService postService) {
        this.postRepository = postRepository;
        this.postService = postService;
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<Optional<Post>> oneNoticePost(@PathVariable String postId) {
        Optional<Post> oneNotice = postService.onePost(Long.parseLong(postId));
        return oneNotice.isPresent() ? ResponseEntity.ok(oneNotice) : ResponseEntity.notFound().build();

    }

    @GetMapping("/postlist")
    public ResponseEntity<List<Post>> postList() {
        List<Post> list = postService.postNoticeList();

        return ResponseEntity.ok(list);
    }

    @PostMapping("/notice/create")
    public Post noticeCreate(@RequestBody NoticeVo notice) {
        return postService.insertNotice(notice);
    }


    @PatchMapping("/update/{postId}")
    public Post update(@PathVariable String postId,
                       @RequestBody Post updateNotice) {

        Optional<Post> findOne = postService.onePost(Long.parseLong(postId));

        Post updatePost = findOne.get();
        updatePost.setContents(updateNotice.getContents());
        updatePost.setPostTitle(updateNotice.getPostTitle());
        updatePost.setCategory(updateNotice.getCategory());
        updatePost.setModiDate(LocalDate.now());
        postService.updatePost(updatePost);
        return updatePost;
    }

    @DeleteMapping("/delete/{postId}")
    public void deleteNotice(@PathVariable String postId) {
        Optional<Post> findOne = postService.onePost(Long.parseLong(postId));
        Post deletePost = findOne.get();
        if (findOne.isPresent()) {
            deletePost.setDeleteYn(true);
        }
        postService.updatePost(deletePost);
    }

    @GetMapping("/inquiry/all")
    public ResponseEntity<List<Post>> inquiryList() {
        List<Post> inquirys = postService.inquiryList();
        return ResponseEntity.ok(inquirys);
    }

    @PatchMapping("/replies/{postId}")
    public Post repliesCreate(@PathVariable String postId, @RequestBody Post updateComment) {
        Optional<Post> findPost = postService.onePost(Long.parseLong(postId));
        Post repliesPost = findPost.get();
        if (findPost.isPresent()) {
            repliesPost.setComment(updateComment.getComment());
            postService.updatePost(repliesPost);
        }
        return repliesPost;
    }


    @PostMapping("/reviews/{storeId}")
    public ResponseEntity<Post> createReview(@PathVariable String storeId,
                                             @RequestBody ReviewRatingVO review) {
        Post userReview = postService.createReview(storeId, review);
        return ResponseEntity.ok(userReview);
    }

    @GetMapping("/reviews/{userId}")
    public ResponseEntity<Map<Long, ReviewRatingVO>> getAllReviewsByUserId(@PathVariable String userId) {
        Map<Long, ReviewRatingVO> reviews = postService.getAllReviewsByUserId(Long.parseLong(userId));
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/reviews/detail/{reviewId}")
    public ResponseEntity<ReviewRatingVO> getOneReviewById(@PathVariable String reviewId) {
        ReviewRatingVO review = postService.getOneReviewById(Long.parseLong(reviewId));
        return ResponseEntity.ok(review);
    }

    @PatchMapping("/reviews/{reviewId}")
    public ResponseEntity<Post> updateReview(@PathVariable String reviewId, @RequestBody ReviewRatingVO review) {
        Post selectPost = postService.findPost(Long.parseLong(reviewId));
        Optional.ofNullable(review.getContents()).ifPresent(contents -> selectPost.setContents(contents));
        Optional.ofNullable(review.getStarRating()).ifPresent(rating -> selectPost.getRating().setStarRating(rating));
        return ResponseEntity.ok(postService.updatePost(selectPost));
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Post> deleteReview(@PathVariable String reviewId) {
        Post findOne = postService.findPost(Long.parseLong(reviewId));
        postService.deletePost(findOne);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/questions/{userId}")
    public ResponseEntity<Post> createQuestion(@PathVariable String userId,
                                               @RequestBody QuestionVO question) {
        Post userQuestion = postService.createQuestion(userId, question);
        return ResponseEntity.ok(userQuestion);
    }

    @GetMapping("/questions/{userId}")
    public ResponseEntity<Map<Long, QuestionVO>> getAllQuestionsByUserId(@PathVariable String userId) {
        Map<Long, QuestionVO> questions = postService.getAllQuestionsByUserId(Long.parseLong(userId));
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/questions/detail/{questionId}")
    public ResponseEntity<QuestionVO> getOneQuestionById(@PathVariable String questionId) {
        QuestionVO question = postService.getOneQuestionById(Long.parseLong(questionId));
        return ResponseEntity.ok(question);
    }

    @PatchMapping("/questions/{questionId}")
    public ResponseEntity<Post> updateQuestion(@PathVariable String questionId, @RequestBody QuestionVO question) {
        Post selectPost = postService.findPost(Long.parseLong(questionId));
        Optional.ofNullable(question.getContents()).ifPresent(contents -> selectPost.setContents(contents));
        Optional.ofNullable(question.getComment()).ifPresent(comment -> selectPost.setComment(comment));
        return ResponseEntity.ok(postService.updatePost(selectPost));
    }

    @DeleteMapping("/questions/{questionId}")
    public ResponseEntity<Post> deleteQuestion(@PathVariable String questionId) {
        Post findOne = postService.findPost(Long.parseLong(questionId));
        postService.deletePost(findOne);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/questions")
    public ResponseEntity<Map<Long, QuestionVO>> getAllQuestionsBySelectedOption(@RequestParam String selectedOption,
                                                                                 @RequestParam String searchWord) {

        Map<Long, QuestionVO> questions = postService.getAllQuestionsBySelectedOption(selectedOption, searchWord);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/notice/search")
    public List<Post> noticeSearch(@RequestParam("searchWord") String searchWord,
                                   @RequestParam("categorySelect") String category) {

        return postService.noticeSearch(searchWord, category);
    }

}
