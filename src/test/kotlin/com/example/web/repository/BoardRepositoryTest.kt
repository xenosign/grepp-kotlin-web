package com.example.web.repository

import com.example.web.entity.Board
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BoardRepositoryTest: BehaviorSpec({
    val boardRepository = mockk<BoardRepository>();

    Given("UserRepository가 주어졌을 때") {
        val board = Board(0, "test", "test", "test@test.com", "test", "test", 0)

        When("제목으로 사용자를 찾으면") {
            val boards = listOf(board)
            every { boardRepository.findBySubjectContaining("test") } returns boards

            Then("일치하는 게시글 목록이 반환되어야 한다") {
                val found = boardRepository.findBySubjectContaining("test")
                found shouldHaveSize 1
                found shouldContain board
                verify { boardRepository.findBySubjectContaining("test") }
            }
        }

        When("이메일로 게시글을 찾으면") {
            every { boardRepository.findByMail("test@test.com") } returns board

            Then("해당 게시글이 반환되어야 한다") {
                val found = boardRepository.findByMail("test@test.com")
                found shouldBe board
                verify { boardRepository.findByMail("test@test.com") }
            }
        }

        When("존재하지 않는 이메일로 검색하면") {
            every { boardRepository.findByMail("notfound@test.com") } returns null

            Then("null이 반환되어야 한다") {
                val found = boardRepository.findByMail("notfound@test.com")
                found shouldBe null
                verify { boardRepository.findByMail("notfound@test.com") }
            }
        }
    }
}) {

}