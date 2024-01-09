package com.guen.program.todo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "TodoDto : todo 정보")
public class TodoDto {

    @Schema(description = "todo 제목", nullable = false, example = "제목 ..." ,defaultValue = "제목...") //@Schema : 필드에 대한 설명
    @NotNull
    private String subject;

    @Schema(description = "todo 내용", nullable = true, example = "내용 ...",defaultValue = "내용 ...") //@Schema : 필드에 대한 설명
    private String body;

    @Schema(description = "todo 완료 여부", nullable = false, example = "true|false",defaultValue = "false") //@Schema : 필드에 대한 설명
    private String completed = "false";

}
