package com.mobeom.local_currency.image;

import com.mobeom.local_currency.board.Board;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
interface ImageRepository extends JpaRepository<Image, Long>, CustomedImageRepository {}

interface CustomedImageRepository{}

public class ImageRepositoryImpl extends QuerydslRepositorySupport implements CustomedImageRepository {

    public ImageRepositoryImpl() {
        super(Image.class);
    }
    @Autowired
    JPAQueryFactory query;


}
