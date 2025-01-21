package com.example.web.Ex

import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet

fun main() {
    val url = "jdbc:mysql://localhost:3306/grepp-be3";
    val user = "root";
    val password = "1234";

    // 지연 초기화로 null 넣고 시작하는거 없이 하기
    // var var conn: Connection? = null;
    lateinit var conn: Connection;
    lateinit var pstmt: PreparedStatement;
    lateinit var rs: ResultSet;


    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(url, user, password);
        println("conn : ${conn}");

        val sql = "select version()";
        pstmt = conn.prepareStatement( sql );

        rs = pstmt.executeQuery();

        if (rs.next()) {
            println(rs.getString("version()"));
        }
    } catch (e: Exception) {
        e.printStackTrace();
        println("데이터베이스 연결 실패: ${e.message}");
    } finally {
        conn!!.close();
    }
}