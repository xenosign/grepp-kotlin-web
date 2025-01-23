package com.example.web.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate


@Service
class KakaoAuthServiceImpl(private val restTemplate: RestTemplate): KakaoAuthService{
    private val KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token"
    private val KAKAO_MESSAGE_URL = "https://kapi.kakao.com/v2/api/talk/memo/default/send"
    private val CLIENT_ID = ""
    private val REDIRECT_URI = "http://localhost:8080/kakao/callback"

    override fun getAccessToken(code: String): String {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
        }

        val params = LinkedMultiValueMap<String, String>().apply {
            add("grant_type", "authorization_code")
            add("client_id", CLIENT_ID)
            add("redirect_uri", REDIRECT_URI)
            add("code", code)
        }

        val request = HttpEntity(params, headers)

        val response = restTemplate.postForObject(
            KAKAO_TOKEN_URL,
            request,
            KakaoTokenResponse::class.java
        ) ?: throw RuntimeException("Failed to get access token")

        return response.access_token
    }

    override fun sendKakaoMessage(accessToken: String) {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
            set("Authorization", "Bearer $accessToken")
        }

        val messageTemplate = mapOf(
            "object_type" to "text",
            "text" to "카카오톡 메시지 테스트입니다.",
            "link" to mapOf(
                "web_url" to "https://developers.kakao.com",
                "mobile_web_url" to "https://developers.kakao.com"
            ),
            "button_title" to "자세히 보기"
        )

        val body = LinkedMultiValueMap<String, String>().apply {
            add("template_object", ObjectMapper().writeValueAsString(messageTemplate))
        }

        val request = HttpEntity(body, headers)

        println("WORK!!")

        val response = restTemplate.exchange(
            KAKAO_MESSAGE_URL,
            HttpMethod.POST,
            request,
            String::class.java
        )

        println("Response: ${response.body}")
    }
}

data class KakaoTokenResponse(
    val access_token: String,
    val token_type: String,
    val refresh_token: String,
    val expires_in: Int,
    val scope: String,
    val refresh_token_expires_in: Int
)