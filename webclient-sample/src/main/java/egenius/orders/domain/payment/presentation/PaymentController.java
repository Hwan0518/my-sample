package egenius.orders.domain.payment.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import egenius.orders.domain.payment.application.PaymentServiceImpl;
import egenius.orders.domain.payment.webdto.CardPayRequestDto;
import egenius.orders.domain.payment.dto.PaymentDto;
import egenius.orders.global.common.exception.BaseException;
import egenius.orders.global.common.BaseResponse.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentServiceImpl paymentService;
    private final ModelMapper modelMapper;

    /**
     * payment
     * 1. 카드 번호 결제
     * 2. 결제 승인
     */

    //1. 카드번호 결제
    @PostMapping("/cardpay")
    public BaseResponse<?> cardPay(@RequestBody CardPayRequestDto requestDto) throws JsonProcessingException, BaseException {
        PaymentDto responseDto = paymentService.cardPay(requestDto);
        return new BaseResponse<>(responseDto);
    }


}
