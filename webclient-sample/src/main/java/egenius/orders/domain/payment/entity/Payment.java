package egenius.orders.domain.payment.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_key")
    private String paymentKey;

    @Column(name = "type")
    private String type;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "order_name")
    private String orderName;

    @Column(name = "m_id")
    private String mId;

    @Column(name = "method")
    private String method;

    @Column(name = "total_amount")
    private Integer totalAmount;

    @Column(name = "balance_amount")
    private Integer balanceAmount;

    @Column(name = "status")
    private String status;

    @Column(name = "requested_at") //todo: datetime으로 바꾸기
    private String requestedAt;

    @Column(name = "approved_at")
    private String approvedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cancels_id", referencedColumnName = "id")
    private Cancels cancels;

    @Column(name = "isPartialCancelable")
    private Boolean isPartialCancelable;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card", referencedColumnName = "id")
    private Card card;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "virtual_account", referencedColumnName = "id")
    private VirtualAccount virtualAccount;

    @Column(name = "receipt_url")
    private String receipt_url; // receipt의 url과 매핑됨

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "easy_pay", referencedColumnName = "id")
    private EasyPay easyPay;


}
