package egenius.orders.global.common.BaseResponse;


import egenius.orders.global.common.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import static egenius.orders.global.common.BaseResponse.BaseResponseStatus.SUCCESS;

public record BaseResponse<T>(HttpStatusCode httpStatus, Boolean isSuccess, String message, int code, T result) {
    /**
     * 필요값 : Http상태코드, 성공여부, 메시지, 에러코드, 결과값
     */

    // 요청 성공한 경우
    public BaseResponse(T result) {
        this(HttpStatus.OK, true, SUCCESS.getMessage(), SUCCESS.getCode(), result);
    }

    // 요청 실패한 경우
    public BaseResponse(BaseException e) {
        // BaseResponseStatus가 있는 경우 그대로 가져와서 사용
        // 없는 경우는 error 객체에서 직접 message와 code를 가져와서 baseResponse를 생성함
        this(
                e.getHttpStatus(),
                false,
                (e.getStatus() != null) ? e.getStatus().getMessage() : e.getMessage(),
                (e.getStatus() != null) ? e.getStatus().getCode() : e.getCode(),
                null
        );
    }
}
