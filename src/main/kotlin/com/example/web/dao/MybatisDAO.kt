package com.example.web.dao

import com.example.web.mapper.MybatisMapper
import com.example.web.to.BoardTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class MybatisDAO {
    @Autowired
    lateinit var mybatisMapper: MybatisMapper;


    fun select(): String {
        return mybatisMapper.select();
    }

    // 게시글 목록 조회
    fun boardList(): List<BoardTO> {
        println("boardList 호출")
        return mybatisMapper.boardList()
    }

    // 게시글 상세 조회
    fun boardView(to: BoardTO): BoardTO? {
        println("boardView 호출")
        mybatisMapper.updateHit(to.seq)
        return mybatisMapper.boardView(to.seq)
    }

    // 게시글 작성
    fun boardWriteOk(to: BoardTO): Int {
        println("boardWriteOk 호출")
        val result = mybatisMapper.boardWrite(to)
        return result;
    }

    // 게시글 수정을 위한 조회
    fun boardModify(to: BoardTO): BoardTO? {
        println("boardModify 호출")
        return mybatisMapper.boardModifyView(to.seq)
    }

    // 게시글 수정
    fun boardModifyOk(to: BoardTO): Int {
        println("boardModifyOk 호출")
        return mybatisMapper.boardModify(to)
    }

    // 게시글 삭제를 위한 조회
    fun boardDelete(to: BoardTO): BoardTO? {
        println("boardDelete 호출")
        return mybatisMapper.boardDeleteView(to.seq)
    }

    // 게시글 삭제
    fun boardDeleteOk(to: BoardTO): Int {
        println("boardDeleteOk 호출")
        return mybatisMapper.boardDelete(to)
    }
}