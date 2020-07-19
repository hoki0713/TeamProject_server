package com.mobeom.local_currency.board;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
interface BoardRepository extends JpaRepository<Board, Long>, CustomedBoardRepository{}

interface CustomedBoardRepository{}

public class BoardRepositoryImpl extends QuerydslRepositorySupport implements CustomedBoardRepository {

    public BoardRepositoryImpl() {
        super(Board.class);
    }
    @Autowired
    JPAQueryFactory query;


}
