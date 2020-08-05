package com.mobeom.local_currency.post;


import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
interface CustomPostRepository {

}

@Component
public class PostRepositoryImpl extends QuerydslRepositorySupport implements CustomPostRepository {

    public PostRepositoryImpl() {
        super(Post.class);
    }


    public List<Post> findAll(){
        return null;
    }




}
