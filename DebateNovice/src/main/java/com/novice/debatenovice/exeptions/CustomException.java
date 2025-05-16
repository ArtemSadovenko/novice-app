package com.novice.debatenovice.exeptions;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private HttpStatus httpStatus;

    public CustomException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public CustomException(String message) {
        super(message);
    }

    public CustomExceptionDTO getExceptionDTO() {
        return new CustomExceptionDTO(super.getMessage(), httpStatus);
    }

    public String getMessage() {
        return super.getMessage();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public class CustomExceptionDTO {
        private final String message;
        private final HttpStatus httpStatus;

        public CustomExceptionDTO(String message, HttpStatus httpStatus) {
            this.message = message;
            this.httpStatus = httpStatus;
        }
    }
}
