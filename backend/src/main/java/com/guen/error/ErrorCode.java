package com.guen.error;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;


@Getter
public enum ErrorCode {
    INPUT_VALUE_INVALID,
    INTERNAL_SERVER_ERROR;

    private String code;
    private String message;
    private int status;

    public void initialize(String code,String message,int httpStatusCode){
        this.code = code;
        this.message = message;
        this.status = httpStatusCode;
    }

    @Component
    @Validated
    public static class ErrorCodeInitializer {

        @Value("${error.INPUT_VALUE_INVALID.code}")
        @NotBlank
        private String invalidInputErrCd;
        @Value("${error.INPUT_VALUE_INVALID.message}")
        @NotBlank
        private String invalidInputErrMsg;
        @Value("${error.INPUT_VALUE_INVALID.status}")
        @NotBlank
        private int invalidInputErrStts;

        @Value("${error.INTERNAL_SERVER_ERROR.code}")
        @NotBlank
        private String serverErrCd;
        @Value("${error.INTERNAL_SERVER_ERROR.message}")
        @NotBlank
        private String serverErrMsg;
        @Value("${error.INTERNAL_SERVER_ERROR.status}")
        @NotBlank
        private int serverErrStts;

        public void init(){
            ErrorCode.INPUT_VALUE_INVALID.initialize(invalidInputErrCd,invalidInputErrMsg,invalidInputErrStts);
            ErrorCode.INTERNAL_SERVER_ERROR.initialize(serverErrCd,serverErrMsg,serverErrStts);
        }
    }
}
