package egenius.orders.domain.payment.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import egenius.orders.domain.payment.dto.*;
import egenius.orders.domain.payment.entity.*;
import egenius.orders.domain.payment.infrastructure.*;
import egenius.orders.domain.payment.webdto.CardPayRequestDto;
import egenius.orders.global.common.exception.OrderServiceTossApiError;
import egenius.orders.global.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.json.simple.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.nio.charset.Charset;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService{

    private final PaymentRepository paymentRepository;
    private final PaymentErrorRepository errorRepository;
    private final CardRepository cardRepository;
    private final VirtualAccountRepository virtualAccountRepository;
    private final EasyPayRepository easyPayRepository;
    private final CancelsRepository cancelsRepository;
    private final ModelMapper modelMapper;
    private final WebClient webClient;

    /**
     * payment
     *
     * 1. 카드결제요청
     */

    // 1. 카드결제요청
    @Override
    public PaymentDto cardPay(CardPayRequestDto requestDto) throws JsonProcessingException {
        // 요청을 보낼 client 생성
        PaymentDto response = null;
        System.out.println("requestDto = " + requestDto);
        try {
            response = webClient
                    // http method 설정
                    .post()
                    // 카드 번호 결제 url 설정
                    .uri("/key-in")
                    // requestDto를 toJson 메소드를 사용하여 Json으로 변환 후, Body값에 넣음
                    .body(BodyInserters.fromValue(toJson(requestDto)))
                    // ClientResponse에서 ResponseEntity를 받아 디코딩함, 즉 body만 받는다 생각하면 된다
                    .retrieve()
                    // 비동기적으로 하나의 데이터를 가져옴, 여러 개를 가져올땐 Flux를 사용
                    // depth를 잘 일치시킨다면? -> 원하는 객체로 바로 매핑시킬 수 있다
                    .bodyToMono(PaymentDto.class)
                    .block();
        }
        // 에러 발생시 json을 파싱해서 에러객체를 return해줌
        catch (WebClientResponseException e) {
            throw errorParsing(e);
        }

        // response를 payment객체로 매핑시킨 후 repository에 save함
        Payment payment = modelMapper.map(response, Payment.class);

        // response에서 card, easypay, virtualAccount, cancels가 존재한다면 매핑시킴
        //todo: querydsl로 쉽게 처리되는지 확인해보기
        if (response.getCard() != null) {
            Card card = modelMapper.map(response.getCard(), Card.class);
            cardRepository.save(card);
            payment = payment.toBuilder().card(card).build();
        }
        if (response.getVirtualAccount() != null) {
            VirtualAccount virtualAccount = modelMapper.map(response.getVirtualAccount(), VirtualAccount.class);
            virtualAccountRepository.save(virtualAccount);
            payment = payment.toBuilder().virtualAccount(virtualAccount).build();
        }
        if (response.getEasyPay() != null) {
            EasyPay easyPay = modelMapper.map(response.getEasyPay(), EasyPay.class);
            easyPayRepository.save(easyPay);
            payment = payment.toBuilder().easyPay(easyPay).build();
        }
        if (response.getCancels() != null) {
            Cancels cancels = modelMapper.map(response.getCancels(), Cancels.class);
            cancelsRepository.save(cancels);
            payment = payment.toBuilder().cancels(cancels).build();
        }

        paymentRepository.save(payment);
        return response;
    }


    // Java객체를 Json으로 변환
    public String toJson(Object o) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(o);
    }

    // Json형태로 받은 에러를 Java객체로 파싱, Error Parsing
    //todo: exception handler에서 곧바로 처리되는지 확인해보기
    public BaseException errorParsing(WebClientResponseException e) {
        // Error객체에서 body값만 UTF-8로 받음
        String body = e.getResponseBodyAsString(Charset.forName("UTF-8"));
        // code와 message만을 따로 뽑아냄
        ObjectMapper objectMapper = new ObjectMapper();
        String code = null;
        String message = null;
        try {
            JsonNode jsonNode = objectMapper.readTree(body);
            code = jsonNode.get("code").asText();
            message = jsonNode.get("message").asText();
            System.out.println("code: "+code +", message: "+message);
            // repository에 이미 저장되어있는 에러code라면 그대로 불러내서 사용
            OrderServiceTossApiError error = errorRepository.findByCodeName(code)
                    .orElseThrow(() -> new NoSuchElementException());
            return new BaseException(e.getStatusCode(),null, error.getCode(), error.getMessage());
        }
        // repository에 저장되지 않은 새로운 객체라면, 새로 저장하고, 에러를 return
        catch (NoSuchElementException ex) {
            OrderServiceTossApiError error = OrderServiceTossApiError.builder()
                    .isSuccess(false)
                    .codeName(code)
                    .message(message)
                    .build();
            errorRepository.save(error);
            // error코드를 생성 -> error코드는 1000 + id
            error.createCodeNumber(error.getId());
            return new BaseException(e.getStatusCode(), null, error.getCode(), error.getMessage());
        }
        // 기타 예외처리
        catch (JsonMappingException ex) {
            throw new RuntimeException(ex);
        }
        catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }
}
