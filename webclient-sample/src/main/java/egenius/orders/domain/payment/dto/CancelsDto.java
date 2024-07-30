package egenius.orders.domain.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CancelsDto {
    private Integer cancelAmount;
    private String cancelReason;
    private Integer refundableAmount;
    private String canceledAt;
    private String transactionKey;
    private String receiptKey;
}
