package com.mobeom.local_currency.post;

import com.mobeom.local_currency.board.Board;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
interface PostRepository extends JpaRepository<Post, Long>, CustomedPostRepository {}

interface CustomedPostRepository{}

public class PostRepositoryImpl extends QuerydslRepositorySupport implements CustomedPostRepository {

    public PostRepositoryImpl() {
        super(Post.class);
    }
    @Autowired
    JPAQueryFactory query;


}
