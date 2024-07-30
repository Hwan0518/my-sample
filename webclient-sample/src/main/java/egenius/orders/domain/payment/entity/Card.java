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
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "issuer_code")
    private String issuer_code; //카드 발급사

    @Column(name = "number")
    private String number; //카드 번호

    @Column(name = "installmentPlanMonths")
    private String installmentPlanMonths; //할부 개월 수

    @Column(name = "card_type")
    private String cardType;

}
