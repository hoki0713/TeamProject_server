package com.mobeom.local_currency.board;

import org.springframework.data.jpa.repository.JpaRepository;

interface BoardRepository extends JpaRepository<Board, Long>, CustomBoardRepository{

}
