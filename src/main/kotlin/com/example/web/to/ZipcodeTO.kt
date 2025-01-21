package com.example.web.to

data class ZipcodeTO(
    val zipcode: String,     // 우편번호: 135-549
    val sido: String,        // 시도: 서울
    val gugun: String,       // 구군: 강남구
    val dong: String,        // 동: 대치2동
    val building: String?,    // 건물명: 코스모타워
    val detail: String?,     // 상세주소: null
    val no: String?          // 번호: 166
)