package kdh.batchtest.domain.test.infrastructure.entity;


import jakarta.persistence.*;
import lombok.*;


@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BeforeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String userName;

}
