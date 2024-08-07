package kdh.batchtest.domain.test.infrastructure.entity;


import jakarta.persistence.*;
import lombok.*;


@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class WinEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "win")
	private Long win;

	@Column(name = "reward")
	private Boolean reward;

	/**
	 * WinEntity
	 * 1. reward 업데이트
	 */

	// 1. reward 여부 변경
	public void updateReward(Boolean reward) {
		this.reward = reward;
	}
}
