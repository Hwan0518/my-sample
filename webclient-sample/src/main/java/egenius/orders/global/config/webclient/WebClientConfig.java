package egenius.orders.global.config.webclient;

import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;

@Configuration
public class WebClientConfig {
    @Value("${test_client_key}")
    private String CLIENT_KEY;
    @Value("${test_secret_key}")
    private String SECRET_KEY;

    // webclient 기본설정
    @Bean
    public WebClient webClient() {
        // (시크릿키 + ":")를 Base64 Url로 인코딩 후 Header에 붙여줘야함
        String encodedSecret = Base64.getUrlEncoder().encodeToString((SECRET_KEY+":").getBytes());
        String authorizationValue = "Basic " + encodedSecret;

        // webClient에 공통으로 사용될 기본 설정값들을 셋팅
        WebClient webClient = WebClient.builder()
                // 요청을 보낼 외부서버의 url
                .baseUrl("https://api.tosspayments.com/v1/payments")
                // 공통으로 들어갈 header 설정
                .defaultHeaders(headers ->
                {
                    headers.add(HttpHeaders.AUTHORIZATION, authorizationValue);
                    headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
                })
                .build();
        return webClient;
    }
}
