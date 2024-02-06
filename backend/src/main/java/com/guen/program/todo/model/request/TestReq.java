package com.guen.program.todo.model.request;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Schema(description = "test")
public class TestReq {

    @Schema(description = "이름", nullable = false, example = "이름")
    @NotBlank
    private String name;

    @Schema(description = "주소", nullable = true, example = "주소")
    private String address;

    @JsonCreator
    public TestReq(
            @JsonProperty("name") String name,
            @JsonProperty("address") String address)
    {
        this.name = name;
        this.address = address;
    }
}
