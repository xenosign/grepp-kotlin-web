package com.example.web.service

import com.example.web.to.ZipcodeTO
import org.springframework.stereotype.Service

@Service
interface ZipcodeService {
    fun findByZipcode(findDong: String): List<ZipcodeTO>;
    fun findByZipcodeCSV(findDong: String): List<ZipcodeTO>;
    fun loadCsvToDatabase(csvFilePath: String);
}