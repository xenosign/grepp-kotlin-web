package com.example.web.controller

import com.example.web.dao.JdbcDAO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.sql.DataSource

@Controller
@RequestMapping("/jdbc")
class JdbcController {
    @Autowired
    lateinit var dataSource: DataSource;
    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate;
    @Autowired
    lateinit var jdbcDAO: JdbcDAO;

    @GetMapping("/1")
    fun jdbc1(): String{
        println(dataSource);

        return "jdbc1";
    }

    @GetMapping("/2")
    fun jdbc2(): String{
        println(jdbcTemplate);
        return "jdbc1";
    }

    @GetMapping("/3")
    fun jdbc3(): String{
        jdbcDAO.jdbc();
        return "jdbc1";
    }
}