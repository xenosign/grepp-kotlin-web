package com.example.web.controller

import com.example.web.dao.MybatisDAO
import com.example.web.to.BoardTO
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/mybatis")
class BoardMybatisController(var mybatisDAO: MybatisDAO) {

    @GetMapping("/list")
    fun list(model: Model): String {
        val boardList = mybatisDAO.boardList();
        println(boardList);
        model.addAttribute("lists", boardList);
        return "board/board_list1"
    }

    @GetMapping("/view")
    fun view(seq: String, model: Model): String {
        val to = BoardTO();
        to.seq = seq;

        val board = mybatisDAO.boardView(to);
        model.addAttribute("to", board);

        return "board/board_view1"
    }

    @GetMapping("/write")
    fun write(): String {
        return "board/board_write1"
    }

    @PostMapping("/write")
    fun writeOk(@ModelAttribute boardTO: BoardTO, model: Model): String {
        val result = mybatisDAO.boardWriteOk(boardTO);
        println(result)
        model.addAttribute("flag", result);
        return "board/board_write1_ok"
    }

    @GetMapping("/modify")
    fun modify(model: Model): String {
        println("/modify 호출")
        // ModifyAction 로직 구현
        return "board/board_modify1"
    }

    @PostMapping("/modify")
    fun modifySubmit(): String {
        println("/modify submit 호출")
        // ModifyOkAction 로직 구현
        return "board/board_modify1_ok"
    }

    @GetMapping("/delete")
    fun delete(): String {
        println("/delete 호출")
        // DeleteAction 로직 구현
        return "board/board_delete1"
    }

    @PostMapping("/delete")
    fun deleteSubmit(): String {
        println("/delete submit 호출")
        // DeleteOkAction 로직 구현
        return "board/board_delete1_ok"
    }
}
