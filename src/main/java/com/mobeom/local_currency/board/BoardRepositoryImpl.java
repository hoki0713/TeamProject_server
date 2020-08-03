package com.mobeom.local_currency.board;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
interface BoardRepository extends JpaRepository<Board, Long>, CustomBoardRepository{}

interface CustomBoardRepository{}

public class BoardRepositoryImpl extends QuerydslRepositorySupport implements CustomBoardRepository {

    public BoardRepositoryImpl() {
        super(Board.class);
    }
    @Autowired
    JPAQueryFactory query;


}
