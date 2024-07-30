package egenius.orders.domain.payment.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import egenius.orders.domain.payment.dto.*;
import egenius.orders.domain.payment.webdto.CardPayRequestDto;
import egenius.orders.global.common.exception.BaseException;

public interface PaymentService {

    /**
     * payment
     *
     * 1. 카드결제요청
     */

    // 1. 카드결제
    PaymentDto cardPay(CardPayRequestDto requestDto) throws JsonProcessingException, BaseException;
}
