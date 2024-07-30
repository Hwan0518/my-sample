package egenius.orders.domain.payment.webdto;

import lombok.*;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CardPayRequestDto {
    private String userEmail;
    private int amount;
    private String orderId;
    private String cardNumber;
    private String cardExpirationYear;
    private String cardExpirationMonth;
    private String orderName;
    private String customerIdentityNumber;
}
