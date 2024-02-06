package com.guen.common.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.guen.program.todo.JsonView.TodoView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "페이지 정보")
public class PageResponse<T> {

    @Schema(description = "페이지")
    @JsonView(TodoView.User.class)
    private int page;
    @Schema(description = "페이지 당 아티클")
    @JsonView(TodoView.User.class)
    private int size;
    @Schema(description = "전체 아티클 수")
    @JsonView(TodoView.User.class)
    private int totalCount;
    @Schema(description = "content")
    @JsonView(TodoView.User.class)
    private Object content;

    @Builder
    public PageResponse(int page, int size, int totalCount, Object content) {
        this.page = page+1;
        this.size = size;
        this.totalCount = totalCount;
        this.content = content;
    }
}
