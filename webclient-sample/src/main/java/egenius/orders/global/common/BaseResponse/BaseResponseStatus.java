package egenius.orders.global.common.BaseResponse;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


@Getter
public enum BaseResponseStatus {

    /**
     * 200: 요청 성공
     **/
    SUCCESS(HttpStatus.OK,true, 200, "요청에 성공하였습니다.");

    /**
     * 1000 : Order Service Error
     * - 외부 api에서 발생한 에러는 db에서 관리한다
     * - code는 (1000 + pk)로 계산해서 보내준다
     */


    private final HttpStatusCode httpStatus;
    private final boolean isSuccess;
    private final int code;
    private final String message;


    BaseResponseStatus(HttpStatusCode httpStatus, boolean isSuccess, int code, String message) {
        this.httpStatus = httpStatus;
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
