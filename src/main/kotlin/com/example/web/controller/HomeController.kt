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
@RequestMapping("/")
class HomeController {
    @GetMapping("/")
    fun index(): String {
        return "index";
    }

    @GetMapping("/get1")
    fun get1(req:HttpServletRequest): String {
        println(req.getParameter("data"));
        return "index";
    }

    @GetMapping("/get2")
    fun get2(@RequestParam(name = "data") data: String): String {
        println(data);
        return "index";
    }

    @GetMapping("/get3")
    fun get3(data: String): String {
        println(data);
        return "index";
    }

    @GetMapping("/get4/{data}")
    fun get4(@PathVariable data: String): String {
        println(data);
        return "index";
    }

    @GetMapping("/set1")
    fun set4(req: HttpServletRequest): String {
        req.setAttribute("data", req.getParameter("data"));
        return "index";
    }

    @GetMapping("/set2")
    fun set2(@RequestParam(name = "data") data: String, model: Model): String {
        model.addAttribute("data", data);
        return "index";
    }

    @GetMapping("/set3")
    fun set3(data: String): ModelAndView {
        var modelAndView = ModelAndView();
        modelAndView.viewName = "index";
        modelAndView.addObject("data", data);
        return modelAndView;
    }

}