package com.example.web.service

import org.springframework.stereotype.Service

@Service
interface KakaoAuthService {
    fun getAccessToken(code: String): String;
    fun sendKakaoMessage(code: String);
}