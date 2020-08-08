package com.mobeom.local_currency.post;

import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
interface PostService {

    Optional<Post> findOne(long postId);
    Post insertNotice(Post notice);
}

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Optional<Post> findOne(long postId) {
        Optional<Post> findOne = postRepository.findById(postId);
        return Optional.of(findOne.get());
    }

    @Override
    public Post insertNotice(Post notice) {
        return postRepository.save(notice);

    }


}
