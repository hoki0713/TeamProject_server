package com.mobeom.local_currency.post;

import org.hibernate.annotations.Fetch;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired PostRepository postRepository;
    @Autowired PostService postService;

    @GetMapping("/post/{postId}")
    public ResponseEntity<Optional<Post>> oneNoticePost(@PathVariable String postId){
        Optional<Post> findOne = postService.findOne(Long.parseLong(postId));
       return ResponseEntity.ok(findOne);

    }

    @GetMapping("/postlist")
    public ResponseEntity<List<Post>> postList(){
        List<Post> list = postRepository.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/create")
    public Post createTest(@RequestBody Post notice){
        return postService.insertNotice(notice);
    }



    @PatchMapping(value = "/{postId}")
    public Post update(@PathVariable String postId,
                       @RequestBody Post updateNotice) {

        Optional<Post> findOne = postService.findOne(Long.parseLong(postId));
        System.out.println(findOne.toString());
        Post updatePost = findOne.get();
        if (findOne.isPresent()) {
            updatePost.setContents(updateNotice.getContents());
            updatePost.setPostTitle(updateNotice.getPostTitle());
            updatePost.setCategory(updateNotice.getCategory());
            postRepository.save(updatePost);
        }
        return updatePost;
    }

    @DeleteMapping("/delete/{postId}")
    public void deleteNotice(@PathVariable String postId){
        Optional<Post> findOne = postService.findOne(Long.parseLong(postId));
        Post deletePost = findOne.get();
        postRepository.delete(deletePost);

    }


}
