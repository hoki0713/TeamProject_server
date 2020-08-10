package com.mobeom.local_currency.post;

import com.mobeom.local_currency.rating.Rating;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Optional<NoticeVO>> oneNoticePost(@PathVariable String postId){
        Optional<NoticeVO> findOne = postService.findOne(Long.parseLong(postId));
       return ResponseEntity.ok(findOne);
    }

    @GetMapping("/postlist")
    public ResponseEntity<List<Post>> postList(){
        List<Post> list = postRepository.findAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/reviews/{storeId}")
    public ResponseEntity<Post> createReview(@PathVariable String storeId,
                                             @RequestBody ReviewVO review) {
        Optional<Post> userReview = postService.createReview(storeId, review);
        if(userReview.isPresent()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
