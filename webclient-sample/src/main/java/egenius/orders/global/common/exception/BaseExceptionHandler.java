package egenius.orders.global.common.exception;


import egenius.orders.global.common.BaseResponse.BaseResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseExceptionHandler {
    /**
     * 발생한 예외 처리
     */

    @ExceptionHandler
    protected ResponseEntity<?> ApiError(BaseException e) {
        // responseEntity에 error객체를 그대로 넘겨주면 상속받고있는 runtimeException의 필드를 모두 넘겨주게 된다
        // 따라서 내가 원하는 필드값만 가져오도록 BaseResponse를 생성한 후, 이를 값으로 넘겨준다
        BaseResponse response = new BaseResponse(e);
        return new ResponseEntity<>(response, e.getHttpStatus());
    }

    @ExceptionHandler
    protected ResponseEntity<?> RuntimeError(RuntimeException e) {
        BaseResponse response = new BaseResponse(e);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}