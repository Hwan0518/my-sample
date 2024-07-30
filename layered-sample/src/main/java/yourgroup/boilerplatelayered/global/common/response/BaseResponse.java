package yourgroup.boilerplatelayered.global.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import static yourgroup.boilerplatelayered.global.common.response.BaseResponseStatus.SUCCESS;


public record BaseResponse<T>(HttpStatusCode httpStatus, Boolean isSuccess, String message, int code, T result) {
    /**
     * 필요값 : Http상태코드, 성공여부, 메시지, 에러코드, 결과값
     */

    // 요청에 성공한 경우 -> return 객체가 필요한 경우
    public BaseResponse(T result) {
        this(HttpStatus.OK, true, SUCCESS.getMessage(), SUCCESS.getCode(), result);
    }

    // 요청에 성공한 경우 -> return 객체가 필요 없는 경우
    public BaseResponse() {
        this(HttpStatus.OK, true, SUCCESS.getMessage(), SUCCESS.getCode(), null);
    }

    // 요청 실패한 경우
    public BaseResponse(BaseResponseStatus status) {
        this(status.getHttpStatusCode(), false, status.getMessage(), status.getCode(), null);
    }

}
