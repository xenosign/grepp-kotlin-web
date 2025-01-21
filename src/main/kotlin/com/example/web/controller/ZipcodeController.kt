package com.example.web.controller

import com.example.web.service.ZipcodeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/zipcode")
class ZipcodeController {
    @Autowired
    lateinit var zipcodeService: ZipcodeService;

    @GetMapping("")
    fun index(): String {
        return "zipcode"
    }

    @GetMapping("/search")
    fun result(dong: String, model: Model): String {
        if (!dong.isNullOrBlank()) {
            // "zipcodes"라는 이름으로 모델에 데이터 추가
            model.addAttribute("zipcodes", zipcodeService.findByZipcode(dong))
        }
        return "zipcode"
    }

    @GetMapping("/dump")
    fun dump() {
        zipcodeService.loadCsvToDatabase("./zipcode2.csv");
    }
}