package com.example.web.Ex

import org.mariadb.jdbc.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet

fun main( args:Array<String> ){
    var url: String = "jdbc:mariadb://localhost:3306/mysql"
    var user: String = "root"
    var password: String = "!123456"

    // Connection - PreparedStatement, Result
    lateinit var conn: Connection
    lateinit var pstmt: PreparedStatement
    lateinit var rs: ResultSet

    Class.forName("org.mariadb.jdbc.Driver")
    conn = DriverManager.getConnection(url, user, password) as Connection;
    println("conn: $conn")

    var sql = "select version()"
    pstmt = conn.prepareStatement(sql)

    rs = pstmt.executeQuery()
    if (rs.next()) {
        println(rs.getString("version()"))
    }

    rs.close()
    pstmt.close()
    conn.close()

}