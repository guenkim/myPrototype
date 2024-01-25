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
    INTERNAL_SERVER_ERROR,
    AccessDenied,
    Signature,
    MalformedJwt,
    ExpiredJwtException,
    ExpiredRefreshJwtException;

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


        @Value("${error.AccessDenied.code}")
        @NotBlank
        private String accessDeniedErrCd;
        @Value("${error.AccessDenied.message}")
        @NotBlank
        private String accessDeniedErrMsg;
        @Value("${error.AccessDenied.status}")
        @NotBlank
        private int accessDeniedErrStts;


        @Value("${error.Signature.code}")
        @NotBlank
        private String signatureErrCd;
        @Value("${error.Signature.message}")
        @NotBlank
        private String signatureErrMsg;
        @Value("${error.Signature.status}")
        @NotBlank
        private int signatureErrStts;

        @Value("${error.MalformedJwt.code}")
        @NotBlank
        private String malformedJwtErrCd;
        @Value("${error.MalformedJwt.message}")
        @NotBlank
        private String malformedJwtErrMsg;
        @Value("${error.MalformedJwt.status}")
        @NotBlank
        private int malformedJwtErrStts;

        @Value("${error.ExpiredJwtException.code}")
        @NotBlank
        private String expiredJwtErrCd;
        @Value("${error.ExpiredJwtException.message}")
        @NotBlank
        private String expiredJwtErrMsg;
        @Value("${error.ExpiredJwtException.status}")
        @NotBlank
        private int expiredJwtErrStts;

        @Value("${error.ExpiredRefreshJwtException.code}")
        @NotBlank
        private String expiredRefreshJwtErrCd;
        @Value("${error.ExpiredRefreshJwtException.message}")
        @NotBlank
        private String expiredRefreshJwtErrMsg;
        @Value("${error.ExpiredRefreshJwtException.status}")
        @NotBlank
        private int expiredRefreshJwtErrStts;



        public void init(){
            ErrorCode.INPUT_VALUE_INVALID.initialize(invalidInputErrCd,invalidInputErrMsg,invalidInputErrStts);
            ErrorCode.INTERNAL_SERVER_ERROR.initialize(serverErrCd,serverErrMsg,serverErrStts);



            ErrorCode.AccessDenied.initialize(accessDeniedErrCd,accessDeniedErrMsg,accessDeniedErrStts);
            ErrorCode.Signature.initialize(signatureErrCd,signatureErrMsg,signatureErrStts);
            ErrorCode.MalformedJwt.initialize(malformedJwtErrCd,malformedJwtErrMsg,malformedJwtErrStts);
            ErrorCode.ExpiredJwtException.initialize(expiredJwtErrCd,expiredJwtErrMsg,expiredJwtErrStts);
            ErrorCode.ExpiredRefreshJwtException.initialize(expiredRefreshJwtErrCd,expiredRefreshJwtErrMsg,expiredRefreshJwtErrStts);
        }
    }
}
