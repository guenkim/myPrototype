package com.guen.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "페이지 정보")
public class PageResponse<T> {

    @Schema(description = "페이지")
    private int page;
    @Schema(description = "페이지 당 아티클")
    private int size;
    @Schema(description = "전체 아티클 수")
    private int totalCount;
    @Schema(description = "content")
    private Object content;

    @Builder
    public PageResponse(int page, int size, int totalCount, Object content) {
        this.page = page+1;
        this.size = size;
        this.totalCount = totalCount;
        this.content = content;
    }
}
