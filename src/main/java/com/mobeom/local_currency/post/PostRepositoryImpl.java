package com.mobeom.local_currency.post;

import static com.mobeom.local_currency.post.QPost.post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;


interface CustomPostRepository {
    Post findByPostId(long postId);
    List<Post> inquiryList();

    List<Post> findAllReviewsByUserIdAndBoardId(long userId, long boardId);
}

@Repository
public class PostRepositoryImpl extends QuerydslRepositorySupport implements CustomPostRepository {

    private final JPAQueryFactory queryFactory;


    public PostRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Post.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Post findByPostId(long postId) {
        Post findOne = queryFactory.selectFrom(post).where(post.postId.eq(postId)).fetchOne();
        return findOne;
    }


    @Override
    public List<Post> inquiryList() {
        List<Post> resultList = queryFactory.selectFrom(post).where(post.category.like("%"+"문의"+"%")).fetch();
        return resultList;
    }

    @Override
    public List<Post> findAllReviewsByUserIdAndBoardId(long userId, long boardId) {
        List<Post> resultList =
                queryFactory
                        .selectFrom(post)
                        .where(post.board.boardId.eq(boardId), post.user.id.eq(userId))
                        .fetch();
        return resultList;
    }


}