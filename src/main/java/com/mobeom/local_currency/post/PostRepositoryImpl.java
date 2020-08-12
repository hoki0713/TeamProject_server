package com.mobeom.local_currency.post;

import static com.mobeom.local_currency.post.QPost.post;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


interface CustomPostRepository {
    Post findByPostId(long postId);
    List<Post> inquiryList();
    List<Post> postList();
    List<Post> findAllPostsByUserIdAndBoardId(long userId, long boardId);
    Post findOnePostByReviewId(long reviewId);

    List<Post> findAllByBoardId(long boardId);

    List<Post> findAllByBoardIdAndCommentYn(long boardId, String selectedOption);

    List<Post> findAllByBoardIdCommentYnSearchWord(long boardId, String selectedOption, String searchWord);
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

    public List<Post> postList() {
  // List<Post> list= queryFactory.select(Projections.fields(post,user.userId,user.name)).from(post).innerJoin(user).fetch();
        List<Post> list= queryFactory.select(Projections.fields(Post.class,post.postTitle,
                post.category,post.comment,post.contents,post.postId,post.regDate,post.readCount)).from(post)
                .where(post.noticeYn.eq(true)).fetch();
    Map<String,List<Post>> test = new HashMap<>();
    test.put("postList",list);
        return list;
    }

    public List<Post> findAllPostsByUserIdAndBoardId(long userId, long boardId) {
        List<Post> resultList =
                queryFactory
                        .selectFrom(post)
                        .where(post.board.boardId.eq(boardId), post.user.id.eq(userId))
                        .fetch();
        return resultList;
    }

    @Override
    public Post findOnePostByReviewId(long reviewId) {
        return queryFactory.selectFrom(post).where(post.postId.eq(reviewId)).fetchOne();
    }

    @Override
    public List<Post> findAllByBoardId(long boardId) {
        List<Post> resultList = queryFactory.selectFrom(post).where(post.board.boardId.eq(boardId)).fetch();
        return resultList;
    }

    @Override
    public List<Post> findAllByBoardIdAndCommentYn(long boardId, String selectedOption) {
        if (selectedOption.equals("solved")) {
            return queryFactory.selectFrom(post).where(post.board.boardId.eq(boardId), post.comment.isNotNull()).fetch();
        } else {
            return queryFactory.selectFrom(post).where(post.board.boardId.eq(boardId), post.comment.isNull()).fetch();
        }
    }

    @Override
    public List<Post> findAllByBoardIdCommentYnSearchWord(long boardId, String selectedOption, String searchWord) {
        if (selectedOption.equals("solved")) {
            return queryFactory
                    .selectFrom(post)
                    .where(
                            post.board.boardId.eq(boardId),
                            post.comment.isNotNull(),
                            post.user.name.eq(searchWord)
                    ).fetch();
        } else {
            return queryFactory
                    .selectFrom(post)
                    .where(
                            post.board.boardId.eq(boardId),
                            post.comment.isNull(),
                            post.user.name.eq(searchWord)
                    ).fetch();
        }
    }


}