package kdh.websocket_sample.domain.chat.api.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

	/**
	 * ChatController
	 * 1. 채팅방 생성
	 * 2. 채팅방 메시지 조회
	 * 3. 메시지 전송
	 * 4. 채팅방 목록 조회
	 */

	//1. 채팅방 생성
	@PostMapping("/create")
	public void createChatRoom() {

	}

	//2. 채팅방 메시지 조회


	//3. 메시지 전송


	//4. 채팅방 목록 조회
}
