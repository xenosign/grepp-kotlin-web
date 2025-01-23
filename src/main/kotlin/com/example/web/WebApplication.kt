package com.example.web

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate


@SpringBootApplication
class WebApplication

@Configuration
class AppConfig {
	@Bean
	fun restTemplate(): RestTemplate {
		return RestTemplate()
	}
}

fun main(args: Array<String>) {
	runApplication<WebApplication>(*args)
}
