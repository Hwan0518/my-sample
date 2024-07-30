package egenius.orders.domain.payment.dto;

import egenius.orders.domain.payment.entity.Cancels;
import egenius.orders.domain.payment.entity.Card;
import egenius.orders.domain.payment.entity.EasyPay;
import egenius.orders.domain.payment.entity.VirtualAccount;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentDto {
    private String paymentKey;
    private String type;
    private String orderId;
    private String orderName;
    private String mId;
    private String method;
    private Integer totalAmount;
    private Integer balanceAmount;
    private String status;
    private String requestedAt;
    private String approvedAt;
    private Cancels cancels;
    private Boolean isPartialCancelable;
    private Card card;
    private VirtualAccount virtualAccount;
    private String secret;
    private String receipt_url;
    private EasyPay easyPay;
}
