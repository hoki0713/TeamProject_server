package com.mobeom.local_currency.post;

import static com.mobeom.local_currency.post.QPost.post;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.*;


interface CustomPostRepository {

    List<Post> inquiryList();

    List<Post> postList();

    List<Post> noticeSearch(String searchWord, String category);

    List<Post> findAllPostsByUserIdAndBoardId(long userId, long boardId);

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
    public List<Post> inquiryList() {
        List<Post> resultList = queryFactory.selectFrom(post).where(post.category.like("%" + "문의" + "%")).fetch();

        return resultList;
    }

    @Override

    public List<Post> postList() {
        List<Post> list = queryFactory.select(Projections.fields(Post.class, post.postTitle,
                post.category, post.comment, post.contents, post.postId, post.regDate, post.readCount)).from(post)
                .where(post.noticeYn.eq(true).and(post.deleteYn.isFalse())).orderBy(post.postId.desc()).fetch();
        return list;
    }

    @Override
    public List<Post> noticeSearch(String searchWord, String category) {
        List<Post> result = null;
        if (searchWord.equals("")) {
            result = queryFactory.select(post).from(post).where(post.category.like("%" + category + "%")
                    .and(post.noticeYn.eq(true).and(post.deleteYn.isFalse()))).fetch();
        } else if (category.equals("")) {
            result = queryFactory.select(Projections.fields(Post.class, post.postTitle,
                    post.category, post.comment, post.contents, post.postId, post.regDate, post.readCount)).from(post)
                    .where(post.noticeYn.eq(true).and(post.deleteYn.isFalse())).fetch();
        } else {
            result = queryFactory.select(post).from(post).where(post.category.like("%" + category + "%")
                    .and(post.noticeYn.eq(true).and(post.deleteYn.isFalse()))
                    .and(post.contents.like("%" + searchWord + "%").or(post.postTitle.like("%" + searchWord + "%"))))
                    .fetch();
        }

        return result;
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