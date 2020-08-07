package com.mobeom.local_currency.post;


import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
interface PostService {

    Optional<NoticeVO> findOne(long postId);
}

@Service
public class PostServiceImpl implements PostService {
    @Autowired PostRepository postRepository;

    @Override
    public Optional<NoticeVO> findOne(long postId) {
        NoticeVO findOne = postRepository.findByPostId(postId);
        return Optional.of(findOne);
    }
}
