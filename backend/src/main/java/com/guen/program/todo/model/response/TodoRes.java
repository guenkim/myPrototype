package com.guen.program.todo.model.response;

import com.guen.program.todo.model.entity.Todo;
import com.guen.program.todo.model.enumclass.Complete;
import com.querydsl.core.types.dsl.BooleanExpression;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

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

    public TodoRes(Todo todo) {
        this.id = todo.getId();
        this.subject = todo.getSubject();
        this.body = todo.getBody();
        this.completed = todo.getCompleted()==Complete.FALSE ? Boolean.FALSE : Boolean.TRUE;
    }
}
