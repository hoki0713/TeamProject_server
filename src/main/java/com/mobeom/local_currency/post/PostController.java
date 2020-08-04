package com.mobeom.local_currency.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/notice/list/{searchWord}")
    public ResponseEntity<List<Post>> getAllList(@PathVariable String searchWord){

        System.out.println("왔다"+searchWord);
        List<Post> noticeList = postService.findAll(searchWord);
        System.out.println(postService.findAll(searchWord).toString());
        return ResponseEntity.ok(noticeList);
    }


}
