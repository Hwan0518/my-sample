package egenius.orders.domain.payment.infrastructure;

import egenius.orders.global.common.exception.OrderServiceTossApiError;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentErrorRepository extends JpaRepository<OrderServiceTossApiError, Long> {
    /**
     * 토스페이 결제 에러
     */

    // 1. Code로 조회
    Optional<OrderServiceTossApiError> findByCodeName(String codeName);
}
