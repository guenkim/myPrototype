package com.guen.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;


@Schema(description = "페이지 정보")
@Getter
@Setter
public final class PageRequest {

    @Schema(description = "검색어", example = "검색어....",required = false)
    private String subject;

    @Schema(description = "요청페이지", example = "1" ,required = false)
    @Positive
    private int page=1;

    @Schema(description = "페이지당 아티클수", example = "10",required = false)
    @Positive
    private int size=5;

    //    private Sort.Direction direction;

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }



    public void setPage(String page) {
        try {
            this.page = Integer.parseInt(page) <= 0 ? 1 : Integer.parseInt(page);
        } catch (NumberFormatException e) {
            this.page = 1;
        }
    }

    public int getPage() {
        return page;
    }


    public void setSize(String size) {
        try {
            this.size = Integer.parseInt(size);
        } catch (NumberFormatException e) {
            int DEFAULT_SIZE = 5;
            int MAX_SIZE = 50;
            this.size = DEFAULT_SIZE;
        }
    }


    public int getSize() {
        return size;
    }

    //    public void setDirection(Sort.Direction direction) {
//        this.direction = direction;
//    }

    //    public Sort.Direction getDirection() {
//        return direction;
//    }


    public org.springframework.data.domain.PageRequest of() {
//        return org.springframework.data.domain.PageRequest.of(page - 1, size, direction, "createdAt");
        return org.springframework.data.domain.PageRequest.of(page - 1, size);
    }
}
