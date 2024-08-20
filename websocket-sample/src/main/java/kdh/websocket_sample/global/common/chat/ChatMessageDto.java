package kdh.websocket_sample.global.common.chat;


import lombok.*;


@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {

	// 메시지 타입
	private MessageType type;
	// 방 번호
	private Long chatRoomId;
	// 메시지 보낸 사람
	private Long senderId;
	// 메시지
	private String message;

}
