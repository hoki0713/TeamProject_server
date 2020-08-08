package com.mobeom.local_currency.post;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


interface CustomPostRepository {
    NoticeVO findByPostId(long postId);
}

@Repository
public class PostRepositoryImpl extends QuerydslRepositorySupport implements CustomPostRepository {
    @Autowired
    JPAQueryFactory queryFactory;


    public PostRepositoryImpl() {
        super(Post.class);
    }

    @Override
    public NoticeVO findByPostId(long postId) {
        QPost post = QPost.post;
        Post findOne = queryFactory.selectFrom(post).where(post.postId.eq(postId)).fetchOne();
        //int postId, String category, String postTitle, LocalDate regDate, int readCount
        NoticeVO resultVO = new NoticeVO(
                findOne.getPostId(),
                findOne.getCategory(),
                findOne.getPostTitle(),
                findOne.getRegDate(),
                findOne.getReadCount(),
                findOne.getContents()
        );
        return resultVO;
    }


    public List<NoticeVO> noticeList(){
        QPost post = QPost.post;
        List<Post> list = queryFactory.select(post).from(post).fetch();

        // List<NoticeVO> resultList = list.
        return null;
    }
}