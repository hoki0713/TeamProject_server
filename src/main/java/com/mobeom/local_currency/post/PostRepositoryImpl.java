package com.mobeom.local_currency.post;




import static com.mobeom.local_currency.post.QPost.post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


interface CustomPostRepository {
    Post findByPostId(long postId);
    List<Post> inquiryList();
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



    @Override
    public List<Post> inquiryList() {
        List<Post> resultList = query.selectFrom(post).where(post.category.like("%"+"문의"+"%")).fetch();
        return resultList;
    }


}