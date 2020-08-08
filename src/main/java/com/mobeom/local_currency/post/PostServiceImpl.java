package com.mobeom.local_currency.post;

import com.mobeom.local_currency.board.Board;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
interface PostService {

    Optional<NoticeVO> findOne(long postId);

    Optional<Post> createReview(String storeId, Post review);
}

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Optional<NoticeVO> findOne(long postId) {
        NoticeVO findOne = postRepository.findByPostId(postId);
        return Optional.of(findOne);
    }

    @Override
    public Optional<Post> createReview(String storeId, Post review) {
        ReviewVO newReview = new ReviewVO();
        newReview.setRegDate(review.getRegDate());
        newReview.setPostTitle(review.getPostTitle());
        return Optional.empty();
    }
}
