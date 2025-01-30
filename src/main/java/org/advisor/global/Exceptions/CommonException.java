package org.advisor.global.Exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class CommonException extends RuntimeException {
    private HttpStatus status;
    private boolean errorCode;
    private Map<String, List<String>> errorMessages;

    public CommonException(String message, HttpStatus status) {
        super(message);
        this.status = Objects.requireNonNullElse(status, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    public CommonException(Map<String, List<String>> errorMessages, HttpStatus status) {
        this.errorMessages = errorMessages;
        this.status = status;
    }
}