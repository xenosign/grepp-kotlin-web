package com.example.web.controller

import com.example.web.service.KakaoAuthService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView
import java.net.http.HttpResponse

@RestController
@RequestMapping("/kakao")
class KakaoController(private val kakaoAuthService: KakaoAuthService) {
    private val KAKAO_AUTH_REDIRECT_URL = "https://kauth.kakao.com/oauth/authorize"
    private val CLIENT_ID = ""
    private val REDIRECT_URI = "http://localhost:8080/kakao/callback"

    @GetMapping("/auth")
    fun requestKakaoCode(): RedirectView {
        val redirectUrl = "$KAKAO_AUTH_REDIRECT_URL?" +
                "client_id=$CLIENT_ID&" +
                "redirect_uri=$REDIRECT_URI&" +
                "response_type=code&" +
                "scope=talk_message,profile_nickname";

        return RedirectView(redirectUrl)
    }

    @GetMapping("/callback")
    fun handleCallback(@RequestParam code: String) {
        println("Received authorization code: $code")

        val accessToken = kakaoAuthService.getAccessToken(code);
        kakaoAuthService.sendKakaoMessage(accessToken);
    }
}