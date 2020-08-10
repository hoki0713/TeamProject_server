package com.mobeom.local_currency.post;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public ResponseEntity<Optional<Post>> oneNoticePost(@PathVariable String postId){
       Optional<Post> oneNotice = postService.onePost(Long.parseLong(postId));
        return oneNotice.isPresent()? ResponseEntity.ok(oneNotice) : ResponseEntity.notFound().build();
       //return oneNotice.orElse(null);

    }

    @GetMapping("/postlist")
    public ResponseEntity<List<Post>> postList(){
        List<Post> list = postService.postList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/create")
    public Post noticeCreate(@RequestBody Post notice){
        return postService.insertNotice(notice);
    }



    @PatchMapping(value = "/{postId}")
    public Post update(@PathVariable String postId,
                       @RequestBody Post updateNotice) {

        Optional<Post> findOne = postService.onePost(Long.parseLong(postId));
        System.out.println(findOne.toString());
        Post updatePost = findOne.get();
        if (findOne.isPresent()) {
            updatePost.setContents(updateNotice.getContents());
            updatePost.setPostTitle(updateNotice.getPostTitle());
            updatePost.setCategory(updateNotice.getCategory());
            updatePost.setModiDate(LocalDate.now());
            postService.updatePost(updatePost);
        }
        return updatePost;
    }

    @DeleteMapping("/delete/{postId}")
    public void deleteNotice(@PathVariable String postId){
        Optional<Post> findOne = postService.onePost(Long.parseLong(postId));
        Post deletePost = findOne.get();
        postService.deleteNotice(deletePost);
    }

    @GetMapping("/inquiry/all")
    public ResponseEntity<List<Post>> inquiryList(){
        List<Post> inquirys = postService.inquiryList();
        return ResponseEntity.ok(inquirys);
    }

    @PatchMapping("/replies/{postId}")
    public Post repliesCreate(@PathVariable String postId,@RequestBody Post updateComment){
        Optional<Post> findPost = postService.onePost(Long.parseLong(postId));
        Post repliesPost = findPost.get();
        if(findPost.isPresent()){
            repliesPost.setComment(updateComment.getComment());
            postService.updatePost(repliesPost);
        }
        return repliesPost;
    }

}
