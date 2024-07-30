package egenius.orders.global.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 멤버 인스턴스가, entity의 컬럼으로 들어가도록 해줌
@EntityListeners(AuditingEntityListener.class) // 변경되었을 때 자동으로 기록
public abstract class BaseTimeEntity {

    @CreatedDate // 최초 생성 시점
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // 마지막 수정 시점
    private LocalDateTime updatedAt;
}
