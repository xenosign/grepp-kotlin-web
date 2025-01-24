package com.example.web.repository

import com.example.web.entity.Board
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardRepository: JpaRepository<Board, Long> {
    fun findBySubjectContaining(title: String): List<Board>  // 부분 문자열 포함
    fun findByMail(title: String): Board?  // 정확히 일치하는 항목
}