package com.mobeom.local_currency.post;




import static com.mobeom.local_currency.post.QPost.post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;


interface CustomPostRepository {
    Post findByPostId(long postId);
    List<NoticeVO> noticeList();
    NoticeVO update(String id,Post post);

}

@Repository
public class PostRepositoryImpl extends QuerydslRepositorySupport implements CustomPostRepository {

    private final JPAQueryFactory query;


    public PostRepositoryImpl(JPAQueryFactory query) {
        super(Post.class);
        this.query = query;
    }

    @Override
    public Post findByPostId(long postId) {

        Post findOne = query.selectFrom(post).where(post.postId.eq(postId)).fetchOne();

        return findOne;
    }

    public List<NoticeVO> noticeList(){

        List<Post> list = query.select(post).from(post).fetch();

        // List<NoticeVO> resultList = list.
        return null;
    }

    @Override
    public NoticeVO update(String id, Post post) {



        return null;

    }


}