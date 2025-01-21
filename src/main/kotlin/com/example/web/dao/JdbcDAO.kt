package com.example.web.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class JdbcDAO {
    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate;

    fun jdbc() {
        println(jdbcTemplate);

        val result = jdbcTemplate.queryForObject("SELECT NOW()", String::class.java);
        println(result)
    }
}