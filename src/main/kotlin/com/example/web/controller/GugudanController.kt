package com.example.web.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/gugudan")
class GugudanController {
    @GetMapping("/{dan}")
    fun gugudan(@PathVariable dan: Int, model: Model): String {
        model.addAttribute("dan", dan);
        return "gugudan";
    }
}