package egenius.orders.domain.payment.webdto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentApproveRequestDto {
    private String paymentKey;
    private String orderId;
    private Integer amount;
}
