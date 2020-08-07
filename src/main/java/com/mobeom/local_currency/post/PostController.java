package com.mobeom.local_currency.post;

import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostController {
    @Autowired PostRepository postRepository;
    @Autowired PostService postService;

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





}
