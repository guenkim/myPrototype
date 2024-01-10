package com.guen.program.todo.api;

import com.guen.error.ErrorResponse;
import com.guen.program.todo.model.enumclass.Complete;
import com.guen.program.todo.model.request.TodoReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@RestController
@RequestMapping("/api/todos")
@Tag(name = "Todo API")
public class TodoController {

    @GetMapping
    @Operation(summary = "todo 목록 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "todo 목록 조회 성공")
    })
    public ResponseEntity getTodos() {
        log.info("TodoController > getTodos");

        List<TodoReq> todoReqs = IntStream.range(1, 11).mapToObj(i -> new TodoReq("제목" + i, "내용" + i, Complete.FALSE)).collect(Collectors.toList());

        todoReqs.add(new TodoReq("제목", "내용", Complete.FALSE));
        return ResponseEntity.ok().body(todoReqs);
    }

    @GetMapping("/{todoId}")
    @Operation(summary = "todo 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "todo 정보 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public ResponseEntity getTodo(
            @Parameter(description = "todo 아이디", required = true, in = ParameterIn.PATH) // @ApiParam : Parameter 설명
            @PathVariable(value = "todoId", required = true) String todoId
    ) {
        log.info("TodoController > getTodo");
        return ResponseEntity.ok().body(new TodoReq("제목", "내용", Complete.FALSE));
    }

    @PostMapping("/create")
    @Operation(summary = "todo 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "todo 생성 성공", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity create(
            @Valid @RequestBody TodoReq todoReq
    ) {
        log.info("TodoController > create");

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{todoId}")
    @Operation(summary = "todo 수정") // @Operation : API 설명
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "todo 수정 성공", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity update(
            @Parameter(description = "todo 아이디", required = true, in = ParameterIn.PATH)
            @PathVariable(value = "todoId", required = true) String todoId,
            @Valid @RequestBody TodoReq todoReq
    ) {
        log.info("TodoController > update");
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{todoId}")
    @Operation(summary = "todo 삭제") // @Operation : API 설명
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "todo 삭제 성공", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity delete(
            @Parameter(description = "todo 아이디",required = true, in = ParameterIn.PATH)
            @PathVariable(value = "todoId", required = true) String todoId
    ) {
        log.info("TodoController > delete");
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{todoId}/{completed}")
    @Operation(summary = "todo 완료 여부 설정") // @Operation : API 설명
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "todo 완료 여부 설정 성공", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity updateComplete(
            @Parameter(description = "todo 아이디", required = true, in = ParameterIn.PATH)
            @PathVariable(value = "todoId", required = true) String todoId,
            @Parameter(description = "todo 완료 여부", required = true, in = ParameterIn.PATH)
            @PathVariable(value = "completed", required = true) boolean completed
    ) {
        log.info("TodoController > updateComplete");
        return ResponseEntity.noContent().build();
    }

}
