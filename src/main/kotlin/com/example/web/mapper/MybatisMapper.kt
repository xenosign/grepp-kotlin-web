package com.example.web.mapper

import com.example.web.to.BoardTO
import org.apache.ibatis.annotations.Mapper

@Mapper
interface MybatisMapper {
    fun select(): String;

    // 게시글 목록 조회
    fun boardList(): List<BoardTO>;

    // 조회수 증가
    fun updateHit(seq: String);

    // 게시글 상세 조회
    fun boardView(seq: String): BoardTO?;

    // 게시글 작성
    fun boardWrite(board: BoardTO): Int;

    // 게시글 수정을 위한 조회
    fun boardModifyView(seq: String): BoardTO?;

    // 게시글 수정
    fun boardModify(board: BoardTO): Int;

    // 게시글 삭제를 위한 조회
    fun boardDeleteView(seq: String): BoardTO?;

    // 게시글 삭제
    fun boardDelete(board: BoardTO): Int;
}