package yourgroup.boilerplatelayered.global.common.exception;

import lombok.Getter;
import yourgroup.boilerplatelayered.global.common.response.BaseResponseStatus;

@Getter
public class BaseException extends RuntimeException{
    private BaseResponseStatus status;

    public BaseException(BaseResponseStatus status) {
        this.status = status;
    }
}