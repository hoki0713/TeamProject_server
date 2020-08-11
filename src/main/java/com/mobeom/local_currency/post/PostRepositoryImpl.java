package com.mobeom.local_currency.post;

import static com.mobeom.local_currency.post.QPost.post;
import static com.mobeom.local_currency.user.QUser.user;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


interface CustomPostRepository {
    Post findByPostId(long postId);
    List<Post> inquiryList();
    List<Post> postList();
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
        update(post).set(post.comment,"아이오").where(post.noticeYn.eq(true)).execute();
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


}