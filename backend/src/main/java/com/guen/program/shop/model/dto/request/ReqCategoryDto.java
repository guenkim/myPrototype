package com.guen.program.shop.model.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "카테고리 정보")
@Getter
public class ReqCategoryDto {

    @Schema(description = "카테고리명")
    private String name;

    @JsonCreator
    public ReqCategoryDto(@JsonProperty("name") String name) {
        this.name = name;
    }
}
