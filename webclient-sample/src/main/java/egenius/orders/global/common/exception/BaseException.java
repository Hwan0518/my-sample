package egenius.orders.global.common.exception;

import egenius.orders.global.common.BaseResponse.BaseResponseStatus;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class BaseException extends RuntimeException{
    private HttpStatusCode httpStatus;
    private BaseResponseStatus status;
    private int code;
    private String message;

    public BaseException(HttpStatusCode httpStatus, BaseResponseStatus status, int code, String message) {
        this.httpStatus = httpStatus;
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
