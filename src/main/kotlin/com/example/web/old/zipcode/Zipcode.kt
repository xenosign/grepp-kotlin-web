package com.example.web.old.zipcode

import org.springframework.stereotype.Service
import java.io.File
import java.io.BufferedReader
import java.io.FileReader

data class Address(
    val zipCode: String,
    val city: String,
    val district: String,
    val dong: String,
    val apartment: String,
    val empty: String,
    val sequence: String
)

// 검색 결과 출력을 위한 확장 함수
fun List<Address>.prettyPrint() {
    if (isEmpty()) {
        println("검색 결과가 없습니다.")
        return
    }

    forEach { address ->
        println("""
                |우편번호: ${address.zipCode}
                |주소: ${address.city} ${address.district} ${address.dong}
                |아파트: ${address.apartment}
                |----------------------------------------
            """.trimMargin())
    }
}

@Service
class AddressSearchService {
    // CSV 파일 읽기 및 데이터 저장
    private val addresses = mutableListOf<Address>()

    init {
        loadAddressData()
    }

    private fun loadAddressData() {
        val file = File("./zipcode2.csv")
        BufferedReader(FileReader(file)).use { reader ->
            reader.lineSequence()
                .map { line ->
                    val parts = line.split(",")
                    Address(
                        zipCode = parts[0],
                        city = parts[1],
                        district = parts[2],
                        dong = parts[3],
                        apartment = parts[4],
                        empty = parts[5],
                        sequence = parts[6]
                    )
                }
                .forEach { addresses.add(it) }
        }
    }

    // 동 이름으로 검색
    fun searchByDong(dongName: String): List<Address> {
        return addresses.filter {
            it.dong.contains(dongName, ignoreCase = true)
        }
    }
}

// 사용 예시
fun main() {
    val searchService = AddressSearchService()

    print("검색할 동 이름을 입력하세요 : ")
    val dongName = readLine() ?: return

    val results = searchService.searchByDong(dongName)
    results.prettyPrint()
}