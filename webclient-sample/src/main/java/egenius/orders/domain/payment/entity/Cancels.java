package egenius.orders.domain.payment.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Cancels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cancelReason")
    private String cancelReason;

    @Column(name = "canceledAt")
    private String canceledAt;

    @Column(name = "cancel_amount")
    private Integer cancelAmount;

    @Column(name = "refundable_amount")
    private Integer refundableAmount;

    @Column(name = "transaction_key")
    private String transactionKey;

    @Column(name = "receipt_key")
    private String receiptKey;


}
