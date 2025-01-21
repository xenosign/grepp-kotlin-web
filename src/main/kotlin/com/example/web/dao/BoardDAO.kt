package com.example.web.dao

import com.example.web.to.BoardTO
import org.springframework.stereotype.Repository
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import javax.naming.Context
import javax.naming.InitialContext
import javax.naming.NamingException
import javax.sql.DataSource

@Repository
class BoardDAO(var dataSource: DataSource) {
    // DataSource 생성
    init {
        try {
            val initCtx: Context = InitialContext()
            val envCtx = initCtx.lookup("java:comp/env") as Context
            this.dataSource = envCtx.lookup("jdbc/mysql") as DataSource
        } catch (e: NamingException) {
            println("[에러] " + e.message)
        }
    }

    // board_list1.jsp의 데이터 처리
    fun boardList(): ArrayList<BoardTO> {
        println("boardList 호출")

        var conn: Connection? = null
        var pstmt: PreparedStatement? = null
        var rs: ResultSet? = null

        val lists: ArrayList<BoardTO> = ArrayList<BoardTO>()

        try {
            conn = dataSource!!.connection

            val sql =
                "select seq, subject, writer, date_format( wdate, '%Y/%m/%d' ) wdate, hit, datediff( now(), wdate ) wgap from board1 order by seq desc"
            pstmt = conn.prepareStatement(sql)

            rs = pstmt.executeQuery()

            while (rs.next()) {
                val to = BoardTO(
                    seq = rs.getString("seq"),
                    subject = rs.getString("subject"),
                    writer = rs.getString("writer"),
                    wdate = rs.getString("wdate"),
                    hit = rs.getString("hit"),
                    wgap = rs.getInt("wgap")
                )

                lists.add(to)
            }
        } catch (e: SQLException) {
            println("[에러] " + e.message)
        } finally {
            if (rs != null) try {
                rs.close()
            } catch (e: SQLException) {
            }
            if (pstmt != null) try {
                pstmt.close()
            } catch (e: SQLException) {
            }
            if (conn != null) try {
                conn.close()
            } catch (e: SQLException) {
            }
        }
        return lists
    }

    // board_view1.jsp의 데이터 처리
    fun boardView(to: BoardTO): BoardTO {
        println("boardView 호출")

        var conn: Connection? = null
        var pstmt: PreparedStatement? = null
        var rs: ResultSet? = null

        try {
            conn = dataSource!!.connection

            var sql = "update board1 set hit=hit+1 where seq=?"
            pstmt = conn.prepareStatement(sql)
            pstmt.setString(1, to.seq)  // getSeq() -> seq

            pstmt.executeUpdate()

            sql = "select subject, writer, mail, wip, wdate, hit, content from board1 where seq=?"
            pstmt = conn.prepareStatement(sql)
            pstmt.setString(1, to.seq)

            rs = pstmt.executeQuery()

            if (rs.next()) {
                to.subject = rs.getString("subject")  // setSubject() -> subject
                to.writer = rs.getString("writer")
                to.mail = rs.getString("mail")
                to.wip = rs.getString("wip")
                to.wdate = rs.getString("wdate")
                to.hit = rs.getString("hit")
                to.content = rs.getString("content").replace("\n".toRegex(), "<br />")
            }
        } catch (e: SQLException) {
            println("[에러] " + e.message)
        } finally {
            if (rs != null) try {
                rs.close()
            } catch (e: SQLException) {
            }
            if (pstmt != null) try {
                pstmt.close()
            } catch (e: SQLException) {
            }
            if (conn != null) try {
                conn.close()
            } catch (e: SQLException) {
            }
        }
        return to
    }

    fun boardWrite() {}

    fun boardWriteOk(to: BoardTO): Int {
        println("boardWriteOk 호출")

        var conn: Connection? = null
        var pstmt: PreparedStatement? = null

        var flag = 1

        try {
            conn = dataSource!!.connection

            val sql = "insert into board1 values ( 0, ?, ?, ?, SHA2(?, 256), ?, 0, ?, now() )"
            pstmt = conn.prepareStatement(sql)
            pstmt.setString(1, to.subject)
            pstmt.setString(2, to.writer)
            pstmt.setString(3, to.mail)
            pstmt.setString(4, to.password)
            pstmt.setString(5, to.content)
            pstmt.setString(6, to.wip)

            val result = pstmt.executeUpdate()
            if (result == 1) {
                flag = 0
            }
        } catch (e: SQLException) {
            println("[에러] " + e.message)
        } finally {
            if (pstmt != null) try {
                pstmt.close()
            } catch (e: SQLException) {
            }
            if (conn != null) try {
                conn.close()
            } catch (e: SQLException) {
            }
        }
        return flag
    }

    fun boardModify(to: BoardTO): BoardTO {
        println("boardModify 호출")

        var conn: Connection? = null
        var pstmt: PreparedStatement? = null
        var rs: ResultSet? = null

        // boardModify 메서드 부분
        try {
            conn = dataSource!!.connection

            val sql = "select subject, writer, mail, content from board1 where seq=?"
            pstmt = conn.prepareStatement(sql)
            pstmt.setString(1, to.seq)

            rs = pstmt.executeQuery()

            if (rs.next()) {
                to.subject = rs.getString("subject")
                to.writer = rs.getString("writer")
                to.mail = rs.getString("mail")
                to.content = rs.getString("content")
            }
        } catch (e: SQLException) {
            println("[에러] " + e.message)
        } finally {
            if (rs != null) try {
                rs.close()
            } catch (e: SQLException) {
            }
            if (pstmt != null) try {
                pstmt.close()
            } catch (e: SQLException) {
            }
            if (conn != null) try {
                conn.close()
            } catch (e: SQLException) {
            }
        }
        return to
    }

    fun boardModifyOk(to: BoardTO): Int {
        println("boardModifyOk 호출")

        var conn: Connection? = null
        var pstmt: PreparedStatement? = null

        var flag = 2

        try {
            conn = dataSource!!.connection

            // password() 함수를 SHA2로 변경
            val sql = "update board1 set subject=?, mail=?, content=? where seq=? and password=SHA2(?, 256)"
            pstmt = conn.prepareStatement(sql)
            pstmt.setString(1, to.subject)
            pstmt.setString(2, to.mail)
            pstmt.setString(3, to.content)
            pstmt.setString(4, to.seq)
            pstmt.setString(5, to.password)

            val result = pstmt.executeUpdate()
            if (result == 0) {
                flag = 1
            } else if (result == 1) {
                flag = 0
            }
        } catch (e: SQLException) {
            println("[에러] " + e.message)
        } finally {
            if (pstmt != null) try {
                pstmt.close()
            } catch (e: SQLException) {
            }
            if (conn != null) try {
                conn.close()
            } catch (e: SQLException) {
            }
        }
        return flag
    }

    fun boardDelete(to: BoardTO): BoardTO {
        println("boardDelete 호출")

        var conn: Connection? = null
        var pstmt: PreparedStatement? = null
        var rs: ResultSet? = null

        try {
            conn = dataSource!!.connection

            val sql = "select subject, writer from board1 where seq=?"
            pstmt = conn.prepareStatement(sql)
            pstmt.setString(1, to.seq)

            rs = pstmt.executeQuery()

            if (rs.next()) {
                to.subject = rs.getString("subject")
                to.writer = rs.getString("writer")
            }
        } catch (e: SQLException) {
            println("[에러] " + e.message)
        } finally {
            if (rs != null) try {
                rs.close()
            } catch (e: SQLException) {
            }
            if (pstmt != null) try {
                pstmt.close()
            } catch (e: SQLException) {
            }
            if (conn != null) try {
                conn.close()
            } catch (e: SQLException) {
            }
        }
        return to
    }

    fun boardDeleteOk(to: BoardTO): Int {
        println("boardDeleteOk 호출")

        var conn: Connection? = null
        var pstmt: PreparedStatement? = null

        var flag = 2

        try {
            conn = dataSource!!.connection

            val sql = "delete from board1 where seq=? and password=password(?)"
            pstmt = conn.prepareStatement(sql)
            pstmt.setString(1, to.seq)
            pstmt.setString(2, to.password)

            val result = pstmt.executeUpdate()
            if (result == 0) {
                flag = 1
            } else if (result == 1) {
                flag = 0
            }
        } catch (e: SQLException) {
            println("[에러] " + e.message)
        } finally {
            if (pstmt != null) try {
                pstmt.close()
            } catch (e: SQLException) {
            }
            if (conn != null) try {
                conn.close()
            } catch (e: SQLException) {
            }
        }
        return flag
    }
}