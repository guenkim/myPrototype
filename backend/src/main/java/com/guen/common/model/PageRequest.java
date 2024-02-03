package com.guen.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;


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

    @Schema(description = "페이지 정렬 정보", example = "sort=field1,asc&sort=field2,desc")
    private String[] sortParams;

    @JsonIgnore
    private List<Sort.Order> sort;
    public void setSort(String[] sortParams) {
        //this.sort = sort;
        if (sortParams != null && sortParams.length > 0) {
            this.sort = new ArrayList<>();
            for (int i = 0; i < sortParams.length; i++) {
                String fieldName = sortParams[i].split(",")[0];
                Sort.Direction direction  = Sort.Direction.fromString(sortParams[i].split(",")[1]);
                this.sort.add(Sort.Order.by(fieldName).with(direction));
            }
        }
    }


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

    public org.springframework.data.domain.PageRequest of() {
        return org.springframework.data.domain.PageRequest.of(page - 1, size ,Sort.by(sort));
    }
}
