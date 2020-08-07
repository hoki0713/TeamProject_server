package com.mobeom.local_currency.post;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
interface PostService {
    List<Post> findAll(String searchWord);
}

@Service
public class PostServiceImpl implements PostService{
    @Autowired PostRepository postRepository;

    @Override
    public List<Post> findAll(String searchWord) {
        return postRepository.List(searchWord);
    }
}
