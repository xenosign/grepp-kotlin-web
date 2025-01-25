package com.example.web.controller

import com.example.web.service.KakaoAuthService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView
import java.net.http.HttpResponse

/**
 * 카카오 OAuth 인증 및 메시지 전송을 처리하는 컨트롤러
 */
@RestController
@RequestMapping("/kakao")
class KakaoController(private val kakaoAuthService: KakaoAuthService) {
    // 카카오 인증 관련 상수
    private val KAKAO_AUTH_REDIRECT_URL = "https://kauth.kakao.com/oauth/authorize"
    private val CLIENT_ID = "" // 카카오 개발자 콘솔에서 발급받은 클라이언트 ID
    private val REDIRECT_URI = "http://localhost:8080/kakao/callback" // 인증 완료 후 리다이렉트될 URI

    /**
     * 카카오 로그인 인증 코드 요청
     * @return 카카오 인증 페이지로 리다이렉트
     */
    @GetMapping("/auth")
    fun requestKakaoCode(): RedirectView {
        val redirectUrl = "$KAKAO_AUTH_REDIRECT_URL?" +
                "client_id=$CLIENT_ID&" +
                "redirect_uri=$REDIRECT_URI&" +
                "response_type=code&" +
                "scope=talk_message,profile_nickname" // 요청할 권한 범위

        return RedirectView(redirectUrl)
    }

    /**
     * 카카오 인증 콜백 처리
     * @param code 카카오로부터 받은 인증 코드
     */
    @GetMapping("/callback")
    fun handleCallback(@RequestParam code: String) {
        println("Received authorization code: $code")

        // 인증 코드로 액세스 토큰 발급
        val accessToken = kakaoAuthService.getAccessToken(code)
        // 발급받은 토큰으로 카카오톡 메시지 전송
        kakaoAuthService.sendKakaoMessage(accessToken)
    }
}