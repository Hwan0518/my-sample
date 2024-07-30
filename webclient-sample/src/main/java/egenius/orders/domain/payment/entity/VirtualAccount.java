package egenius.orders.domain.payment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class VirtualAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number")
    private String accountNumber; // 계좌번호

    @Column(name = "bank_code")
    private String bankCode; //가상계좌 은행 숫자코드

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "due_date")
    private String dueDate; //입금기한

    @Column(name = "refund_status")
    private String refundStatus; //환불처리 상태

    @Column(name = "expired")
    private Boolean expired;

    @Column(name = "settlement_status")
    private String settlementStatus;
}
