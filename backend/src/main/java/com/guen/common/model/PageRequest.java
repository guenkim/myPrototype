package com.guen.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Positive;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;


@Schema(description = "페이지 정보")
@Getter
@Setter
public final class PageRequest {


    @Nullable
    @Schema(description = "검색어", example = "검색어....",required = false)
    private String subject;

    @Schema(description = "요청페이지", example = "1" ,required = false)
    @Positive
    @Nullable
    private int page=1;

    @Schema(description = "페이지당 아티클수", example = "10",required = false)
    @Positive
    @Nullable
    private int size=10;

    //    private Sort.Direction direction;
    public void setPage(int page) {
        this.page = page <= 0 ? 1 : page;
    }

    public void setSize(int size) {
        int DEFAULT_SIZE = 5;
        int MAX_SIZE = 50;
        this.size = size > MAX_SIZE ? DEFAULT_SIZE : size;
    }


    //    public void setDirection(Sort.Direction direction) {
//        this.direction = direction;
//    }
    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }


    //    public Sort.Direction getDirection() {
//        return direction;
//    }
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public org.springframework.data.domain.PageRequest of() {
//        return org.springframework.data.domain.PageRequest.of(page - 1, size, direction, "createdAt");
        return org.springframework.data.domain.PageRequest.of(page - 1, size);
    }
}
