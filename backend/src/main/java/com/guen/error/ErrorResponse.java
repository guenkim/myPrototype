package com.guen.error;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "에러 Response")
@Getter
@Setter
public class ErrorResponse{

    @Schema(description = "에러 메시지", defaultValue = "")
    private String errorMessage="일단 에러메시지다..";

    @Schema(description = "에러코드", allowableValues = {"404", "500"})
    private String errorCode="일단 에러코드다..";

    public ErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
        this.errorCode = "404";
    }
    public ErrorResponse(String errorMessage, String errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
}