package com.example.web.controller

import com.example.web.entity.Board
import com.example.web.repository.BoardRepository
import com.example.web.to.BoardTO
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/jpa")
class BoardJpaController(private val boardRepository: BoardRepository) {
    @GetMapping("/list")
    fun list(model: Model): String {
        val boardLists = boardRepository.findAll();
        model.addAttribute("lists", boardLists);
        return "board/board_list_jpa";
    }

    @GetMapping("/view")
    fun view(@RequestParam seq: Long, model: Model): String {  // String -> Long으로 변경
        val board = boardRepository.findById(seq)
            .orElseThrow { NoSuchElementException("게시글을 찾을 수 없습니다.") }

        model.addAttribute("to", board)
        return "board/board_view_jpa"
    }

    @GetMapping("/write")
    fun write(): String {  // String -> Long으로 변경
        return "board/board_write1_jpa"
    }

    @PostMapping("/write")
    fun writeOk(@ModelAttribute board: Board, model: Model): String {
        val flag = try {
            val result = boardRepository.save(board);

            if(result.seq > 0) 1 else 0;
        } catch(e: Exception) {
            println("저장 실패: ${e.message}");

            0;
        }

        model.addAttribute("flag", flag);
        return "board/board_write1_ok_jpa"
    }

    @GetMapping("/modify")
    fun modify(@RequestParam seq: Long, model: Model): String {
        var board = boardRepository.findById(seq)
            .orElseThrow { NoSuchElementException("게시글을 찾을 수 없습니다.") };

        val emailParts = if (board.mail.isNullOrEmpty()) {
            listOf("", "")
        } else {
            board.mail!!.split("@")
        }

        model.addAttribute("board", board)
        model.addAttribute("mail1", emailParts[0])
        model.addAttribute("mail2", if (emailParts.size > 1) emailParts[1] else "")


        model.addAttribute("board", board);
        return "board/board_modify1_jpa"
    }

    @PostMapping("/modify")
    fun modifyOk(@ModelAttribute board: Board, model: Model): String {
        val flag = try {
            val result = boardRepository.save(board)
            if (result.seq > 0) 0 else 2  // 성공: 0, 실패: 2
        } catch (e: Exception) {
            println("수정 실패: ${e.message}")
            2  // 실패
        }

        model.addAttribute("flag", flag)
        model.addAttribute("seq", board.seq)
        return "board/board_modify1_ok_jpa"
    }

    @GetMapping("/delete")
    fun delete(@RequestParam seq: Long, model: Model): String {
        val board = boardRepository.findById(seq)
            .orElseThrow { NoSuchElementException("게시글을 찾을 수 없습니다.") }
        model.addAttribute("board", board)
        return "board/board_delete1_jpa"
    }

    @PostMapping("/delete")
    fun deleteOk(@ModelAttribute board: Board, model: Model): String {
        val flag = try {
            // 비밀번호 검증 로직이 필요하다면 여기서 추가
            boardRepository.delete(board)
            0  // 삭제 성공
        } catch (e: Exception) {
            println("삭제 실패: ${e.message}")
            2  // 삭제 실패
        }

        model.addAttribute("flag", flag)
        return "board/board_delete1_ok_jpa"
    }
}