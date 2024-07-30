package egenius.orders.global.common.exception;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderServiceTossApiError {
    /**
     * 1000 : Order Service Toss Api Error
     * - 외부 api에서 발생한 에러는 db에서 관리한다
     * - code는 (1000 + pk)로 계산해서 보내준다
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code_name")
    private String codeName;

    @Column(name = "is_success")
    private boolean isSuccess;

    @Column(name = "code")
    private int code;

    @Column(name = "message")
    private String message;

    // 코드번호 생성 -> 1000 + id값
    public void createCodeNumber(Long id) {
        this.code = (int) (1000 + id);
    }
}
