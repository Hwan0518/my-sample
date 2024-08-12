package kdh.websocket_sample.global.config.websocket;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

	// 웹소켓 통신을 처리해줄 핸들러
	private final WebSocketHandler webSocketHandler;


	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// WebSocketHandler와 endPoint를 등록
		// -> "ws://localhost:8080/ws/chat" end-point로 요청이 오면 webSocketHandler를 통해 webSocket 통신을 진행
		registry
			.addHandler(webSocketHandler, "/ws/chat")
//			.addInterceptors(new HttpSessionHandshakeInterceptor())
			.setAllowedOrigins("*");
	}

}
