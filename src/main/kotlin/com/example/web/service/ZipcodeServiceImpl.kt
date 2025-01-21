package com.example.web.service

import com.example.web.to.ZipcodeTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.io.*

@Service
class ZipcodeServiceImpl: ZipcodeService {
    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate;

    override fun findByZipcode(findDong: String): List<ZipcodeTO> {
        val sql = """
           SELECT zipcode, sido, gugun, dong, building, detail, no 
           FROM zipcode 
           WHERE dong LIKE ?
       """.trimIndent()

        return jdbcTemplate.query(sql, { rs, _ ->
            ZipcodeTO(
                zipcode = rs.getString("zipcode"),
                sido = rs.getString("sido"),
                gugun = rs.getString("gugun"),
                dong = rs.getString("dong"),
                building = rs.getString("building"),
                detail = rs.getString("detail"),
                no = rs.getString("no")
            )
        }, "%$findDong%")
    }


    override fun findByZipcodeCSV(findDong: String): List<ZipcodeTO> {
        val zipcodeTOS = mutableListOf<ZipcodeTO>()

        val file = File("./zipcode2.csv")
        BufferedReader(FileReader(file)).use { reader ->
            reader.lineSequence()
                .map { line ->
                    val parts = line.split(",")
                    ZipcodeTO(
                        zipcode = parts[0],
                        sido = parts[1],
                        gugun = parts[2],
                        dong = parts[3],
                        building = parts[4],
                        detail = parts[5],
                        no = parts[6]
                    )
                }
                .forEach { zipcodeTOS.add(it) }
        }

        return zipcodeTOS.filter {
            it.dong.contains(findDong, ignoreCase = true)
        }
    }

    override fun loadCsvToDatabase(csvFilePath: String) {
        val resource = ClassPathResource("zipcode2.csv")
        val reader = BufferedReader(InputStreamReader(resource.inputStream))

        val insertSql = """
            INSERT INTO zipcode (zipcode, sido, gugun, dong, building, detail, no) 
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """.trimIndent()

        reader.useLines { lines ->
            lines.forEach { line ->
                try {
                    val parts = line.split(",")
                    jdbcTemplate.update(insertSql,
                        parts[0],                                  // zipcode
                        parts[1],                                  // sido
                        parts[2],                                  // gugun
                        parts[3],                                  // dong
                        parts[4].takeIf { it.isNotBlank() },      // building
                        parts[5].takeIf { it.isNotBlank() },      // detail
                        parts[6].takeIf { it.isNotBlank() }       // no
                    )
                } catch (e: Exception) {
                    println("Error processing line: $line")
                    println("Error: ${e.message}")
                }
            }
        }
    }
}