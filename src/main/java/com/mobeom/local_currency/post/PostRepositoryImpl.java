package com.mobeom.local_currency.post;

import com.mobeom.local_currency.board.Board;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

interface CustomPostRepository {
    List<Post> List(String searchWord);
}

@Repository
public class PostRepositoryImpl extends QuerydslRepositorySupport implements CustomPostRepository {
    @Autowired
    JPAQueryFactory queryFactory;

    PostRepositoryImpl() {
        super(Post.class);
    }

    @Override
    public List<Post> List(String searchWord) {
        QPost post = QPost.post;
        List<Post> list = new ArrayList<>();
        if(!searchWord.equals("null")) {
            list =query.select(Projections.fields(Post.class, post.category, post.postTitle, post.user.id, post.regDate, post.readCount)).from(post)
                    .where(post.postTitle.like("%"+searchWord+"%")).fetch();
        } else{
            list =query.select(Projections.fields(Post.class, post.category, post.postTitle, post.user.id, post.regDate, post.readCount)).from(post)
                    .fetch();
        }
        return list;

    }
}
