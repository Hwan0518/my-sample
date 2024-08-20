package kdh.websocket_sample.global.config.websocket;


import com.fasterxml.jackson.databind.ObjectMapper;
import kdh.websocket_sample.global.common.chat.ChatMessageDto;
import kdh.websocket_sample.global.common.chat.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


// 여러 클라이언트에서 오는 메시지를 받아서 처리해주는 역할
// 받은 메시지를 log로 출력하고, 클라이언트로 환영 메시지를 돌려줌
@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {

	// 직렬화를 위한 objectMapper
	private final ObjectMapper mapper;
	// 현재 연결된 세션들
	private final Set<WebSocketSession> sessions = new HashSet<>();
	// 채팅방과 연결된 세션들 -> {채팅방id: {session1, session2, ...}}
	private final Map<Long, Set<WebSocketSession>> chatRoomSessionMap = new HashMap<>();


	/**
	 * 클라이언트가 연결되었을 때 실행되는 메서드
	 * 1. 소켓 연결 확인
	 * 2. 유효성 검사 및 메시지 전송 핸들러
	 * 3. 소켓 종료 확인
	 * 4. 종료된 세션 제거
	 * 5. 채팅방에 메시지 전송
	 */

	// 1. 소켓 연결 확인
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("{} 연결됨", session.getId());
		sessions.add(session);
	}


	// 2. 유효성 검사 및 메시지 전송 핸들러
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// payload를 ChatMessageDto로 변환
		String payload = message.getPayload();
		ChatMessageDto chatMessageDto = mapper.readValue(payload, ChatMessageDto.class);
		log.info("sessionId: {}, chatMessage: {}", session.getId(), chatMessageDto);

		// 채팅방 세션 확인 -> 없으면 새로 생성
		Long chatRoomId = chatMessageDto.getChatRoomId();
		if (!chatRoomSessionMap.containsKey(chatRoomId)) {
			chatRoomSessionMap.put(chatRoomId, new HashSet<>());
		}
		Set<WebSocketSession> chatRoomSessions = chatRoomSessionMap.get(chatRoomId);

		// 메시지 전송 -> 메시지 타입이 Enter라면 새로운 session이므로 추가해줌
		MessageType messageType = chatMessageDto.getType();
		if (messageType.equals(MessageType.ENTER)) {
			// 새로운 session을 채팅방 session set에 추가
			chatRoomSessions.add(session);
		}
		if (chatRoomSessions.size() >= 3) {
			// 채팅방 인원이 3명 이상이면 연결이 끊긴 세션을 제거
			removeClosedSession(chatRoomSessions);
		}
		sendMessageToChatRoom(chatMessageDto, chatRoomSessions);
	}


	// 3. 소켓 종료 확인
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.info("{} 연결 종료됨", session.getId());
		sessions.remove(session);
	}


	// 4. 종료된 세션 제거
	private void removeClosedSession(Set<WebSocketSession> chatRoomSession) {
		chatRoomSession.removeIf(session -> !sessions.contains(session));
	}


	// 5. 채팅방에 메시지 전송
	private void sendMessageToChatRoom(ChatMessageDto chatMessageDto, Set<WebSocketSession> chatRoomSession) {
		chatRoomSession.parallelStream().forEach(
			session -> this.sendMessage(session, chatMessageDto)
		);
	}

	public <T> void sendMessage(WebSocketSession session, T message) {
		try {
			session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
		} catch (Exception e) {
			log.error("메시지 전송 실패: ", e);
		}
	}


}
