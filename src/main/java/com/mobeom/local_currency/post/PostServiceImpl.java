package com.mobeom.local_currency.post;

import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
interface PostService {
    List<Post>  postList();
    Optional<Post> onePost(long postId);
    Post insertNotice(Post notice);
    Post updatePost(Post notice);
    void deleteNotice(Post notice);
    List<Post> inquiryList();



}

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    @Override
    public List<Post> postList() {
        return postRepository.findAll();
    }

    @Override
    public Optional<Post> onePost(long postId) {
        Optional<Post> onePost = postRepository.findById(postId);
        return onePost;
    }

    @Override
    public Post insertNotice(Post notice) {
        return postRepository.save(notice);
    }

    @Override
    public Post updatePost(Post notice) {
        return postRepository.save(notice);
    }

    @Override
    public void deleteNotice(Post notice) {
        postRepository.delete(notice);
    }

    @Override
    public List<Post> inquiryList() {
        return postRepository.inquiryList();
    }






}
