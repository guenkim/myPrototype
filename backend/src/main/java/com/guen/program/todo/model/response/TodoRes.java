package com.guen.program.todo.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guen.program.todo.model.entity.Todo;
import com.guen.program.todo.model.enumclass.Complete;
import com.querydsl.core.types.dsl.BooleanExpression;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "todo 정보")
@Getter
public class TodoRes {

    @Schema(description = "todo 아이디", nullable = false, example = "todo 아이디" ,defaultValue = "todo 아이디")
    private Long id;
    @Schema(description = "todo 제목", nullable = false, example = "제목 ..." ,defaultValue = "제목...")
    private String subject;

    @Schema(description = "todo 내용", nullable = true, example = "내용 ...",defaultValue = "내용 ...")
    private String body;

    @Schema(description = "todo 완료 여부", nullable = false, example = "TRUE or FALSE",defaultValue = "FALSE" )
    private Boolean completed;

    @Schema(description = "todo 등록일", nullable = false, example = "2021-01-01 00:00:00", type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regdt;

    @Schema(description = "todo 수정일", nullable = false, example = "2021-01-01 00:00:00", type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime moddt;

    @Builder
    public TodoRes(Long id, String subject, String body, Boolean completed, LocalDateTime regdt, LocalDateTime moddt) {
        this.id = id;
        this.subject = subject;
        this.body = body;
        this.completed = completed;
        this.regdt = regdt;
        this.moddt = moddt;
    }
}
